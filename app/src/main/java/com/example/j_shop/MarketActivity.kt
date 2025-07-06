package com.example.j_shop

import ProductDatabaseHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MarketActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var btnCart: ImageButton
    private lateinit var btnFilter: ImageButton
    private lateinit var searchAutoComplete: AutoCompleteTextView
    private lateinit var dbHelper: ProductDatabaseHelper
    private lateinit var allProducts: List<Product>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerMarket)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        btnFilter = findViewById(R.id.btnFilter)
        searchAutoComplete = findViewById(R.id.searchView) // AutoCompleteTextView

        // Récupération des données
        dbHelper = ProductDatabaseHelper(this)
        allProducts = dbHelper.getAllProducts()

        // Initialisation de l'adapter
        adapter = ProductAdapter(allProducts) { product ->
            CartManager.addItem(product)
            Toast.makeText(this, "${product.name} ajouté au panier", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Suggestions pour l'auto-complétion
        val productNames = allProducts.map { it.name }
        val searchAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, productNames)
        searchAutoComplete.setAdapter(searchAdapter)

        // 🔍 Recherche rapide et insensible à la casse
        searchAutoComplete.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                val filteredList = if (query.isNotEmpty()) {
                    allProducts.filter { it.name.contains(query, ignoreCase = true) }
                } else {
                    allProducts
                }
                adapter.updateProducts(filteredList)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Cart

        // Menu filtre
        btnFilter.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                val sortedProducts = when (item.itemId) {
                    R.id.filter_price_asc -> allProducts.sortedBy { it.price }
                    R.id.filter_price_desc -> allProducts.sortedByDescending { it.price }
                    R.id.filter_name_asc -> allProducts.sortedBy { it.name }
                    R.id.filter_name_desc -> allProducts.sortedByDescending { it.name }
                    else -> allProducts
                }
                adapter.updateProducts(sortedProducts)
                true
            }

            popupMenu.show()
        }

        // Bottom Navigation Setup
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Accueil - reste dans MarketActivity
                    true
                }
                R.id.nav_cart -> {
                    // Panier
                    val intentCart = Intent(this, CartActivity::class.java)
                    startActivity(intentCart)
                    true
                }
                R.id.nav_profile -> {
                    // Profil
                    val intentProfile = Intent(this, LoginActivity::class.java)
                    startActivity(intentProfile)
                    true
                }
                else -> false
            }
        }
    }
}
