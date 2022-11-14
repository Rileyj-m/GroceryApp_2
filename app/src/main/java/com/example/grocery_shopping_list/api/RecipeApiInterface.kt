package com.example.grocery_shopping_list.api

import com.google.gson.JsonObject
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiInterface {
    @GET("search.php?")
    fun getMealsV2(@Query("s") recipe: String) : Call<JsonObject>
}