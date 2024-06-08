package com.exo.productcatalog

import android.app.AlertDialog
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.exo.productcatalog.connection.ProductFirst
import com.exo.productcatalog.connection.ProductsApi
import com.exo.productcatalog.databinding.ActivityMainBinding
import com.exo.productcatalog.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productsApi = retrofit.create(ProductsApi::class.java)

        val call: Call<List<ProductFirst>> = productsApi.getProducts(Constants.PRODUCTS_URL)

        call.enqueue(object: Callback<List<ProductFirst>>{
            override fun onResponse(
                p0: Call<List<ProductFirst>>,
                response: Response<List<ProductFirst>>
            ) {
                Log.d(Constants.LOGTAG, getString(R.string.mess_log,response.toString()))
                response.body()?.let{ products ->
                    val productAdapter = ProductAdapter(products) {product ->
                        product.id.let { id ->
                            val bundle = bundleOf(
                                "id" to id
                            )

                         //   val intent = Intent(this@MainActivity,Details::class.java)
                        }
                    }

                    binding.rvListProducts.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = productAdapter
                    }

                }
            }

            override fun onFailure(p0: Call<List<ProductFirst>>, p1: Throwable) {
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