package com.example.grocery_shopping_list.listAdapter

import android.content.Context
import android.system.Os.bind
import android.text.InputType
import android.util.Log
import android.util.Log.DEBUG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_shopping_list.BuildConfig.DEBUG
import com.example.grocery_shopping_list.MainActivity
import com.example.grocery_shopping_list.R
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private var list: MutableList<String>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>(){

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
            val mytoast = Toast(textView.context)
            if (mytoast != null){
                mytoast.cancel()
            }
            Toast.makeText(textView.context, s, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.textView.text = list[position]
            holder.textViewList.text = "${position + 1}."
            tag = position
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() : Int{
        return list.size
    }
}