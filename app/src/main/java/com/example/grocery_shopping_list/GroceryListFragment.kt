package com.example.grocery_shopping_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery_shopping_list.Adapters.GroceryListAdapter
import com.example.grocery_shopping_list.databinding.FragmentGroceryListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [GroceryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroceryListFragment : Fragment() {
    private val viewModel: GroceryViewModel by activityViewModels()
    private var toastMessage: Toast? = null
    private var _binding: FragmentGroceryListBinding? = null
    private val binding get() = _binding!!
    val args: GroceryListFragmentArgs by navArgs()
    val args2 = arguments?.getSerializable("item")
    private lateinit var adapter: GroceryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentGroceryListBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener{
            addListItem()
        }

        val recyclerView = binding.groclistview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val longClick = {item: String ->
            deleteListItem(item)
        }

        adapter = GroceryListAdapter(longClick)
        recyclerView.adapter = adapter
        adapter.submitList(viewModel.list.value?.elementAt(getItemIndex(args.item))?.detailItems)
    }

    private fun getItemIndex(item: Item): Int {
        return viewModel.list.value?.indexOf(item) ?: -1
    }

    private fun addListItem(){
        val input = binding.root.findViewById<TextView>(R.id.input)
        val text = input.text.toString()

        if(text == null || text.isEmpty()){
            makeToast("Enter an item.")
        }
        else{
            viewModel.addItemToRecipeList(viewModel.list.value?.elementAt(getItemIndex(args.item))!!, text)
            input.setText("")
            makeToast("added: $text")
            adapter.notifyDataSetChanged()
        }

    }

    private fun deleteListItem(item: String): Boolean{
        val index = args?.let { viewModel.list.value?.indexOf(it.item) }
        if(index != null){
            viewModel.list.value?.elementAt(index)?.let {new -> viewModel.deleteItemFromRecipeList(new, item)}
            adapter.notifyDataSetChanged()
        }
        makeToast("Removed: $item")
        return true
    }

    private fun makeToast(s : String){
        toastMessage?.cancel()
        toastMessage = Toast.makeText(activity, s, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }
}