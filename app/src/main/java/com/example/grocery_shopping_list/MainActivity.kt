package com.example.grocery_shopping_list

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery_shopping_list.Adapters.MainListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    /*lateinit var adapter: MainListAdapter
    var items = mutableListOf<String>()
    var toastMessage: Toast? = null

    lateinit var input : EditText
    lateinit var enter : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        enter = findViewById(R.id.add)

        items.add("apples")
        items.add("Oranges")
        items.add("Milk")
        items.add("Chocolate")

        adapter = MainListAdapter(items)
        listview.adapter = adapter
        listview.layoutManager = LinearLayoutManager(this)
        enter.setOnClickListener {
            onClickAddItem()
        }
    }

    private fun hidekeyboard(view: View){
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    private fun onClickAddItem(){
        val text = input.text.toString()

        if(text == null || text.isEmpty()){
            makeToast("Enter an item.")
        }
        else{
            addItem(text)
            input.setText("")
            makeToast("added: $text")
            hidekeyboard(input)
        }
    }

    private fun addItem(item : String){
        items.add(item)
        adapter.notifyDataSetChanged()
    }

    private fun makeToast(s : String){
        toastMessage?.cancel()
        toastMessage = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toastMessage?.show()
    }*/
}