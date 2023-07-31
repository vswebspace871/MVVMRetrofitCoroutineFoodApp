package com.example.mvvmretrofitcoroutinefoodapp.pojo

data class PopularMealModel(
    val meals: List<MealPopular>
)

data class MealPopular(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)