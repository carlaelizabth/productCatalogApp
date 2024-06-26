package com.exo.productcatalog.connection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductsApi {

  //  https://www.serverbpw.com/cm/store/products.php
    @GET
    fun getProducts(
        @Url url: String
    ): Call<List<Product>>

    // https://www.serverbpw.com/cm/store/product_detail.php?id=6541
    @GET("cm/store/product_detail.php?id=")
    fun getProductDetail(
        @Query("id")id: String
    ): Call<Product>

}