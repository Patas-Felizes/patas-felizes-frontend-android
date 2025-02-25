package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Adoption
import retrofit2.Call
import retrofit2.http.*

interface AdoptionsService {
    @GET("adocoes/")
    fun listAdoptions(): Call<List<Adoption>>

    @GET("adocoes/{id}")
    fun getAdoption(@Path("id") id: Int): Call<Adoption>

    @POST("adocoes")
    fun createAdoption(@Body adoption: Adoption): Call<Adoption>

    @PUT("adocoes/{id}")
    fun updateAdoption(@Path("id") id: Int, @Body adoption: Adoption): Call<Adoption>

    @DELETE("adocoes/{id}")
    fun deleteAdoption(@Path("id") id: Int): Call<Void>
}