package com.example.mvvmretrofitcoroutinefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.activities.MealActivity
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentHomeBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealList
import com.example.mvvmretrofitcoroutinefoodapp.retrofit.RetrofitInstance
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_ID
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_NAME
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_THUMB
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var randomMeal : Meal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRandomMeal()
        onRndomMealClick()
    }

    private fun onRndomMealClick() {
        binding.randomMeal.setOnClickListener {
            //val temp = meal.meals[0]
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun getRandomMeal() {
        viewModel.getRandomMeal()
        viewModel.mealList.observe(viewLifecycleOwner, Observer {
            it?.let {
                randomMeal = it
                Glide.with(requireContext()).load(it.strMealThumb)
                    .into(binding.imgRandomMeal)
                binding.mealName.text = it.strMeal
            }

        })
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList?> {
//            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
//                response.body()?.let {
//                   val meal =  it.meals[0]
//                    Log.d("TAG", "onResponse:Image = ${meal.strMealThumb}, ${meal.strMeal}")
//                }
//            }
//
//            override fun onFailure(call: Call<MealList?>, t: Throwable) {
//                Log.d("TAG", "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

}