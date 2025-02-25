package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Extense
import retrofit2.Call
import retrofit2.http.*

interface ExtenseService {
    @GET("despesas/")
    fun listExtenses(): Call<List<Extense>>

    @GET("despesas/{despesa_id}")
    fun getExtense(@Path("despesa_id") id: Int): Call<Extense>

    @POST("despesas")
    fun createExtense(@Body extense: Extense): Call<Extense>

    @PUT("despesas/{despesa_id}")
    fun updateExtense(@Path("despesa_id") id: Int, @Body extense: Extense): Call<Extense>

    @DELETE("despesas/{despesa_id}")
    fun deleteExtense(@Path("despesa_id") id: Int): Call<Void>
}