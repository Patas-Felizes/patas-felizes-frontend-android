package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Extense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExtenseRepository {
    private val TAG = "ExtenseRepository"

    fun listExtenses(onSuccess: (List<Extense>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar despesas")
        val call = RetrofitInitializer().extenseService().listExtenses()
        call.enqueue(object : Callback<List<Extense>> {
            override fun onResponse(call: Call<List<Extense>>, response: Response<List<Extense>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar despesas. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { extenses ->
                        Log.d(TAG, "Quantidade de despesas recebidas: ${extenses.size}")
                        onSuccess(extenses)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar despesas")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar despesas. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Extense>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar despesas", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getExtense(id: Int, onSuccess: (Extense) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar despesa ID: $id")
        val call = RetrofitInitializer().extenseService().getExtense(id)
        call.enqueue(object : Callback<Extense> {
            override fun onResponse(call: Call<Extense>, response: Response<Extense>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar despesa ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados da despesa: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar despesa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Extense>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar despesa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createExtense(extense: Extense, onSuccess: (Extense) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar despesa: $extense")
        val call = RetrofitInitializer().extenseService().createExtense(extense)
        call.enqueue(object : Callback<Extense> {
            override fun onResponse(call: Call<Extense>, response: Response<Extense>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar despesa. Status: ${response.code()}")
                    Log.d(TAG, "Despesa criada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar despesa. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar despesa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Extense>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar despesa", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateExtense(id: Int, extense: Extense, onSuccess: (Extense) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar despesa ID: $id")
        Log.d(TAG, "Dados para atualização: $extense")
        val call = RetrofitInitializer().extenseService().updateExtense(id, extense)
        call.enqueue(object : Callback<Extense> {
            override fun onResponse(call: Call<Extense>, response: Response<Extense>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar despesa ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Despesa atualizada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar despesa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar despesa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Extense>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar despesa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteExtense(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar despesa ID: $id")
        val call = RetrofitInitializer().extenseService().deleteExtense(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar despesa ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar despesa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar despesa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar despesa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}