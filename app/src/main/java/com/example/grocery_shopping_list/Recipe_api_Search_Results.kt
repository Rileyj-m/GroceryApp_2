package com.example.grocery_shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery_shopping_list.Adapters.IngredientsAdapter
import com.example.grocery_shopping_list.databinding.FragmentRecipeApiSearchResultsBinding
import com.google.gson.*
import com.squareup.picasso.Picasso
import okhttp3.*

/**
 * A simple [Fragment] subclass.
 * Use the [Recipe_api_Search_Results.newInstance] factory method to
 * create an instance of this fragment.
 */
class Recipe_api_Search_Results : Fragment() {
    private val viewModel: RecipeApiViewModel by activityViewModels()
    private var _binding: FragmentRecipeApiSearchResultsBinding? = null
    private val binding get() = _binding!!
    val args: Recipe_api_Search_ResultsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeApiSearchResultsBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.getRecipes(args.recipeName)

        // Wait for the image to come in and set it to the imageview.
        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            it?.let {
                recipeData ->
                Picasso.with(activity)
                    .load(recipeData.meals?.get(0)?.strMealThumb)
                    .into(binding.RecipeImage)
            }
        })

        // wait for the recipes ingredients to come in and populate
        viewModel.recipeIngredients.observe(viewLifecycleOwner, Observer {
            it?.let{
                val recyclerView = binding.ingredientsList
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                val adapter = IngredientsAdapter()
                adapter.setDataList(getArrayOfIng())
                binding.ingredientsList.adapter = adapter
            }
        })
        return root
    }

    private fun getArrayOfIng(): ArrayList<String>? {
        var list = viewModel.recipeIngredients.value?.meals?.get(0)?.let {
            arrayListOf(it.strMeasure1 + ", " + it.strIngredient1, it.strMeasure2 + ", " + it.strIngredient2, it.strMeasure3 + ", " + it.strIngredient3, it.strMeasure4 + ", " + it.strIngredient4, it.strMeasure5 + ", " + it.strIngredient5,
                it.strMeasure6 + ", " + it.strIngredient6, it.strMeasure7 + ", " + it.strIngredient7, it.strMeasure8 + ", " + it.strIngredient8, it.strMeasure9 + ", " + it.strIngredient9, it.strMeasure10 + ", " + it.strIngredient10,
                it.strMeasure11 + ", " + it.strIngredient11, it.strMeasure12 + ", " + it.strIngredient12, it.strMeasure13 + ", " + it.strIngredient13, it.strMeasure14 + ", " + it.strIngredient14, it.strMeasure15 + ", " + it.strIngredient15,
                it.strMeasure16 + ", " + it.strIngredient16, it.strMeasure17 + ", " + it.strIngredient17, it.strMeasure18 + ", " + it.strIngredient18, it.strMeasure19 + ", " + it.strIngredient19, it.strMeasure20 + ", " + it.strIngredient20)
        }
        val iterator = list?.iterator()

        if (list != null) {
            while(iterator?.hasNext() == true){
                val item = iterator.next()
                if (item == null || item.length <= 5 || item == "" || item.contains("Null") || item.contains("null")){
                    iterator.remove()
                }
            }
        }
        return list
    }
}