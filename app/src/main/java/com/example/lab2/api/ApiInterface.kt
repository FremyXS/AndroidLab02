package com.example.lab2.api

import com.example.lab2.model.CardRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("new_text.json")
    fun getMovieList(): Call<List<CardRequest>>
}

object RetrofitClient {

    private  val BASE_URL = "https://develtop.ru/study/"

    fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}