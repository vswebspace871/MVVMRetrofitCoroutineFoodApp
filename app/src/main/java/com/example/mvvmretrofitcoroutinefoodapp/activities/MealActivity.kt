package com.example.mvvmretrofitcoroutinefoodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.databinding.ActivityMealBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String
    private val viewmodel by viewModels<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomMealInfoFromIntent()
        setInformationInViews()
        getMealDetailById()

        onYoutubeImageClicked()
    }

    private fun onYoutubeImageClicked() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun getMealDetailById() {
        loadingCase()
        viewmodel.getMealDetail(mealId)
        viewmodel.mealDetail.observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                t?.let {
                    binding.tvCategoryInfo.text = "Category : ${t.strCategory}"
                    binding.tvAreaInfo.text = "Area : ${t.strArea}"
                    binding.tvInstructions.text = "Instructions : " + t.strInstructions

                    youtubeLink = t.strYoutube

                }
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(this).load(mealThumb)
            .into(binding.imgMealDetail)
        binding.imgMealDetail.background =
            ContextCompat.getDrawable(
                this,
                R.drawable.image_gradient
            )
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
    }

    private fun getRandomMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(Constants.MEAL_ID).toString()
        mealName = intent.getStringExtra(Constants.MEAL_NAME).toString()
        mealThumb = intent.getStringExtra(Constants.MEAL_THUMB).toString()

    }

    private fun loadingCase() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            container.visibility = View.INVISIBLE
            btnSave.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        binding.apply {
            progressBar.visibility = View.GONE
            container.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
        }
    }
}