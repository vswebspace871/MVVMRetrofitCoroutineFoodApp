package com.example.mvvmretrofitcoroutinefoodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.databinding.ActivityMealBinding
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.MealViewModel
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.MealViewModelFactory
import kotlinx.coroutines.delay

class MealActivity : AppCompatActivity() {
    /** meal is not added in favourite */
    private var isMealFavourite: Boolean = false

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String
    //private val viewmodel by viewModels<MealViewModel>()
    private lateinit var mealMVVM: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this@MealActivity)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMVVM = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getRandomMealInfoFromIntent()
        setInformationInViews()
        getMealDetailById()

        onYoutubeImageClicked()

        onFavouriteClicked()
    }

    private fun onFavouriteClicked() {
        binding.btnSave.setOnClickListener {
            mealMVVM.mealDetail.observe(this, Observer {
                it?.let {
                    mealMVVM.insertMeal(it)
                    Toast.makeText(this, "Meal is Added", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun onYoutubeImageClicked() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun getMealDetailById() {
        loadingCase()
        mealMVVM.getMealDetail(mealId)
        mealMVVM.mealDetail.observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                t?.let {
                    binding.tvCategoryInfo.text = "Category : ${t.strCategory}"
                    binding.tvAreaInfo.text = "Area : ${t.strArea}"
                    binding.tvInstructions.text = "Instructions : " + t.strInstructions

                    youtubeLink = t.strYoutube.toString()
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