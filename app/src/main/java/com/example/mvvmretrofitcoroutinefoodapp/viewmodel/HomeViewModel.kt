package com.example.mvvmretrofitcoroutinefoodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Category
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealList
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

    private val _bottomSheetMeal = MutableLiveData<Meal>()
    val bottomSheetMeal: LiveData<Meal>
        get() = _bottomSheetMeal

    private val _searchedMealList = MutableLiveData<MealList>()
    val searchedMealList: LiveData<MealList>
        get() = _searchedMealList

    private var saveStateRandomMeal : Meal? = null
    fun getRandomMeal() {
        saveStateRandomMeal?.let {
            _mealList.postValue(it)
            return
        }
        viewModelScope.launch {
            val list = RetrofitInstance.api.getRandomMeal().body()
            list?.let {
                _mealList.postValue(it.meals[0])
                saveStateRandomMeal = it.meals[0]
            }
        }
    }

    fun getMealFromSearch(search : String){
        viewModelScope.launch {
            val list = RetrofitInstance.api.getMealFromSearch(search).body()
            list?.let {
                _searchedMealList.postValue(it)
            }
        }
    }

    fun getMealById(id : String){
        viewModelScope.launch {
            val meal = RetrofitInstance.api.getMealDetail(id).body()?.meals
            if (meal != null){
                _bottomSheetMeal.postValue(meal[0])
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