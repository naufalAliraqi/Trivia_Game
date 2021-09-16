package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.triviagame.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val clint = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeRequest()
    }

    private fun makeRequest() {
        Log.i("TAG", "make request")

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.nationalize.io")
            .addQueryParameter("name", "Ali")
            .build()

        val request = Request.Builder().url(url).build()
        clint.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i("TAG", "fail : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
//                Log.i("TAG", response.body?.string().toString())
                runOnUiThread {
                    binding.text.text = response.body?.string().toString()
                }
            }

        })
    }
}