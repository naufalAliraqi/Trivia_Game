package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.triviagame.data.NationalResponse
import com.example.triviagame.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private  val questionFragment = QuestionFragment()
    val clint = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, questionFragment).commit()

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
                Log.i("TAG", "Start Response")
                response.body?.string().let { jsonString ->
                    Log.i("TAG", jsonString.toString())
//                    val result = JSONObject(jsonString).toNationalResponse()
                    val result = Gson().fromJson(jsonString, NationalResponse::class.java)
                    Log.i("TAG", result.toString())
                    runOnUiThread {
//                        binding.text.text = result.countries[2].countryId
                    }
                }

//                Log.i("TAG", response.body?.string().toString())
            }

        })
    }
}