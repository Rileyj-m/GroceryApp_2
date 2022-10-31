package com.example.grocery_shopping_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grocery_shopping_list.Item

class GroceryViewModel : ViewModel() {
    private val _list = MutableLiveData<MutableList<Item>>()
    val list: LiveData<MutableList<Item>> = _list

    init {
        _list.value = arrayListOf()
    }

    fun addItemToGroceryList(item: Item){
        _list.value?.add(item)
        _list.value = _list.value
    }

    fun deleteItemFromGroceryList(item: Item){
        if (_list.value?.contains(item) == true) {
            _list.value?.remove(item)
            _list.value = _list.value
        }
    }

    fun addItemToRecipeList(item: Item, secondaryItem: String){
        val targetItem = list.value?.indexOf(item)
        if (targetItem != null) {
            _list.value?.elementAt(targetItem)?.detailItems?.add(secondaryItem)
        }

    }

    fun deleteItemFromRecipeList(item: Item, secondaryItem: String){
        val targetItem = list.value?.indexOf(item)
        if (targetItem != null) {
            _list.value?.elementAt(targetItem)?.detailItems?.remove(secondaryItem)
        }
    }

}