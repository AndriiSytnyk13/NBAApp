package com.develpouk.niu.news.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.ItemNewsBinding
import com.develpouk.niu.interfaces.ClickEvent
import com.develpouk.niu.news.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var clickEvent: ClickEvent

    private val news = mutableListOf<Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(news: List<Article>) {
        this.news.clear()
        this.news.addAll(news)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindViews(news[position])
    }

    inner class NewsViewHolder(private val newsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {

        fun bindViews(article: Article) {
            newsBinding.apply {
                if (article.urlToImage == null) {
                    newsBinding.newsImageview.setImageResource(R.drawable.no_news_image)
                } else {
                    Glide.with(newsBinding.root).load(article.urlToImage).fitCenter().into(newsImageview)
                }
                newsHeader.text = article.title
                newsDate.text = article.publishedAt
                newsLayout.setOnClickListener {
                    clickEvent.onClick(adapterPosition)
                }
            }
        }
    }
}