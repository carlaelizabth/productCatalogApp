package com.exo.productcatalog.connection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ProductsApi {

  //  https://www.serverbpw.com/cm/store/products.php
    @GET
    fun getProducts(
        @Url url: String
    ): Call<List<ProductFirst>>

    // https://www.serverbpw.com/cm/store/product_detail.php?id=6541
    @GET("cm/store/product_detail.php?id=")
    fun getProductDetail(
        @Path("id")id: String
    ): Call<ProductDetail>

}