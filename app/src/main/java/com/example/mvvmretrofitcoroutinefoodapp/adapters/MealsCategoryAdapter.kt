package com.example.mvvmretrofitcoroutinefoodapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutinefoodapp.databinding.CategoryCardBinding
import com.example.mvvmretrofitcoroutinefoodapp.databinding.MostPopularCardBinding
import com.example.mvvmretrofitcoroutinefoodapp.pojo.Category
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealPopular
import com.example.mvvmretrofitcoroutinefoodapp.pojo.MealsCategory

class MealsCategoryAdapter : RecyclerView.Adapter<MealsCategoryAdapter.CategoryViewHolder>() {
     lateinit var onClick : ((Category)->Unit)
    private var categoryList = ArrayList<Category>()

    fun setMealCategoryList(list: ArrayList<Category>) {
        this.categoryList = list
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onClick.invoke(categoryList[position])
        }
    }
}