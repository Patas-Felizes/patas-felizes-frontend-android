package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Donation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationRepository {
    private val TAG = "DonationRepository"

    fun listDonations(onSuccess: (List<Donation>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar doações")
        val call = RetrofitInitializer().donationService().listDonations()
        call.enqueue(object : Callback<List<Donation>> {
            override fun onResponse(call: Call<List<Donation>>, response: Response<List<Donation>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar doações. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { donations ->
                        Log.d(TAG, "Quantidade de doações recebidas: ${donations.size}")
                        onSuccess(donations)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar doações")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar doações. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Donation>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar doações", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getDonation(id: Int, onSuccess: (Donation) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar doação ID: $id")
        val call = RetrofitInitializer().donationService().getDonation(id)
        call.enqueue(object : Callback<Donation> {
            override fun onResponse(call: Call<Donation>, response: Response<Donation>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar doação ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados da doação: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar doação ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Donation>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar doação ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createDonation(donation: Donation, onSuccess: (Donation) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar doação: $donation")
        val call = RetrofitInitializer().donationService().createDonation(donation)
        call.enqueue(object : Callback<Donation> {
            override fun onResponse(call: Call<Donation>, response: Response<Donation>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar doação. Status: ${response.code()}")
                    Log.d(TAG, "Doação criada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar doação. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar doação: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Donation>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar doação", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateDonation(id: Int, donation: Donation, onSuccess: (Donation) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar doação ID: $id")
        Log.d(TAG, "Dados para atualização: $donation")
        val call = RetrofitInitializer().donationService().updateDonation(id, donation)
        call.enqueue(object : Callback<Donation> {
            override fun onResponse(call: Call<Donation>, response: Response<Donation>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar doação ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Doação atualizada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar doação ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar doação: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Donation>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar doação ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteDonation(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar doação ID: $id")
        val call = RetrofitInitializer().donationService().deleteDonation(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar doação ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar doação ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar doação: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar doação ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}