package com.example.grocery_shopping_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery_shopping_list.databinding.FragmentMainListBinding
import com.example.grocery_shopping_list.Adapters.MainListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        val recyclerView = binding.listview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val clicked = { item: Item ->
            val action =
                MainListFragmentDirections.actionMainListFragmentToGroceryListFragment(item, item.itemName)
            findNavController().navigate(action)
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
        val text = input.text.toString()

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

        val input = binding.root.findViewById<TextView>(R.id.input)
        val text = input.text.toString()
        val newItem = Item(text, mutableListOf())
        viewModel.addItemToGroceryList(newItem)
        input.setText("")
        makeToast("added: $text")

        return true
    }

    private fun makeToast(s : String){
        toastMessage?.cancel()
        toastMessage = Toast.makeText(activity, s, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }
}