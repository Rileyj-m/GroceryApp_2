package com.example.grocery_shopping_list.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_shopping_list.Item
import com.example.grocery_shopping_list.databinding.ListItemBinding

class GroceryListAdapter(
    private val onItemLongClick: (String) -> Boolean
): ListAdapter<String, GroceryListAdapter.ViewHolder>(DiffCallBack){

    companion object DiffCallBack : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(old: String, new: String): Boolean {
            return old == new
        }

        override fun areContentsTheSame(old: String, new: String): Boolean {
            return old == new
        }
    }

    class ViewHolder(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int){
            binding.itemName.text = item
            binding.number.text = position.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val viewHolder = ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnLongClickListener{
            val position = viewHolder.adapterPosition
            onItemLongClick(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}