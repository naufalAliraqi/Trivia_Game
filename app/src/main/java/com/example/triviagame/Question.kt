package com.example.triviagame

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>,
    val question: String,
)
