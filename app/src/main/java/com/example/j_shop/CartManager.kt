package com.example.j_shop

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(product: Product) {
        // Chercher un article existant via l'ID du produit
        val existing = cartItems.find { it.id == product.id }
        if (existing != null) {
            existing.quantity++
        } else {
            cartItems.add(
                CartItem(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    imageResId = product.imageResId,
                    quantity = 1
                )
            )
        }
    }

    fun getItems(): List<CartItem> = cartItems

    fun clearCart() {
        cartItems.clear()
    }
}
