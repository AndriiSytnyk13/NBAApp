package com.develpouk.niu.news.ui

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentNewsDetailBinding


class NewsFragmentDetail : Fragment() {

    private lateinit var newsDetailBinding: FragmentNewsDetailBinding
    private val newsDetailArgs: NewsFragmentDetailArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        newsDetailBinding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return newsDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        newsDetailBinding.apply {
            newsDetailArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
            if (newsDetailArgs.article.urlToImage == null) {
                newsDetailImageView.setImageResource(R.drawable.no_news_image)
            } else {
                Glide.with(requireActivity()).load(newsDetailArgs.article.urlToImage)
                    .placeholder(R.drawable.no_news_image)
                    .fitCenter().into(newsDetailImageView)
            }
            newsDetailHeader.text = newsDetailArgs.article.title
            dateDetailHeader.text = newsDetailArgs.article.publishedAt
            newsDetailText.text = newsDetailArgs.article.description
            if(newsDetailArgs.article.url == null) {
                newsDetailUrlTextview.visibility = View.GONE
            }
            newsDetailUrlTextview.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            newsDetailUrlTextview.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(newsDetailArgs.article.url)
                startActivity(intent)
            }
        }
    }
}
