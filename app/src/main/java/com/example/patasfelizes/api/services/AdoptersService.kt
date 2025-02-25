package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Adopter
import retrofit2.Call
import retrofit2.http.*

interface AdoptersService {
    @GET("adotantes/")
    fun listAdopters(): Call<List<Adopter>>

    @GET("adotantes/{id}")
    fun getAdopter(@Path("id") id: Int): Call<Adopter>

    @POST("adotantes")
    fun createAdopter(@Body adopter: Adopter): Call<Adopter>

    @PUT("adotantes/{id}")
    fun updateAdopter(@Path("id") id: Int, @Body adopter: Adopter): Call<Adopter>

    @DELETE("adotantes/{id}")
    fun deleteAdopter(@Path("id") id: Int): Call<Void>
}