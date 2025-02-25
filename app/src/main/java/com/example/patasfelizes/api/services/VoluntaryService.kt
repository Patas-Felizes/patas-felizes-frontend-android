package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Voluntary
import retrofit2.Call
import retrofit2.http.*

interface VoluntaryService {
    @GET("voluntarios/")
    fun listVoluntarios(): Call<List<Voluntary>>

    @GET("voluntarios/{id}")
    fun getVoluntario(@Path("id") id: Int): Call<Voluntary>

    @POST("voluntarios")
    fun createVoluntario(@Body voluntario: Voluntary): Call<Voluntary>

    @PUT("voluntarios/{id}")
    fun updateVoluntario(@Path("id") id: Int, @Body voluntario: Voluntary): Call<Voluntary>

    @DELETE("voluntarios/{id}")
    fun deleteVoluntario(@Path("id") id: Int): Call<Void>
}