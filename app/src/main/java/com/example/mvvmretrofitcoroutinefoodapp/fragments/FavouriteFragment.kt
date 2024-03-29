package com.example.mvvmretrofitcoroutinefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofitcoroutinefoodapp.activities.MainActivity
import com.example.mvvmretrofitcoroutinefoodapp.activities.MealActivity
import com.example.mvvmretrofitcoroutinefoodapp.adapters.MealAdapter
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FragmentFavouriteBinding
import com.example.mvvmretrofitcoroutinefoodapp.db.MealDatabase
import com.example.mvvmretrofitcoroutinefoodapp.util.Constants
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModel
import com.example.mvvmretrofitcoroutinefoodapp.viewmodel.FavouriteViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewmodel: FavouriteViewModel
    private lateinit var favadapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealDatabase = MealDatabase.getInstance(requireContext())
        val viewModelFactory = FavouriteViewModelFactory(mealDatabase)
        viewmodel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        getListofMealFromRoom()

        onFavMealClicked()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewmodel.deleteMealFromRoomDb(favadapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", View.OnClickListener {
                        viewmodel.insertMeal(favadapter.differ.currentList[position])
                    }).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)

    }

    private fun onFavMealClicked() {
        favadapter.onClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, it.idMeal)
            intent.putExtra(Constants.MEAL_NAME, it.strMeal)
            intent.putExtra(Constants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setupFavMealRecylerview() {
        favadapter = MealAdapter()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favadapter
        }
    }

    private fun getListofMealFromRoom() {
        setupFavMealRecylerview()
        /** setting up recyclerview */
        viewmodel.getMealFromRoomDb()
        /** calling method from viewmodel */
        viewmodel.mealfromRoom.observe(requireActivity(), Observer {
            /** Observing the data from Room */
            if (it == null) {
                Log.d("TAG", "getListofMealFromRoom: Error")
                Toast.makeText(
                    requireContext(),
                    "Error in Fetching Data from Room ",
                    Toast.LENGTH_LONG
                ).show()
            }
            it?.let {
                val myAct: MainActivity? = activity as MainActivity?
                myAct?.increaseCount()
                favadapter.differ.submitList(it)

            }
        })
    }


}