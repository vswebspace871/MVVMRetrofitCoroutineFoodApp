package com.example.mvvmretrofitcoroutinefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmretrofitcoroutinefoodapp.activities.CategoryMealsActivity
import com.example.mvvmretrofitcoroutinefoodapp.adapters.CategoryMealsAdapter
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MealsCategoryAdapter
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentCategoriesBinding
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Category
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.util.hide
import com.example.mvvmretrofitcoroutinefoodapp.util.show
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: MealsCategoryAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mealDatabase = MealDatabase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerview()

        getMealCategories()

        onCategoryClicked()
    }

    private fun onCategoryClicked() {
        categoryAdapter.onClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(Constants.MEAL_CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun getMealCategories() {
        binding.progressBar2.show()
        viewModel.getMealsCategory()
        viewModel.mealCategoryList.observe(viewLifecycleOwner, Observer {
            it?.let {
                val tempList = it.filter { it.strCategory != "Beef"}
                val tList = tempList.filter { cat-> cat.strCategory != "Goat" }
                categoryAdapter.setMealCategoryList(tList as ArrayList<Category>)
                binding.progressBar2.hide()
            }
        })
    }

    private fun setUpRecyclerview() {
        categoryAdapter = MealsCategoryAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }
}