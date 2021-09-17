package com.example.triviagame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.triviagame.databinding.FragmentQuestionBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class QuestionFragment: Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    lateinit var ollQuestion: Requests
    private val clint = OkHttpClient()

    private var correctAnswer = ""
    private var answer = mutableListOf<String?>()
    private var questionIndex = 0
    private var score = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        makeNewRequest()
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        clickAnswer()
        return binding.root
    }

    private fun clickAnswer() {
        binding.textFirst.setOnClickListener {
            checkAnswer(binding.textFirst.text.toString())
        }
        binding.textSecond.setOnClickListener {
            checkAnswer(binding.textSecond.text.toString())

        }
        binding.textThird.setOnClickListener {
            checkAnswer(binding.textThird.text.toString())

        }
        binding.textFourth.setOnClickListener {
            checkAnswer(binding.textFourth.text.toString())
        }
    }

    private fun makeNewRequest() {
        Log.i("TAG", "make request")
//        val url = HttpUrl.Builder()
//            .scheme("https")
//            .host("opentdb.com")
//            .addQueryParameter("amount", "10")
//            .build()

/*  */

        val request = Request.Builder()
            .url("https://opentdb.com/api.php?amount=10&type=multiple")
            .build()
        clint.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("TAG", "fail : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("TAG", "Start Response")
                response.body?.string().let { jsonString ->
                    Log.i("TAG", jsonString.toString())
//                    val result = JSONObject(jsonString).toNationalResponse()
                    ollQuestion = Gson().fromJson(jsonString, Requests::class.java)
                    getNextQuestion()
                }
            }

        })
    }

    private fun checkAnswer(answerText: String) {
        if (answerText == correctAnswer) {
            score++
            Toast.makeText(context, "Correct 👍", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Ops!! 🤕", Toast.LENGTH_SHORT).show()
        }
        questionIndex++
        getNextQuestion()
    }

    private fun getNextQuestion() {
        if (questionIndex < 10)
        {
            val question = ollQuestion.results.toMutableList()[questionIndex]
            answer = mutableListOf(
                question.correctAnswer,
                question.incorrectAnswers[0],
                question.incorrectAnswers[1],
                question.incorrectAnswers[2],
            ).shuffled().toMutableList()
            correctAnswer = question.correctAnswer

//                    Log.i("TAG", result.toString())
            activity?.runOnUiThread {
                binding.textQuestion.text = question.question
                binding.textFirst.text = answer[0]
                binding.textSecond.text = answer[1]
                binding.textThird.text = answer[2]
                binding.textFourth.text = answer[3]
                binding.textScore.text = (questionIndex + 1).toString()
            }
        }
        else
        {
            showResultFragment()
        }

    }

    private fun showResultFragment() {
        val resultFragment = ResultFragment()
        val bundle = Bundle()
        bundle.putInt(Constatnt.SCORE_KEY,score)
        resultFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,resultFragment)
            .commit()
    }
}