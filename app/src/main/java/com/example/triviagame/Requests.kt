package com.example.triviagame

import com.example.triviagame.Question
import com.google.gson.annotations.SerializedName

data class Requests(
    val results: List<Question>
)