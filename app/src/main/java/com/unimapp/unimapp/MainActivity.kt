package com.unimapp.unimapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.unimapp.common.extensions.gone
import com.unimapp.common.extensions.show
import com.unimapp.profile.ProfileFragment
import com.unimapp.unimapp.databinding.ActivityMainBinding
import com.unimapp.unimapp.databinding.AddPostLayoutBinding
import com.unimapp.unimapp.databinding.MyProfileLayoutBinding
import com.unimapp.unimapp.databinding.NotificationBadgeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val hiddenBottomNavigationViews by lazy {
        setOf(
            R.id.onBoardingFragment,
            R.id.signInFragment,
            R.id.signUpFragment,
            R.id.signInWithEmailFragment,
            R.id.setPasswordFragment,
            R.id.addPostFragment,
            R.id.tagFriendFragment,
            R.id.commentsFragment,
            R.id.peersFragment
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
            navController = navHost.navController
            val graph = navHost.navController
                .navInflater.inflate(R.navigation.nav_graph)
            graph.startDestination = findStartGraph()
            navController.graph = graph
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                if (destination.id !in hiddenBottomNavigationViews)
                    bottomNavigationMenu.show()
                else
                    bottomNavigationMenu.gone()
            }
            setupBottomMenuCustomLayout()
            NavigationUI.setupWithNavController(bottomNavigationMenu, navController)
        }
    }

    private fun setupBottomMenuCustomLayout() {
        with(mainActivityMainBinding) {
            val bottomMenuView = bottomNavigationMenu.getChildAt(0) as BottomNavigationMenuView
            //Add post menu
            val addPostMenuItem = bottomMenuView.getChildAt(2) as BottomNavigationItemView
            val addPostMenuItemCustomView = AddPostLayoutBinding.inflate(LayoutInflater.from(this@MainActivity), bottomMenuView, false)
            addPostMenuItemCustomView.icon.setOnClickListener {
                showAddPostFragment()
            }
            addPostMenuItem.addView(addPostMenuItemCustomView.root)
            //Profile
            val profileMenuItem = bottomMenuView.getChildAt(4) as BottomNavigationItemView
            val profileMenuItemCustomView = MyProfileLayoutBinding.inflate(LayoutInflater.from(this@MainActivity), bottomMenuView, false)
            profileMenuItemCustomView.profileImage.setOnClickListener {
                showProfileFragment()
            }
            profileMenuItem.addView(profileMenuItemCustomView.root)
            //Notification icon
            val itemView = bottomMenuView.getChildAt(3) as BottomNavigationItemView
            NotificationBadgeBinding.inflate(LayoutInflater.from(this@MainActivity), itemView)
            showNotification(3, "5")
        }
    }

    private fun findStartGraph(): Int {
        return when {
            mainViewModel.isAuthTokenAvailable() -> R.id.home_nav_graph
            mainViewModel.isRegisteredUser() -> R.id.authorization_graph
            mainViewModel.isOnBoardFinished() -> R.id.sign_in_graph
            else -> R.id.onBoardingFragment
        }
    }

    private fun showNotification(index: Int, count: String) {
        val bottomMenuView = mainActivityMainBinding.bottomNavigationMenu.getChildAt(0) as BottomNavigationMenuView
        bottomMenuView.getChildAt(index).findViewById<TextView>(R.id.notifications_badge).text = count
    }

    private fun showAddPostFragment() {
        navController.navigate(R.id.addPostFragment)
    }

    private fun showProfileFragment() {
        navController.navigate(R.id.profile_nav_graph)
    }
}