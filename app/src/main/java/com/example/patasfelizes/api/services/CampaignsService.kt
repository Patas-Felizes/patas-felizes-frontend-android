package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Campaign
import retrofit2.Call
import retrofit2.http.*

interface CampaignsService {
    @GET("campanhas/")
    fun listCampaigns(): Call<List<Campaign>>

    @GET("campanhas/{campanha_id}")
    fun getCampaign(@Path("campanha_id") id: Int): Call<Campaign>

    @POST("campanhas")
    fun createCampaign(@Body campaign: Campaign): Call<Campaign>

    @PUT("campanhas/{campanha_id}")
    fun updateCampaign(@Path("campanha_id") id: Int, @Body campaign: Campaign): Call<Campaign>

    @DELETE("campanhas/{campanha_id}")
    fun deleteCampaign(@Path("campanha_id") id: Int): Call<Void>
}