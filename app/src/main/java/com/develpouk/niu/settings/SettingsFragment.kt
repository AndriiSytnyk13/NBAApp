package com.develpouk.niu.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.develpouk.niu.BuildConfig
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        FragmentSettingsBinding.inflate(inflater, container, false).run {
            privacyPolicyLayout.setOnClickListener {
                showPolicy()
            }
            shareAppLayout.setOnClickListener {
                shareApp()
            }
            rateAppLayout.setOnClickListener {
                rateApp()
            }
            return root
        }
    }

    private fun rateApp() {
        requireActivity().findNavController(R.id.nav_host_main).navigate(R.id.rateAppFragment)
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            }
            var shareMessage = "\n${getString(R.string.shareMessage)}\n\n"
            shareMessage =
                """${shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID} """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_available_app),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showPolicy() {
        requireActivity().findNavController(R.id.nav_host_main).navigate(R.id.policyFragment)
    }


}