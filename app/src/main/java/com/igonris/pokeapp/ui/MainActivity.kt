package com.igonris.pokeapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.igonris.pokeapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var splashScreen: androidx.core.splashscreen.SplashScreen


    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        controlSplashScreen()
        setContentView(R.layout.activity_main)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(navController = navHost.navController)

        viewModel.countDown()
    }

    private fun initSplashScreen() {
        this.splashScreen = installSplashScreen()
            .apply {
                setKeepOnScreenCondition { true }
            }
    }

    private fun controlSplashScreen() {
        viewModel.goHome.observe(this) { goHome ->
            if (goHome) {
                splashScreen.setKeepOnScreenCondition { false }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() ||
                super.onSupportNavigateUp()
    }
}