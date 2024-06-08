package com.exo.productcatalog

import androidx.recyclerview.widget.RecyclerView
import com.exo.productcatalog.connection.ProductFirst
import com.exo.productcatalog.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding):
    RecyclerView.ViewHolder(binding.root) {

    val ivProductImage = binding.ivProductImage
    fun bind(product: ProductFirst){
        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = product.price
            tvProductSupplier.text = product.supplierProduct
            tvShippingCost.text = product.delivery
        }
    }

}