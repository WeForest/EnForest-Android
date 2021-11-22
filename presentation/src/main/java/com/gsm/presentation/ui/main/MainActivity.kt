package com.gsm.presentation.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gsm.presentation.R
import com.gsm.presentation.base.BaseActivity
import com.gsm.presentation.databinding.ActivityMainBinding
import com.gsm.presentation.ui.study.group.CommunityFragment
import dagger.hilt.android.AndroidEntryPoint

// ViewBinding
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    lateinit var navi: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        navi = binding.bottomNavigationView
        navController = findNavController(R.id.navHostFragment)
        //앱 바 구성성
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment,
                R.id.communityFragment,
                R.id.missionFragment,

            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navi.setupWithNavController(navController)
        initNavigation()


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initNavigation() {

        val navController = findNavController(R.id.navHostFragment)
        navi.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // fragment id 가 아닐 시 bottom navigation 안뜸
            if (destination.id == R.id.communityFragment) {
            } else {
                supportActionBar?.hide()
            }
        }
    }


}