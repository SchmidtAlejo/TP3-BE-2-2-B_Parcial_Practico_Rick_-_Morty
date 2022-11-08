package com.rickandmorty.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rickandmorty.R

class MainActivity : AppCompatActivity() {


    private lateinit var navHostFragment: NavHostFragment
    private lateinit var activityLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLayout = findViewById(R.id.activityMainLayoutId)
        navigationView = findViewById(R.id.activityMainDrawerNavigatorId)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.activityMainNavHostFragmentId) as NavHostFragment

        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {

        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        navigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, activityLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navHostFragment.navController, activityLayout)
    }

    /*override fun onSupportNavigateUp(): Boolean {
       if (activityLayout.isDrawerOpen(GravityCompat.START)) {
           activityLayout.closeDrawer(GravityCompat.START)
        } else {
           activityLayout.openDrawer(GravityCompat.START)
        }

        return false
    }*/
}