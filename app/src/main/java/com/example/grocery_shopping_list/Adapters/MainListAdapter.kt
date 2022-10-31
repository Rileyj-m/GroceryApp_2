package com.example.grocery_shopping_list.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.grocery_shopping_list.Item
import com.example.grocery_shopping_list.databinding.ListItemBinding
import java.text.FieldPosition

/*class ListAdapter(private var list: MutableList<String>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    var toastMessage: Toast? = null

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        val textView: TextView = view.findViewById(R.id.item_name)
        val textViewList: TextView = view.findViewById(R.id.number)
        val duplicate: ImageView = view.findViewById(R.id.copy)
        val remove: ImageView = view.findViewById(R.id.remove)

        init {
            view.setOnLongClickListener(this)

            duplicate.setOnClickListener{
                makeToast("Duplicated: " + list[view?.tag as Int])
                list.add(textView.text as String)
                notifyDataSetChanged()
            }

            remove.setOnClickListener{
                makeToast("Removed: " + list[view?.tag as Int])
                list.removeAt(view?.tag as Int)
                notifyDataSetChanged()
            }
        }

        override fun onLongClick(view: View?): Boolean {
            makeToast("Removed: " + list[view?.tag as Int])
            list.removeAt(view?.tag as Int)
            notifyDataSetChanged()
            return true
        }

        private fun makeToast(s : String){
            toastMessage?.cancel()
            toastMessage = Toast.makeText(textView.context, s, Toast.LENGTH_SHORT)
            toastMessage?.show()
        }
    }

    *//**
     * Create new views (invoked by the layout manager)
     *//*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(adapterLayout)
    }

    *//**
     * Replace the contents of a view (invoked by the layout manager)
     *//*
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.textView.text = list[position]
            holder.textViewList.text = "${position + 1}."
            tag = position
        }
    }

    *//**
     * Return the size of your dataset (invoked by the layout manager)
     *//*
    override fun getItemCount() : Int{
        return list.size
    }
}*/
class MainListAdapter(
    private val onItemClick: (Item) -> Unit, private val onItemLongClick: (Item) -> Boolean
): ListAdapter<Item, MainListAdapter.ViewHolder>(DiffCallBack){

    companion object DiffCallBack : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(old: Item, new: Item): Boolean {
            return old.itemName == new.itemName
        }

        override fun areContentsTheSame(old: Item, new: Item): Boolean {
            return old == new
        }
    }

    class ViewHolder(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, position: Int){
            binding.itemName.text = item.itemName.toString()
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
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            onItemClick(getItem(position))
        }
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