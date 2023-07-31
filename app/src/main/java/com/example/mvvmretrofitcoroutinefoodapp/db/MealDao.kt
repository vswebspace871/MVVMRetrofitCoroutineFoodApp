package com.example.mvvmretrofitcoroutinefoodapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal : Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("Select * From meal_information")
    fun getAllMeals() : LiveData<List<Meal>>

}