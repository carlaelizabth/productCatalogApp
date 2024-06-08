package com.exo.productcatalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exo.productcatalog.connection.ProductFirst
import com.exo.productcatalog.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: List<ProductFirst>, private val onProductClicked: (ProductFirst) -> Unit): RecyclerView.Adapter<ProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)

        Picasso.get()
            .load(product.thumbnail)
            .into(holder.ivProductImage)

        holder.itemView.setOnClickListener{
            onProductClicked(product)
        }
    }

}