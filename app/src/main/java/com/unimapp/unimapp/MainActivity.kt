package com.unimapp.unimapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.unimapp.unimapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityMainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        setStartGraph()
    }

    private fun setStartGraph() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment? ?: return
        val navController = navHost.navController
        val graph = navHost.navController
            .navInflater.inflate(R.navigation.nav_graph)
        graph.startDestination = findStartGraph()
        navController.graph = graph
    }

    private fun findStartGraph(): Int {
        return when (mainViewModel.isOnBoardFinished()) {
            true -> R.id.authorization_graph
            else -> R.id.onBoardingFragment
        }
    }
}