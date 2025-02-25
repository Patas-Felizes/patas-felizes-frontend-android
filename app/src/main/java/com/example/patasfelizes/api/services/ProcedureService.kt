package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Procedure
import retrofit2.Call
import retrofit2.http.*

interface ProcedureService {
    @GET("procedimentos/")
    fun listProcedures(): Call<List<Procedure>>

    @GET("procedimentos/{procedimento_id}")
    fun getProcedure(@Path("procedimento_id") id: Int): Call<Procedure>

    @POST("procedimentos")
    fun createProcedure(@Body procedure: Procedure): Call<Procedure>

    @PUT("procedimentos/{procedimento_id}")
    fun updateProcedure(@Path("procedimento_id") id: Int, @Body procedure: Procedure): Call<Procedure>

    @DELETE("procedimentos/{procedimento_id}")
    fun deleteProcedure(@Path("procedimento_id") id: Int): Call<Void>
}
