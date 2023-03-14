package com.develpouk.niu.match.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.develpouk.niu.match.model.MatchModel
import com.develpouk.niu.match.ui.Head2HeadFragment
import com.develpouk.niu.match.ui.StandingsFragment

class MatchInfoViewPagerAdapter(fragmentActivity: FragmentActivity, match: MatchModel) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        Head2HeadFragment(match),
        StandingsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}