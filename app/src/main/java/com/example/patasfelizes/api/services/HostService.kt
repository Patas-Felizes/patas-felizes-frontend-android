package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Host
import retrofit2.Call
import retrofit2.http.*

interface HostService {
    @GET("hospedeiros")
    fun listHosts(): Call<List<Host>>

    @GET("hospedeiros/{id}")
    fun getHost(@Path("id") id: Int): Call<Host>

    @POST("hospedeiros")
    fun createHost(@Body host: Host): Call<Host>

    @PUT("hospedeiros/{id}")
    fun updateHost(@Path("id") id: Int, @Body host: Host): Call<Host>

    @DELETE("hospedeiros/{id}")
    fun deleteHost(@Path("id") id: Int): Call<Void>
}
