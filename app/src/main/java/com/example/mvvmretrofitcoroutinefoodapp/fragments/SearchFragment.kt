package com.example.mvvmretrofitcoroutinefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.activities.MealActivity
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MealAdapter
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentSearchBinding
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var searchAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUprv()

        binding.icSearch.setOnClickListener {
            searchMeal()
        }

        observeSearchMealData()

        onSearchedMealClicked()


        var searchJob : Job? = null
        binding.edSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(1000)
                viewModel.getMealFromSearch(it.toString())
            }
        }


    }

    private fun onSearchedMealClicked() {
        searchAdapter.onClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, it.idMeal)
            intent.putExtra(Constants.MEAL_NAME, it.strMeal)
            intent.putExtra(Constants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun searchMeal() {
        val searchQuery = binding.edSearch.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.getMealById(searchQuery)
        }

    }

    private fun observeSearchMealData() {
        viewModel.searchedMealList.observe(requireActivity(), Observer {
            it?.let {
                searchAdapter.differ.submitList(it.meals)
            }
        })
    }

    private fun setUprv() {
        searchAdapter = MealAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }


}