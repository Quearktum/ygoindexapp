package com.example.ygoindexapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import com.example.ygoindexapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { CardsAdapter() { OnCardSelect(it) } }
    private lateinit var viewModel: MainViewModel
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //drawer layout initiate
        drawerLayout = binding.lDrawer
        val navView: NavigationView = binding.nvNavView

        //toggle open, close for drawer
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initiate database
        val cardDb = CardDatabase.getInstance(this)
        val myViewModelFactory = ViewModelFactory(cardDb!!)

        viewModel = ViewModelProvider(this, myViewModelFactory).get(MainViewModel::class.java)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //the viewModel observes the change in the adapter when it trigger the setData function
        viewModel.cards.observe(this) {
            adapter.setData(it)
        }

        //button functionality for drawer navigation
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_Add -> switchActitvity(AddActivity())
            }
            true

        }
    }

    //DetailedActivity is triggered when one of the card is clicked
    private fun OnCardSelect(card: Card) {
        val intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra("card", card)

        startActivity(intent)
    }

    //Search menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null)
            searchDb(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null)
            searchDb(query)
        return true
    }

    //search function for search menu
    private fun searchDb(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchCardByName(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchActitvity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}