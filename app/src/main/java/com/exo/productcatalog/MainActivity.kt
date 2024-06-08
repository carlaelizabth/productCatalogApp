package com.exo.productcatalog

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.exo.productcatalog.connection.Product
import com.exo.productcatalog.connection.ProductsApi
import com.exo.productcatalog.databinding.ActivityMainBinding
import com.exo.productcatalog.ui.ProductAdapter
import com.exo.productcatalog.util.Constants
import com.exo.productcatalog.util.Constants.PRODUCTS_URL
import com.exo.productcatalog.util.Constants.URL_BASE
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productsApi = retrofit.create(ProductsApi::class.java)

        val call: Call<List<Product>> = productsApi.getProducts(PRODUCTS_URL)

        call.enqueue(object: Callback<List<Product>>{
            override fun onResponse(
                p0: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                Log.d(Constants.LOGTAG, getString(R.string.mess_log,response.toString()))
                response.body()?.let{ products ->
                    val productAdapter = ProductAdapter(products) {product ->
                     //   product.id.let { id ->
                        //    val bundle = bundleOf(
                        //        "id" to id,
                        //        "price" to product.price
                        //    )
                        val productJson = Gson().toJson(product)
                        val bundle = Bundle().apply{
                            putString("id",product.id)
                            putString("product",productJson)
                        }

                        val intent = Intent(this@MainActivity,Details::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)

                    }

                    binding.rvListProducts.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = productAdapter
                    }

                }
            }

            override fun onFailure(p0: Call<List<Product>>, p1: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.tFConnection), Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(getString(R.string.tFConnection))
                    .setMessage(getString(R.string.tQFail))
                    .setPositiveButton(getString(R.string.tReintentar)) { dialog, _ ->
                        dialog.dismiss()
                        productsApi.getProducts(Constants.PRODUCTS_URL).enqueue(this)
                    }
                    .setNegativeButton(getString(R.string.tCancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        })


    }
}