package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Donation
import retrofit2.Call
import retrofit2.http.*

interface DonationService {
    @GET("doacoes/")
    fun listDonations(): Call<List<Donation>>

    @GET("doacoes/{doacao_id}")
    fun getDonation(@Path("doacao_id") id: Int): Call<Donation>

    @POST("doacoes")
    fun createDonation(@Body donation: Donation): Call<Donation>

    @PUT("doacoes/{doacao_id}")
    fun updateDonation(@Path("doacao_id") id: Int, @Body donation: Donation): Call<Donation>

    @DELETE("doacoes/{doacao_id}")
    fun deleteDonation(@Path("doacao_id") id: Int): Call<Void>
}