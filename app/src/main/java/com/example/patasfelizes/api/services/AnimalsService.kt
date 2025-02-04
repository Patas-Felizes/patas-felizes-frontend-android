package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Animal
import retrofit2.http.GET
import retrofit2.Call

interface AnimalsService {
    @GET("animals")
    fun list(): Call<List<Animal>>
}