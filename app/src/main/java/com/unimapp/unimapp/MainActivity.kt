package com.unimapp.unimapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.unimapp.common.extensions.gone
import com.unimapp.common.extensions.show
import com.unimapp.unimapp.databinding.ActivityMainBinding
import com.unimapp.unimapp.databinding.NotificationBadgeBinding
import com.unimapp.unimapp.ui.authorization.siginwithemail.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityMainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val hiddenBottomNavigationViews by lazy {
        setOf(
            R.id.onBoardingFragment,
            R.id.signInFragment,
            R.id.signUpFragment,
            R.id.signInWithEmailFragment,
            R.id.setPasswordFragment,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        setStartGraph()
    }

    private fun setStartGraph() {
        with(mainActivityMainBinding) {
            val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment? ?: return
            val navController = navHost.navController
            val graph = navHost.navController
                .navInflater.inflate(R.navigation.nav_graph)
            graph.startDestination = findStartGraph()
            navController.graph = graph
            NavigationUI.setupWithNavController(bottomNavigationMenu, navController)
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                if (destination.id !in hiddenBottomNavigationViews)
                    bottomNavigationMenu.show()
                else
                    bottomNavigationMenu.gone()
            }

            setupBottomMenuCustomLayout()
        }
    }

    private fun setupBottomMenuCustomLayout() {
        with(mainActivityMainBinding) {
            val bottomMenuView = bottomNavigationMenu.getChildAt(0) as BottomNavigationMenuView
            val addPostMenuItem = bottomMenuView.getChildAt(2) as BottomNavigationItemView
            val addPostMenuItemCustomView = LayoutInflater.from(this@MainActivity).inflate(R.layout.add_post_layout, bottomMenuView, false)
            addPostMenuItem.addView(addPostMenuItemCustomView)
            val profileMenuItem = bottomMenuView.getChildAt(4) as BottomNavigationItemView
            val profileMenuItemCustomView = LayoutInflater.from(this@MainActivity).inflate(R.layout.my_profile_layout, bottomMenuView, false)
            profileMenuItem.addView(profileMenuItemCustomView)
            val itemView = bottomMenuView.getChildAt(3) as BottomNavigationItemView
            NotificationBadgeBinding.inflate(LayoutInflater.from(this@MainActivity), itemView)
            showNotification(3, "5")
        }
    }

    private fun findStartGraph(): Int {
        return when {
            mainViewModel.isRegisteredUser() -> R.id.home_nav_graph
            mainViewModel.isOnBoardFinished() -> R.id.authorization_graph
            else -> R.id.onBoardingFragment
        }
    }

    private fun showNotification(index: Int, count: String) {
        val bottomMenuView = mainActivityMainBinding.bottomNavigationMenu.getChildAt(0) as BottomNavigationMenuView
        bottomMenuView.getChildAt(index).findViewById<TextView>(R.id.notifications_badge).text = count
    }
}