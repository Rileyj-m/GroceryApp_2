package com.example.grocery_shopping_list.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_shopping_list.databinding.RecipeListBinding

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>(){
    var items = ArrayList<String>()

    fun setDataList(data: ArrayList<String>?){
        if (data != null) {
            this.items = data
        }
    }

    class ViewHolder(private var binding: RecipeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String?, position: Int){
            val position = position + 1
            binding.RecipeIngredient.text = data.toString()
            binding.ingredientsItemNumber.text = position.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val viewHolder = ViewHolder(
            RecipeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}