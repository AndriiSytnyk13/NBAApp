package com.develpouk.niu.match.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentMatchBinding
import com.develpouk.niu.extensions.errorSnackBar
import com.develpouk.niu.generalui.MainScreenFragmentDirections
import com.develpouk.niu.interfaces.ClickEvent
import com.develpouk.niu.interfaces.Collector
import com.develpouk.niu.match.ui.adapter.MatchAdapter
import com.develpouk.niu.util.NetworkResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchFragment : Fragment(R.layout.fragment_match), Collector {

    private lateinit var matchBinding: FragmentMatchBinding
    private val matchViewModel: MatchViewModel by activityViewModels()
    private var dayDifference = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMatches(dayDifference)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchBinding = FragmentMatchBinding.bind(view)
        collectValues()
        setupRadioButtons()
    }

    override fun collectValues() {
        val matchAdapter = MatchAdapter()
        matchBinding.matchesRecyclerview.adapter = matchAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchViewModel.matchesResponse.collect { response ->
                    when (response) {
                        is NetworkResponse.Success -> {
                            matchAdapter.setClickEvent(object : ClickEvent {
                                override fun onClick(position: Int) {
                                    val action =
                                        MainScreenFragmentDirections.actionMainScreenFragmentToMatchInfoFragment(
                                            response.data[position]
                                        )
                                    requireActivity().findNavController(R.id.nav_host_main)
                                        .navigate(action)
                                }
                            })
                            successStateViews()
                            matchAdapter.setMatches(response.data)
                        }
                        is NetworkResponse.Error -> {
                            errorViewState(response.message)
                        }
                        else -> loadingStateViews()
                    }
                }
            }
        }
    }

    private fun errorViewState(snackBarMessage: String) {
        matchBinding.loadingLottie.visibility = View.GONE
        errorSnackBar(snackBarMessage) {
            matchViewModel.getMatches(dayDifference)
        }
    }

    private fun successStateViews() {
        matchBinding.loadingLottie.visibility = View.GONE
        matchBinding.matchesRecyclerview.visibility = View.VISIBLE
        matchBinding.dateRadoogroup.visibility = View.VISIBLE

    }

    private fun loadingStateViews() {
        matchBinding.loadingLottie.visibility = View.VISIBLE
        matchBinding.matchesRecyclerview.visibility = View.GONE
        matchBinding.dateRadoogroup.visibility = View.GONE
    }

    private fun setupRadioButtons() {
        matchBinding.apply {
            threeDaysBeforeTodayTextview.text = matchViewModel.getFormattedDays(-3)
            twoDaysBeforeTodayTextview.text = matchViewModel.getFormattedDays(-2)
            oneDayBeforeTodayTextview.text = matchViewModel.getFormattedDays(-1)
            oneDayAfterTodayTextview.text = matchViewModel.getFormattedDays(1)
            twoDaysAfterTodayTextview.text = matchViewModel.getFormattedDays(2)
            threeDaysAfterTodayTextview.text = matchViewModel.getFormattedDays(3)

            dateRadoogroup.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.three_days_before_today_textview -> {
                        getMatches(-3)
                    }
                    R.id.two_days_before_today_textview -> {
                        getMatches(-2)
                    }
                    R.id.one_day_before_today_textview -> {
                        getMatches(-1)
                    }
                    R.id.today_textview -> {
                        getMatches(0)
                    }
                    R.id.one_day_after_today_textview -> {
                        getMatches(1)
                    }
                    R.id.two_days_after_today_textview -> {
                        getMatches(2)
                    }
                    R.id.three_days_after_today_textview -> {
                        getMatches(3)
                    }
                }
            }
        }
    }

    private fun getMatches(dayDifference: Int) {
        this.dayDifference = dayDifference
        matchViewModel.getMatches(this.dayDifference)
    }
}