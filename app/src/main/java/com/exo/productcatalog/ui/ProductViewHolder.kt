package com.exo.productcatalog.ui

import androidx.recyclerview.widget.RecyclerView
import com.exo.productcatalog.connection.Product
import com.exo.productcatalog.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding):
    RecyclerView.ViewHolder(binding.root) {

    val ivProductImage = binding.ivProductImage
    fun bind(product: Product){
        binding.apply {
            tvProductName.text = product.productName
            tvProductPrice.text = product.price.toString()
            tvProductSupplier.text = product.pSupplier
            tvShippingCost.text = product.shippingCost.toString()
        }
    }

}