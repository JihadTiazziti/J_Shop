package com.example.j_shop

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalText: TextView
    private lateinit var clearCartButton: Button
    private lateinit var cartItems: MutableList<CartItem>
    private lateinit var adapter: CartAdapter
    private lateinit var backButton: ImageButton  // Déclaration du bouton de retour

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalText = findViewById(R.id.cartTotalText)
        clearCartButton = findViewById(R.id.btnClearCart)
        backButton = findViewById(R.id.btnBack)  // Initialisation du bouton de retour

        // Récupérer les articles du CartManager
        cartItems = CartManager.getItems().toMutableList()

        adapter = CartAdapter(cartItems) {
            updateTotal()
        }

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = adapter

        updateTotal()

        // Action vider le panier
        clearCartButton.setOnClickListener {
            CartManager.clearCart()
            cartItems.clear()
            adapter.notifyDataSetChanged()
            updateTotal()

            Toast.makeText(this, "Panier vidé avec succès", Toast.LENGTH_SHORT).show()
        }

        // Action du bouton retour
        backButton.setOnClickListener {
            onBackPressed()  // Retour à l'activité précédente
        }
    }

    private fun updateTotal() {
        val total = cartItems.sumOf { it.price * it.quantity }
        totalText.text = "Total : %.2f €".format(total)
    }
}
