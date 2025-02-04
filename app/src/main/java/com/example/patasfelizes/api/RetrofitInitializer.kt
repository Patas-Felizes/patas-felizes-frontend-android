package com.example.patasfelizes.api

import com.example.patasfelizes.api.services.AnimalsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.8:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun animalsService() = retrofit.create(AnimalsService::class.java)
}