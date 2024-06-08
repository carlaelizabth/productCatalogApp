package com.exo.productcatalog.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exo.productcatalog.connection.Product
import com.exo.productcatalog.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: List<Product>, private val onProductClicked: (Product) -> Unit): RecyclerView.Adapter<ProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)

        Picasso.get()
            .load(product.imageUrl)
            .into(holder.ivProductImage)

        holder.itemView.setOnClickListener{
            onProductClicked(product)
        }
    }

}