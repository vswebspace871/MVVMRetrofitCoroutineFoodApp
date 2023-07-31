package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(
    val mealDatabase: MealDatabase,
) : ViewModel() {

    private val _mealDetail = MutableLiveData<Meal>()
    val mealDetail: LiveData<Meal>
        get() = _mealDetail

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            val mealDetail = RetrofitInstance.api.getMealDetail(id)
            if (mealDetail.body() != null) {
                mealDetail.let {
                    _mealDetail.postValue(it.body()?.meals!![0])
                }
            }

        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabase.mealdao().insertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabase.mealdao().deleteMeal(meal)
        }
    }
}