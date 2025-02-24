package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Support
import retrofit2.Call
import retrofit2.http.*

interface SupportService {
    @GET("apadrinhamentos")
    fun listSupports(): Call<List<Support>>

    @GET("apadrinhamentos/{apadrinhamento_id}")
    fun getSupport(@Path("apadrinhamento_id") id: Int): Call<Support>

    @POST("apadrinhamentos")
    fun createSupport(@Body support: Support): Call<Support>

    @PUT("apadrinhamentos/{apadrinhamento_id}")
    fun updateSupport(@Path("apadrinhamento_id") id: Int, @Body support: Support): Call<Support>

    @DELETE("apadrinhamentos/{apadrinhamento_id}")
    fun deleteSupport(@Path("apadrinhamento_id") id: Int): Call<Void>
}