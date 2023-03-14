package com.develpouk.niu.generalui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.develpouk.niu.R
import com.develpouk.niu.databinding.FragmentMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private lateinit var mainScreenBinding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mainScreenBinding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return mainScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_menu) as NavHostFragment
        val navController = navHost.navController

        mainScreenBinding.menuBotttomNavView.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_matches -> {
                        navController.navigate(R.id.matchFragment)
                        true
                    }
                    R.id.navigation_news-> {
                        navController.navigate(R.id.newsFragment)
                        true
                    }
                    else -> {
                        navController.navigate(R.id.settingsFragment)
                        true
                    }
                }
            }
        }
    }
}