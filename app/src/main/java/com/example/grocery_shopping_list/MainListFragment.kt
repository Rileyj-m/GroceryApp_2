package com.example.grocery_shopping_list

import android.graphics.Color
import android.icu.text.UnicodeSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery_shopping_list.databinding.FragmentMainListBinding
import com.example.grocery_shopping_list.Adapters.MainListAdapter
import kotlinx.android.synthetic.main.list_item.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainListFragment : Fragment() {
    private val viewModel: GroceryViewModel by activityViewModels()
    private var toastMessage: Toast? = null
    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private var isChecked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener{
            addListItem()
        }
        binding.isactivated.text = "Grocery List Mode"
        binding.createRecipe.setOnClickListener{
            if(isChecked){
                makeToast("You left recipe mode!")
                isChecked = false
                binding.isactivated.text = "Grocery List Mode"
            }else{
                isChecked = true
                makeToast("You are in recipe mode!")
                binding.isactivated.text = "Recipe Mode"
            }
        }
        val recyclerView = binding.listview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        var clicked = { item: Item ->
            if(isChecked){
                val action =
                    MainListFragmentDirections.actionMainListFragmentToGroceryListFragment(item, item.itemName)
                findNavController().navigate(action)
            }
        }

        val longClick = {item: Item ->
            deleteListItem(item)
        }

        val adapter = MainListAdapter(clicked, longClick)
        recyclerView.adapter = adapter
        viewModel.list.observe(this.viewLifecycleOwner){
            items -> items.let{
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun addListItem(){
        val input = binding.root.findViewById<TextView>(R.id.input)
        var text = input.text.toString()

        if(isChecked){
            text += " " + String(Character.toChars(0x1F4CB))
        }

        if(text == null || text.isEmpty()){
            makeToast("Enter an item.")
        }
        else{
            val newItem = Item(text, mutableListOf())
            viewModel.addItemToGroceryList(newItem)
            input.setText("")
            makeToast("added: $text")
        }

    }

    private fun deleteListItem(item: Item): Boolean{
        viewModel.deleteItemFromGroceryList(item)
        makeToast("Deleted: ${item.itemName}")

        return true
    }

    private fun makeToast(s : String){
        toastMessage?.cancel()
        toastMessage = Toast.makeText(activity, s, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }
}