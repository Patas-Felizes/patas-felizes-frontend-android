package com.example.patasfelizes.api

import com.example.patasfelizes.api.services.AdoptersService
import com.example.patasfelizes.api.services.AdoptionsService
import com.example.patasfelizes.api.services.AnimalsService
import com.example.patasfelizes.api.services.CampaignsService
import com.example.patasfelizes.api.services.DonationService
import com.example.patasfelizes.api.services.ExtenseService
import com.example.patasfelizes.api.services.HostService
import com.example.patasfelizes.api.services.ProcedureService
import com.example.patasfelizes.api.services.VoluntaryService
import com.example.patasfelizes.api.services.StockService
import com.example.patasfelizes.api.services.SupportService
import com.example.patasfelizes.api.services.TaskService
import com.example.patasfelizes.api.services.TempHomeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.6:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun animalsService() = retrofit.create(AnimalsService::class.java)
    fun adoptersService() = retrofit.create(AdoptersService::class.java)
    fun adoptionsService() = retrofit.create(AdoptionsService::class.java)
    fun voluntaryService() = retrofit.create(VoluntaryService::class.java)
    fun stockService() = retrofit.create(StockService::class.java)
    fun campaignsService() = retrofit.create(CampaignsService::class.java)
    fun supportService() = retrofit.create(SupportService::class.java)
    fun taskService() = retrofit.create(TaskService::class.java)
    fun hostService() = retrofit.create(HostService::class.java)
    fun tempHomeService() = retrofit.create(TempHomeService::class.java)
    fun donationService() = retrofit.create(DonationService::class.java)
    fun extenseService() = retrofit.create(ExtenseService::class.java)
    fun procedureService() = retrofit.create(ProcedureService::class.java)


}