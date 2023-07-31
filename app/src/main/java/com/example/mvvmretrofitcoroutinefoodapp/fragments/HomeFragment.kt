package com.example.mvvmretrofitcoroutinefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.activities.CategoryMealsActivity
import com.example.mvvmretrofitcoroutinefoodapp.activities.MealActivity
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MealsCategoryAdapter
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MostPopularAdapter
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentHomeBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Category
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealPopular
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_CATEGORY_NAME
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_ID
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_NAME
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants.MEAL_THUMB
import com.example.mvvmretrofitcoroutinefoodapp.util.hide
import com.example.mvvmretrofitcoroutinefoodapp.util.show
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var randomMeal: Meal
    private var popularMealAdapter = MostPopularAdapter()
    private var adapterCategory = MealsCategoryAdapter()
    private var meal_List = mutableListOf<MealPopular>()

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
        /** Random Meal */
        getRandomMeal()
        onRandomMealClick()

        /** Ten Popular Meal */
        getTenPopularMeal()
        setupPopularMealRv()

        onPopularMealClick()

        /** meals all category */
        getMealCategory()
        setUpCategoryRecyclerView()
        onMealCategoryClicked()
    }

    private fun onMealCategoryClicked() {
        adapterCategory.onClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(MEAL_CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun setUpCategoryRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = adapterCategory
        }
    }

    private fun getMealCategory() {
        viewModel.getMealsCategory()
        viewModel.mealCategoryList.observe(viewLifecycleOwner, Observer { it ->
            val tempList = it.filter { it.strCategory != "Beef"}
            val tList = tempList.filter { cat-> cat.strCategory != "Goat" }
            adapterCategory.setMealCategoryList(tList as ArrayList<Category>)
        })
    }

    private fun onPopularMealClick() {
        popularMealAdapter.onClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setupPopularMealRv() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMealAdapter
        }
    }

    private fun getTenPopularMeal() {
        viewModel.getTenPopularMeal()
        viewModel.popularMealList.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.forEach { popMeal ->
                    if (meal_List.size < 10) {
                        meal_List.add(popMeal)
                    }
                }
            }
            popularMealAdapter.setMealList(meal_List as ArrayList<MealPopular>)
        })
    }

    private fun onRandomMealClick() {
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
        binding.pgBar.show()
        binding.imgRandomMeal.hide()

        viewModel.getRandomMeal()
        viewModel.mealList.observe(viewLifecycleOwner, Observer {
            it?.let {
                randomMeal = it

                binding.pgBar.hide()
                binding.imgRandomMeal.show()

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