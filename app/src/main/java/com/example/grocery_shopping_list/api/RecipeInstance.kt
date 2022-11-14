package com.example.grocery_shopping_list.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeInstance {

    companion object{
        val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        fun getRecipeInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}