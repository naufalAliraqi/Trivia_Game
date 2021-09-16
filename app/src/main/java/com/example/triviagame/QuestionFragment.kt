package com.example.triviagame

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.triviagame.data.NationalResponse
import com.example.triviagame.data.Requests
import com.example.triviagame.databinding.ActivityMainBinding
import com.example.triviagame.databinding.FragmentQuestionBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class QuestionFragment: Fragment() {
    lateinit var binding: FragmentQuestionBinding
    private val clint = OkHttpClient()
    var correctAnswer = ""
    var answer = mutableListOf<String?>()
    var questionIndex = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root

        makeRequest()
    }

    private fun makeRequest() {
        Log.i("TAG", "make request")
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("opentdb.com/api.php")
            .addQueryParameter("amount", "10")
            .build()

        val request = Request.Builder().url(url).build()
        clint.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("TAG", "fail : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("TAG", "Start Response")
                response.body?.string().let { jsonString ->
                    Log.i("TAG", jsonString.toString())
//                    val result = JSONObject(jsonString).toNationalResponse()
                    val ollQuestion = Gson().fromJson(jsonString, Requests::class.java)

                    val question = ollQuestion.results.toMutableList()[questionIndex]
                    answer = mutableListOf(
                        question.correctAnswer,
                        question.incorrectAnswers[0],
                        question.incorrectAnswers[1],
                        question.incorrectAnswers[2],
                    ).shuffled().toMutableList()
                    correctAnswer = question.correctAnswer.toString()

//                    Log.i("TAG", result.toString())
                    activity?.runOnUiThread {
//                        binding.text.text = result.countries[2].countryId
                    }
                }

//                Log.i("TAG", response.body?.string().toString())
            }

        })
    }
}