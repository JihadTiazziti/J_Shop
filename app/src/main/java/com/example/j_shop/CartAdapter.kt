package com.example.j_shop

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.cartItemImage)
        val name: TextView = itemView.findViewById(R.id.cartItemName)
        val price: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantity: TextView = itemView.findViewById(R.id.cartItemQuantity)
        val btnAdd: Button = itemView.findViewById(R.id.btnIncrease)
        val btnRemove: Button = itemView.findViewById(R.id.btnDecrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.imageResId)
        holder.name.text = item.name
        holder.quantity.text = item.quantity.toString()

        // Prix total = prix unitaire * quantité
        val totalPrice = item.price * item.quantity
        holder.price.text = "€%.2f".format(totalPrice)

        // Ajouter quantité
        holder.btnAdd.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onQuantityChanged()
        }

        // Réduire ou supprimer avec confirmation
        holder.btnRemove.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onQuantityChanged()
            } else {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Supprimer l'article")
                    .setMessage("Voulez-vous retirer cet article du panier ?")
                    .setPositiveButton("Oui") { _, _ ->
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        onQuantityChanged()
                    }
                    .setNegativeButton("Non", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
