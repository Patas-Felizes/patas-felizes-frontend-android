package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Campaign
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampaignsRepository {
    private val TAG = "CampaignsRepository"

    fun listCampaigns(onSuccess: (List<Campaign>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar campanhas")
        val call = RetrofitInitializer().campaignsService().listCampaigns()
        call.enqueue(object : Callback<List<Campaign>> {
            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar campanhas. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { campaigns ->
                        Log.d(TAG, "Quantidade de campanhas recebidas: ${campaigns.size}")
                        onSuccess(campaigns)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar campanhas")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar campanhas. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar campanhas", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getCampaign(id: Int, onSuccess: (Campaign) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar campanha ID: $id")
        val call = RetrofitInitializer().campaignsService().getCampaign(id)
        call.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar campanha ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados da campanha: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar campanha ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar campanha ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createCampaign(campaign: Campaign, onSuccess: (Campaign) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar campanha: $campaign")
        val call = RetrofitInitializer().campaignsService().createCampaign(campaign)
        call.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar campanha. Status: ${response.code()}")
                    Log.d(TAG, "Campanha criada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar campanha. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar campanha: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar campanha", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateCampaign(id: Int, campaign: Campaign, onSuccess: (Campaign) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar campanha ID: $id")
        Log.d(TAG, "Dados para atualização: $campaign")
        val call = RetrofitInitializer().campaignsService().updateCampaign(id, campaign)
        call.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar campanha ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Campanha atualizada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar campanha ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar campanha: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar campanha ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteCampaign(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar campanha ID: $id")
        val call = RetrofitInitializer().campaignsService().deleteCampaign(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar campanha ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar campanha ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar campanha: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar campanha ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}