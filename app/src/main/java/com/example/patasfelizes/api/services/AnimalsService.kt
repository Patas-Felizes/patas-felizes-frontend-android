package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Animal
import retrofit2.Call
import retrofit2.http.*

interface AnimalsService {
    @GET("animals/")
    fun listAnimals(): Call<List<Animal>>

    @GET("/animals/{id}")
    fun getAnimal(@Path("id") id: Int): Call<Animal>

    @POST("/animals/")
    fun createAnimal(@Body animal: Animal): Call<Animal>

    @PUT("/animals/{id}")
    fun updateAnimal(@Path("id") id: Int, @Body animal: Animal): Call<Animal>

    @DELETE("/animals/{id}")
    fun deleteAnimal(@Path("id") id: Int): Call<Void>

}