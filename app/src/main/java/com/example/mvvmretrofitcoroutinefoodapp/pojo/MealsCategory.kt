package com.example.mvvmretrofitcoroutinefoodapp.pojo

data class MealsCategory(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)