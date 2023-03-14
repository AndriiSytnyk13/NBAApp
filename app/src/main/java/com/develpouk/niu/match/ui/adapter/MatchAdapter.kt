package com.develpouk.niu.match.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.ItemMatchBinding
import com.develpouk.niu.enums.MatchStatus
import com.develpouk.niu.interfaces.ClickEvent
import com.develpouk.niu.match.model.MatchModel

class MatchAdapter : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private val matches = mutableListOf<MatchModel>()

    private var clickEvent: ClickEvent? = null

    fun setClickEvent(clickEvent: ClickEvent) {
        this.clickEvent = clickEvent
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMatches(matches: List<MatchModel>) {
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder =
        MatchViewHolder(
            ItemMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindMatchItems(matches[position])
    }

    inner class MatchViewHolder(
        private val itemMatchBinding: ItemMatchBinding,
        private val context: Context,

        ) : RecyclerView.ViewHolder(itemMatchBinding.root) {


        private val team1ScoreByQuarterTextViews = listOf(
            itemMatchBinding.team11qScore,
            itemMatchBinding.team12qScore,
            itemMatchBinding.team13qScore,
            itemMatchBinding.team14qScore,
            itemMatchBinding.team1Ot1Score,
            itemMatchBinding.team1Ot2Score,
            itemMatchBinding.team1Ot3Score,
        )

        private val team2ScoreByQuarterTextViews = listOf(
            itemMatchBinding.team21qScore,
            itemMatchBinding.team22qScore,
            itemMatchBinding.team23qScore,
            itemMatchBinding.team24qScore,
            itemMatchBinding.team2Ot1Score,
            itemMatchBinding.team2Ot2Score,
            itemMatchBinding.team2Ot3Score,
        )

        fun bindMatchItems(matchModel: MatchModel) {
            itemMatchBinding.apply {
                date.text = matchModel.getDate()
                time.text = matchModel.getTime()
                Glide.with(itemMatchBinding.root).load(matchModel.teams.home.logo).into(team1Logo)
                Glide.with(itemMatchBinding.root).load(matchModel.teams.visitors.logo).into(team2Logo)
                team1Name.text = matchModel.teams.home.name
                team2Name.text = matchModel.teams.visitors.name
                team1TotalScore.text = matchModel.scores.home.points.toString()
                team2TotalScore.text = matchModel.scores.visitors.points.toString()

                matchModel.scores.home.linescore.mapIndexed { index, scoreByQuarter ->
                    team1ScoreByQuarterTextViews[index].text = scoreByQuarter
                }

                matchModel.scores.visitors.linescore.mapIndexed { index, scoreByQuarter ->
                    team2ScoreByQuarterTextViews[index].text = scoreByQuarter
                }
                ContextCompat.getColor(context, R.color.blue).also {
                    when (matchModel.scores.matchStatus()) {
                        MatchStatus.HOME_WIN -> {
                            team1ScoreByQuarterTextViews.forEach { textView ->
                                textView.setTextColor(it)
                            }
                            team1TotalScore.setTextColor(it)
                            team1Name.setTextColor(it)
                        }
                        MatchStatus.VISITOR_WIN -> {
                            team2ScoreByQuarterTextViews.forEach { textView ->
                                textView.setTextColor(it)
                            }
                            team2TotalScore.setTextColor(it)
                            team2Name.setTextColor(it)
                        }
                        else -> return
                    }
                }
                gameLayout.setOnClickListener {
                    clickEvent?.onClick(adapterPosition)
                }
            }
        }
    }
}