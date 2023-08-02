package com.example.mvvmretrofitcoroutinefoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.databinding.FavMealCardBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.PopularViewHolder>() {
     lateinit var onClick : ((Meal)->Unit)
    private var mealList = ArrayList<Meal>()

    inner class PopularViewHolder(val binding: FavMealCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            FavMealCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView).load(differ.currentList[position].strMealThumb)
            .into(holder.binding.imgFavMeal)
        holder.binding.tvFavMealName.text = differ.currentList[position].strMeal
        holder.itemView.setOnClickListener {
            onClick.invoke(differ.currentList[position])
        }
    }
}