package com.example.thronesapi.network

import com.example.thronesapi.models.Character
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://thronesapi.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ThronesapiService {
    @GET("api/v2/Characters")
    suspend fun getCharacter(): List<Character>
}

object Thronesapi {
    val retrofitService: ThronesapiService by lazy {
        retrofit.create(ThronesapiService::class.java)
    }
}
