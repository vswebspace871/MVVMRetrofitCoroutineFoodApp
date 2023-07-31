package com.example.mvvmretrofitcoroutinefoodapp.retrofit

import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
//    fun getRandomMeal() : Call<MealList>
    suspend fun getRandomMeal(): Response<MealList>

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): Response<MealList>
}