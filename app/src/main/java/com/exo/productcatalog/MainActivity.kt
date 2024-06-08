package com.exo.productcatalog

import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

                    binding.

                }
            }

            override fun onFailure(p0: Call<List<ProductFirst>>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })


    }
}