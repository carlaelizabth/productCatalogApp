package com.exo.productcatalog.connection

import com.google.gson.annotations.SerializedName

data class ProductFirst(
    var id: String,
    var name: String,
    @SerializedName("thumbnail_url")
    var thumbnail: String,
    var price: String,
    @SerializedName("provider")
    var supplierProduct: String,
    var delivery: String
)
