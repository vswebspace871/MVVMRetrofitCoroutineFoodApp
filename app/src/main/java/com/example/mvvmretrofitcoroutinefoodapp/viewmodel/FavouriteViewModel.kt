package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(
    val mealDatabase: MealDatabase,
) : ViewModel() {
    private var _mealfromRoom = MutableLiveData<List<Meal>>()
    val mealfromRoom: LiveData<List<Meal>>
        get() = _mealfromRoom

    fun getMealFromRoomDb(){
        viewModelScope.launch(Dispatchers.IO) {
            _mealfromRoom.postValue(mealDatabase.mealdao().getAllMeals())
        }
    }

}