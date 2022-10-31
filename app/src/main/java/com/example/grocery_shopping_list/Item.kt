package com.example.grocery_shopping_list

import java.io.Serializable

data class Item(val itemName: String, var detailItems: MutableList<String>) : Serializable