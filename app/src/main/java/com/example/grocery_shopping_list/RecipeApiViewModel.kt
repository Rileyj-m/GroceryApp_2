package com.example.grocery_shopping_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocery_shopping_list.api.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class ApiInterface {LOADING, ERROR, DONE}

class RecipeApiViewModel() : ViewModel() {

    private val _status = MutableLiveData<ApiInterface>()

    val status: LiveData<ApiInterface> = _status

    private val _recipes = MutableLiveData<RecipeData>()

    val recipes: LiveData<RecipeData> = _recipes

    private val _recipeIngredients = MutableLiveData<RecipeIng>()

    val recipeIngredients: LiveData<RecipeIng> = _recipeIngredients


    init {
        _recipes.value = RecipeData()
        _recipeIngredients.value = RecipeIng()
    }

    fun getRecipes(recipeSearchString: String) {

        viewModelScope.launch {
            _status.value = ApiInterface.LOADING
            try{
                val recipeInstance = RecipeInstance.getRecipeInstance().create(RecipeApiInterface::class.java)
                val recipe = recipeInstance.getMealsV2(recipeSearchString)

                recipe.enqueue(object : Callback<JsonObject?> {
                    override fun onResponse(
                        call: Call<JsonObject?>,
                        response: Response<JsonObject?>
                    ) {
                        var body = response.body()
                        val gson : Gson = Gson()

                        // output for total array, measure array, and ing array
                        val jsonOutput1 = gson.fromJson(body, RecipeData::class.java)
                        val jsonOutput2 = gson.fromJson(body, RecipeIng::class.java)

                        _recipes.value = jsonOutput1
                        _recipeIngredients.value = jsonOutput2

                        _status.value = ApiInterface.DONE
                    }

                    override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                        Log.e("Failure", t.toString())
                    }
                })
            }
            catch (e: Exception){
                Log.e("ViewmodelError", e.toString())
                _status.value = ApiInterface.ERROR
                _recipes.value = RecipeData(null)
            }
        }
    }
}