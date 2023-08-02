package com.example.mvvmretrofitcoroutinefoodapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.R
import com.example.mvvmretrofitcoroutinefoodapp.activities.MealActivity
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentMealBottomSheetBinding
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var mealThumb: String
    private lateinit var mealName: String
    private var mealId: String? = null
    private lateinit var binding : FragmentMealBottomSheetBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { viewModel.getMealById(it) }

        observeBottomSheetMeal()

        onBottomSheetDialogFragmentCLicked()
    }

    private fun onBottomSheetDialogFragmentCLicked() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(requireContext(), MealActivity::class.java)
                intent.putExtra(Constants.MEAL_ID, mealId)
                intent.putExtra(Constants.MEAL_NAME, mealName)
                intent.putExtra(Constants.MEAL_THUMB, mealThumb)
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheetMeal() {
        viewModel.bottomSheetMeal.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.strMealThumb).into(binding.imgCategory)
            binding.tvMealNameInBtmsheet.text = it.strMeal
            binding.tvMealCategory.text = it.strCategory
            binding.tvMealCountry.text = it.strArea

            mealName = it.strMeal.toString()
            mealThumb = it.strMealThumb.toString()
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}