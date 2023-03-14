package com.develpouk.niu.match.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentHead2headBinding
import com.develpouk.niu.extensions.errorSnackBar
import com.develpouk.niu.interfaces.Collector
import com.develpouk.niu.match.model.MatchModel
import com.develpouk.niu.match.ui.adapter.MatchH2HAdapter
import com.develpouk.niu.util.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Head2HeadFragment(private val match: MatchModel) : Fragment(), Collector {

    private lateinit var head2HeadBinding: FragmentHead2headBinding
    private val matchViewModel: MatchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        head2HeadBinding = FragmentHead2headBinding.inflate(inflater, container, false)
        return head2HeadBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectValues()
        showStanding()
    }

    override fun collectValues() {
        val adapter = MatchH2HAdapter(requireContext())
        head2HeadBinding.matchH2hRecyclerview.adapter = adapter
        val team1Id = match.teams.home.id
        val team2Id = match.teams.visitors.id
        matchViewModel.getHead2HeadMatches("$team1Id-$team2Id")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchViewModel.matchesH2HResponse.collect { response ->
                    when (response) {
                        is NetworkResponse.Success -> {
                            successState()
                            adapter.seth2hMatches(response.data)
                        }
                        is NetworkResponse.Error -> errorViewState(response.message)
                        else -> loadingState()
                    }
                }
            }
        }
    }

    private fun showStanding() {
        Glide.with(requireActivity()).load(match.teams.home.logo).into(head2HeadBinding.team1LogoImageview)
        Glide.with(requireActivity()).load(match.teams.visitors.logo).into(head2HeadBinding.team2LogoImageview)
        head2HeadBinding.team1TeamName.text = match.teams.home.name
        head2HeadBinding.team2TeamName.text = match.teams.visitors.name
    }

    private fun successState() {
        head2HeadBinding.h2hLoading.visibility = View.GONE
        head2HeadBinding.matchH2hRecyclerview.visibility = View.VISIBLE
        head2HeadBinding.h2hLayout1.visibility = View.VISIBLE
        head2HeadBinding.h2hLayout2.visibility = View.VISIBLE
    }

    private fun loadingState() {
        head2HeadBinding.h2hLoading.visibility = View.VISIBLE
        head2HeadBinding.matchH2hRecyclerview.visibility = View.GONE
        head2HeadBinding.h2hLayout1.visibility = View.GONE
        head2HeadBinding.h2hLayout2.visibility = View.GONE
    }

    private fun errorViewState(snackBarMessage: String) {
        val team1Id = match.teams.home.id
        val team2Id = match.teams.visitors.id
        head2HeadBinding.h2hLoading.visibility = View.GONE
        errorSnackBar(snackBarMessage) {
            matchViewModel.getHead2HeadMatches("$team1Id-$team2Id")
        }
    }

}