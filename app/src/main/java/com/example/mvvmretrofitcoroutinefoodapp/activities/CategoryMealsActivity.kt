package com.example.mvvmretrofitcoroutinefoodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.adapters.CategoryMealsAdapter
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MostPopularAdapter
import com.example.mvvmretrofitcoroutinefoodapp.databinding.ActivityCategoryMealsBinding
import com.example.mvvmretrofitcoroutinefoodapp.databinding.ActivityMealBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealPopular
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryMealsBinding
    private val viewmodel by viewModels<HomeViewModel>()
    private var categoryAdapter = CategoryMealsAdapter()
    private lateinit var mealCategory: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealCategoryInfoFromIntent()

        getMealByCategoryName()

        setupPopularMealRv()
        onMealByCategoryClicked()
    }

    private fun onMealByCategoryClicked() {
        categoryAdapter.onClick = {
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, it.idMeal)
            intent.putExtra(Constants.MEAL_NAME, it.strMeal)
            intent.putExtra(Constants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setupPopularMealRv() {
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun getMealByCategoryName() {
        viewmodel.getMealByCategoryName(mealCategory)
        viewmodel.popularMealList.observe(this, Observer {
            it?.let {
                binding.tvCategoryCount.text = "${mealCategory} : ${it.size}"
                categoryAdapter.setMealList(it as ArrayList<MealPopular>)
            }
        })
    }


    private fun getMealCategoryInfoFromIntent() {
        val intent = intent
        mealCategory = intent.getStringExtra(Constants.MEAL_CATEGORY_NAME).toString()
    }
}