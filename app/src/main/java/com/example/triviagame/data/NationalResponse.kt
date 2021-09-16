package com.example.triviagame.data

import com.google.gson.annotations.SerializedName

data class NationalResponse(
    val name: String,
    @SerializedName("country") val countries: List<Requests>
)