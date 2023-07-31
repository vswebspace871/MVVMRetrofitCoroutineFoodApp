package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Category
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealPopular
import com.example.mvvmretrofitcoroutinefoodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _mealList = MutableLiveData<Meal>()
    val mealList: LiveData<Meal>
        get() = _mealList

    private val _popularMealList = MutableLiveData<List<MealPopular>>()
    val popularMealList: LiveData<List<MealPopular>>
        get() = _popularMealList

    private val _mealCategoryList = MutableLiveData<List<Category>>()
    val mealCategoryList: LiveData<List<Category>>
        get() = _mealCategoryList


    fun getRandomMeal() {
        viewModelScope.launch {
            val list = RetrofitInstance.api.getRandomMeal().body()
            list?.let {
                _mealList.postValue(it.meals[0])
            }
        }
    }

    fun getTenPopularMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = RetrofitInstance.api.getTenPopularMeal("Seafood").body()
            list?.let {
                _popularMealList.postValue(it.meals)
            }
        }
    }

    fun getMealByCategoryName(categoryName : String){
        viewModelScope.launch(Dispatchers.IO) {
            val list = RetrofitInstance.api.getTenPopularMeal(categoryName).body()
            list?.let {
                _popularMealList.postValue(it.meals)
            }
        }
    }

    fun getMealsCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = RetrofitInstance.api.getMealsCategories().body()
            list?.let {
                _mealCategoryList.postValue(list.categories)
            }
        }
    }
}