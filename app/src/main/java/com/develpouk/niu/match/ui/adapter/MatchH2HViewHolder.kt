package com.develpouk.niu.match.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.ItemHeadToHeadBinding
import com.develpouk.niu.enums.MatchStatus
import com.develpouk.niu.match.model.MatchModel

class MatchH2HViewHolder(private val binding: ItemHeadToHeadBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bindViews(match: MatchModel) {
        binding.apply {
            h2hDate.text = match.getDate()
            Glide.with(binding.root).load(match.teams.home.logo).into(h2hTeam1Logo)
            Glide.with(binding.root).load(match.teams.visitors.logo).into(h2hTeam2Logo)
            h2hTeam1Name.text = match.teams.home.name
            h2hTeam2Name.text = match.teams.visitors.name
            h2hScore.text = "${match.scores.home.points}-${match.scores.visitors.points}"

            ContextCompat.getColor(context, R.color.blue).also {
                when (match.scores.matchStatus()) {
                    MatchStatus.HOME_WIN -> h2hTeam1Name.setTextColor(it)
                    MatchStatus.VISITOR_WIN -> h2hTeam2Name.setTextColor(it)
                    else -> {
                        h2hTeam1Name.setTextColor(ContextCompat.getColor(context, R.color.white))
                        h2hTeam2Name.setTextColor(ContextCompat.getColor(context, R.color.white))
                    }
                }
            }
        }
    }
}