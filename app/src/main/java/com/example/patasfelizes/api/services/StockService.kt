package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Stock
import retrofit2.Call
import retrofit2.http.*

interface StockService {
    @GET("estoque/")
    fun listStock(): Call<List<Stock>>

    @GET("estoque/{estoque_id}")
    fun getStock(@Path("estoque_id") id: Int): Call<Stock>

    @POST("estoque")
    fun createStock(@Body stock: Stock): Call<Stock>

    @PUT("estoque/{estoque_id}")
    fun updateStock(@Path("estoque_id") id: Int, @Body stock: Stock): Call<Stock>

    @DELETE("estoque/{estoque_id}")
    fun deleteStock(@Path("estoque_id") id: Int): Call<Void>
}