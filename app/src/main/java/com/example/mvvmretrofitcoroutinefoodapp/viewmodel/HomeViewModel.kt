package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _mealList = MutableLiveData<Meal>()
    val mealList: LiveData<Meal>
        get() = _mealList

    fun getRandomMeal() {
        viewModelScope.launch {
            val list = RetrofitInstance.api.getRandomMeal().body()
            list?.let {
                _mealList.postValue(it.meals[0])
            }
        }
    }
}