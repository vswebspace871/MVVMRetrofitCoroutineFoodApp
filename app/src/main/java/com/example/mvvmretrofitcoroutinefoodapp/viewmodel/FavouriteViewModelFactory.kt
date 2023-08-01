package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase

class FavouriteViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(mealDatabase) as T
    }
}