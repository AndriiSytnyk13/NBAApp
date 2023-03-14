package com.develpouk.niu.match.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentMatchInfoBinding
import com.develpouk.niu.match.ui.adapter.MatchAdapter
import com.develpouk.niu.match.ui.adapter.MatchInfoViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchInfoFragment : Fragment() {

    private lateinit var  matchInfoBinding: FragmentMatchInfoBinding
    private val matchInfoArguments: MatchInfoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        matchInfoBinding = FragmentMatchInfoBinding.inflate(inflater, container, false)
        return matchInfoBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchInfoBinding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        showMatch()
        showFragmentsDetail()
    }

    private fun showFragmentsDetail() {
        matchInfoBinding.viewPager.adapter = MatchInfoViewPagerAdapter(requireActivity(), matchInfoArguments.match)
        TabLayoutMediator(matchInfoBinding.tabLayout, matchInfoBinding.viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "H2H"
                }
                1 -> {
                    tab.text = "Standing"
                }
            }
        }.attach()
        for (i in 0 until  matchInfoBinding.tabLayout.tabCount) {
            val tab: View = (matchInfoBinding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val padding = tab.layoutParams as ViewGroup.MarginLayoutParams
            padding.setMargins(50,0,50,0)
            tab.requestLayout()
        }
    }

    private fun showMatch() {
        val adapter = MatchAdapter()
        matchInfoBinding.matchInfoRecyclerview.adapter = adapter
        adapter.setMatches(listOf(matchInfoArguments.match))
    }

}