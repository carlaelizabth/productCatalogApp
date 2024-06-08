package com.exo.productcatalog

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.exo.productcatalog.connection.Product
import com.exo.productcatalog.connection.ProductsApi
import com.exo.productcatalog.databinding.ActivityDetailsBinding
import com.exo.productcatalog.util.Constants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate((layoutInflater))
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getString("id","")
        val productJson = bundle?.getString("product")
        val product = Gson().fromJson(productJson,Product::class.java)
   //     val productPrice = intent.extras?.getString("price")
        Log.d(Constants.LOGTAG, getString(R.string.tIdRecibido, id))

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productsApi = retrofit.create(ProductsApi::class.java)
        val call: Call<Product> = productsApi.getProductDetail(id!!)
        call.enqueue(object: Callback<Product> {
            override fun onResponse(
                p0: Call<Product>,
                response: Response<Product>
            ) {
                binding.apply {
                    tvProductName.text = response.body()?.productName
                    tvProductPrice.text = getString(R.string.tvPrice, product!!.price)
                    tvProductDescription.text = response.body()?.pDescription

                    Glide.with(this@Details)
                        .load(product.imageUrl)
                        .into(ivProductImage)
                }
            }

            override fun onFailure(p0: Call<Product>, p1: Throwable) {
                Toast.makeText(this@Details, getString(R.string.tFConnection), Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@Details)
                    .setTitle(getString(R.string.tFConnection))
                    .setMessage(getString(R.string.tQFail))
                    .setPositiveButton(getString(R.string.tReintentar)) { dialog, _ ->
                        dialog.dismiss()
                        productsApi.getProductDetail(id).enqueue(this)
                    }
                    .setNegativeButton(getString(R.string.tCancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        })

    }
}