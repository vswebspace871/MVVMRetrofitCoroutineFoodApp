package com.example.mvvmretrofitcoroutinefoodapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.databinding.MostPopularCardBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealPopular

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>() {
     lateinit var onClick : ((MealPopular)->Unit)
    private var mealList = ArrayList<MealPopular>()

    fun setMealList(list: ArrayList<MealPopular>) {
        this.mealList = list
        notifyDataSetChanged()
    }

    inner class PopularViewHolder(val binding: MostPopularCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            MostPopularCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)
        holder.itemView.setOnClickListener {
            onClick.invoke(mealList[position])
        }
    }
}