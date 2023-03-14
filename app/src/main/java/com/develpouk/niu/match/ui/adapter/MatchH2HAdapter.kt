package com.develpouk.niu.match.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develpouk.niu.databinding.ItemHeadToHeadBinding
import com.develpouk.niu.match.model.MatchModel

class MatchH2HAdapter(private val context: Context) : RecyclerView.Adapter<MatchH2HViewHolder>() {

    private val h2hMatches = mutableListOf<MatchModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun seth2hMatches(h2hMatches: List<MatchModel>) {
        this.h2hMatches.clear()
        this.h2hMatches.addAll(h2hMatches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchH2HViewHolder =
        MatchH2HViewHolder(
            ItemHeadToHeadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
    )

    override fun getItemCount(): Int  = h2hMatches.size

    override fun onBindViewHolder(holder: MatchH2HViewHolder, position: Int) {
        holder.bindViews(h2hMatches[position])
    }
}