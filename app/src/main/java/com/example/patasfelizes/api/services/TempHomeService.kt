package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.TempHome
import retrofit2.Call
import retrofit2.http.*

interface TempHomeService {
    @GET("temporary_shelters/")
    fun listTempHomes(): Call<List<TempHome>>

    @GET("temporary_shelters/{id}")
    fun getTempHome(@Path("id") id: Int): Call<TempHome>

    @POST("temporary_shelters")
    fun createTempHome(@Body tempHome: TempHome): Call<TempHome>

    @PUT("temporary_shelters/{id}")
    fun updateTempHome(@Path("id") id: Int, @Body tempHome: TempHome): Call<TempHome>

    @DELETE("temporary_shelters/{id}")
    fun deleteTempHome(@Path("id") id: Int): Call<Void>
}
