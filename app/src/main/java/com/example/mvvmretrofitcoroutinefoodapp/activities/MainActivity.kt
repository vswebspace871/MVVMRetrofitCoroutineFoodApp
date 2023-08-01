package com.example.mvvmretrofitcoroutinefoodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        /** connect Bottom navigationView with Navigation Controller*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun increaseCount(count: Int) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        if (count > 0)
            bottomNavigationView.getOrCreateBadge(R.id.favouriteFragment).number = count
        else
            bottomNavigationView.getOrCreateBadge(R.id.favouriteFragment).number = 0
    }

}