package com.develpouk.niu.settings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentPolicyBinding
import kotlinx.coroutines.launch


class PolicyFragment : Fragment(R.layout.fragment_policy) {

    private val policyUrl = "https://sites.google.com/view/privpolparivacy/%CE%B1%CF%81%CF%87%CE%B9%CE%BA%CE%AE-%CF%83%CE%B5%CE%BB%CE%AF%CE%B4%CE%B1"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPolicyBinding.bind(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.apply {
                    policyView.settings.javaScriptEnabled = true
                    policyView.webViewClient = WebViewClient()
                    policyView.loadUrl(policyUrl)
                }
            }
        }
    }
}