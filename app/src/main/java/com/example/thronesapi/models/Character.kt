package com.example.thronesapi.models

import com.squareup.moshi.Json

data class Character(
    val id: Int,
    @Json(name="fullName") val name: String,
    val title: String,
    val family: String,
    val imageUrl: String
)
