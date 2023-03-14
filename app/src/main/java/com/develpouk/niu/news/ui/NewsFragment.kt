package com.develpouk.niu.news.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentNewsBinding
import com.develpouk.niu.extensions.errorSnackBar
import com.develpouk.niu.generalui.MainScreenFragmentDirections
import com.develpouk.niu.interfaces.ClickEvent
import com.develpouk.niu.interfaces.Collector
import com.develpouk.niu.news.ui.adapter.NewsAdapter
import com.develpouk.niu.util.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(), Collector {

    private lateinit var newsFragmentBinding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        newsFragmentBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return newsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectValues()

    }

    override fun collectValues() {
        val adapter = NewsAdapter()
        newsFragmentBinding.newsRecyclerview.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.newsResponse.collect { response ->
                    when (response) {
                        is NetworkResponse.Success -> {
                            adapter.clickEvent = object : ClickEvent {
                                override fun onClick(position: Int) {
                                    val action =
                                        MainScreenFragmentDirections.actionMainScreenFragmentToNewsFragmentDetail(
                                            response.data[position]
                                        )
                                    requireActivity().findNavController(R.id.nav_host_main)
                                        .navigate(
                                            action
                                        )
                                }
                            }
                            newsFragmentBinding.mainNewsLayout.setOnClickListener {
                                val action =
                                    MainScreenFragmentDirections.actionMainScreenFragmentToNewsFragmentDetail(
                                        response.data[0]
                                    )
                                requireActivity().findNavController(R.id.nav_host_main)
                                    .navigate(action)
                            }
                            successViews()
                            adapter.setNews(response.data)
                            newsFragmentBinding.newsMainHeader.text = response.data[0].title
                            newsFragmentBinding.dateMainHeader.text = response.data[0].publishedAt
                            if (response.data[0].urlToImage == null) {
                                newsFragmentBinding.imageLayout.setImageResource(R.drawable.no_news_image)
                            } else {
                                Glide.with(requireActivity()).load(response.data[0].urlToImage)
                                    .fitCenter().into(newsFragmentBinding.imageLayout)
                            }
                        }
                        is NetworkResponse.Error -> errorViews(response.message)
                        else -> loadingViews()
                    }
                }
            }
        }
    }

    private fun successViews() {
        newsFragmentBinding.newsRecyclerview.visibility = View.VISIBLE
        newsFragmentBinding.newsProgressBar.visibility = View.GONE
        newsFragmentBinding.mainNewsLayout.visibility = View.VISIBLE
    }

    private fun loadingViews() {
        newsFragmentBinding.newsRecyclerview.visibility = View.GONE
        newsFragmentBinding.newsProgressBar.visibility = View.VISIBLE
        newsFragmentBinding.mainNewsLayout.visibility = View.GONE
    }

    private fun errorViews(message: String) {
        newsFragmentBinding.newsProgressBar.visibility = View.GONE
        errorSnackBar(message) {
        }
    }

}