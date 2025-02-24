package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptersRepository {
    private val TAG = "AdoptersRepository"

    fun listAdopters(onSuccess: (List<Adopter>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar adotantes")
        val call = RetrofitInitializer().adoptersService().listAdopters()
        call.enqueue(object : Callback<List<Adopter>> {
            override fun onResponse(call: Call<List<Adopter>>, response: Response<List<Adopter>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar adotantes. Status: ${response.code()}")
                    Log.d(TAG, "Resposta completa: ${response.body()}")
                    response.body()?.let { adopters ->
                        Log.d(TAG, "Quantidade de adotantes recebidos: ${adopters.size}")
                        Log.d(TAG, "Lista de adotantes: $adopters")
                        onSuccess(adopters)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar adotantes")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar adotantes. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Adopter>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar adotantes", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getAdopter(id: Int, onSuccess: (Adopter) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar adotante ID: $id")
        val call = RetrofitInitializer().adoptersService().getAdopter(id)
        call.enqueue(object : Callback<Adopter> {
            override fun onResponse(call: Call<Adopter>, response: Response<Adopter>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar adotante ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados do adotante: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Adotante encontrado: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados do adotante vieram nulos")
                        onError("Dados do adotante não encontrados")
                    }
                } else {
                    Log.e(TAG, "Erro ao buscar adotante ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adopter>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar adotante ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createAdopter(adopter: Adopter, onSuccess: (Adopter) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar adotante")
        Log.d(TAG, "Dados do adotante a ser criado: $adopter")
        val call = RetrofitInitializer().adoptersService().createAdopter(adopter)
        call.enqueue(object : Callback<Adopter> {
            override fun onResponse(call: Call<Adopter>, response: Response<Adopter>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar adotante. Status: ${response.code()}")
                    Log.d(TAG, "Adotante criado: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Dados do adotante criado: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados do adotante criado vieram nulos")
                        onError("Erro ao criar adotante: dados nulos")
                    }
                } else {
                    Log.e(TAG, "Erro ao criar adotante. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar adotante: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adopter>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar adotante", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateAdopter(id: Int, adopter: Adopter, onSuccess: (Adopter) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar adotante ID: $id")
        Log.d(TAG, "Dados para atualização: $adopter")
        val call = RetrofitInitializer().adoptersService().updateAdopter(id, adopter)
        call.enqueue(object : Callback<Adopter> {
            override fun onResponse(call: Call<Adopter>, response: Response<Adopter>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar adotante ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Adotante atualizado: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Dados atualizados: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados do adotante atualizado vieram nulos")
                        onError("Erro ao atualizar adotante: dados nulos")
                    }
                } else {
                    Log.e(TAG, "Erro ao atualizar adotante ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar adotante: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adopter>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar adotante ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteAdopter(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar adotante ID: $id")
        val call = RetrofitInitializer().adoptersService().deleteAdopter(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar adotante ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar adotante ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar adotante: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar adotante ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}