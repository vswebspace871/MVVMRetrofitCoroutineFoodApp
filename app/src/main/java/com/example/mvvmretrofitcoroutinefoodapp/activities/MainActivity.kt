package com.example.mvvmretrofitcoroutinefoodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModel
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModelFactory
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel : FavouriteViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = FavouriteViewModelFactory(mealDatabase)
        viewmodel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        /** connect Bottom navigationView with Navigation Controller*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        increaseCount()
    }

    fun increaseCount() {
        viewmodel.getMealFromRoomDb()
        viewmodel.mealfromRoom.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty())
                    bottomNavigationView.getOrCreateBadge(R.id.favouriteFragment).number = it.size
            }
        })
    }

}