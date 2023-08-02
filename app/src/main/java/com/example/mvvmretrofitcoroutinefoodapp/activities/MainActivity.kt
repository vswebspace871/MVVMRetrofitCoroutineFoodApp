package com.example.mvvmretrofitcoroutinefoodapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModel
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModelFactory
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel: FavouriteViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = FavouriteViewModelFactory(mealDatabase)
        viewmodel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        navController = Navigation.findNavController(this, R.id.host_fragment)

        /** connect Bottom navigationView with Navigation Controller*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        increaseCount()

       /* if (Build.VERSION.SDK_INT >= 33) {
           onBackInvokedDispatcher.registerOnBackInvokedCallback(
               OnBackInvokedDispatcher.PRIORITY_DEFAULT
           ) {
               handleBackButtonPressed()
           }
       } else {

           onBackPressedDispatcher.addCallback(
               this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Back is pressed... Finishing the activity
                        handleBackButtonPressed()
                    }
                })

        }*/
    }

   /* private fun handleBackButtonPressed() {
        val currentFragment = findNavController(R.id.host_fragment).currentDestination?.id

        currentFragment?.let {currentFragmentID ->
            if (currentFragmentID == R.id.homeFragment) {
                finish()
            } else if (currentFragmentID == R.id.searchFragment) {
                findNavController(R.id.host_fragment).navigate(R.id.action_searchFragment_to_homeFragment)
            }
        }
    }*/

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