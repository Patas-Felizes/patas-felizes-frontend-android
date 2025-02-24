package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Support
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupportRepository {
    private val TAG = "SupportRepository"

    fun listSupports(onSuccess: (List<Support>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar apadrinhamentos")
        val call = RetrofitInitializer().supportService().listSupports()
        call.enqueue(object : Callback<List<Support>> {
            override fun onResponse(call: Call<List<Support>>, response: Response<List<Support>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar apadrinhamentos. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { supports ->
                        Log.d(TAG, "Quantidade de apadrinhamentos recebidos: ${supports.size}")
                        onSuccess(supports)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar apadrinhamentos")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar apadrinhamentos. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Support>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar apadrinhamentos", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getSupport(id: Int, onSuccess: (Support) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar apadrinhamento ID: $id")
        val call = RetrofitInitializer().supportService().getSupport(id)
        call.enqueue(object : Callback<Support> {
            override fun onResponse(call: Call<Support>, response: Response<Support>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar apadrinhamento ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados do apadrinhamento: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar apadrinhamento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Support>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar apadrinhamento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createSupport(support: Support, onSuccess: (Support) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar apadrinhamento: $support")
        val call = RetrofitInitializer().supportService().createSupport(support)
        call.enqueue(object : Callback<Support> {
            override fun onResponse(call: Call<Support>, response: Response<Support>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar apadrinhamento. Status: ${response.code()}")
                    Log.d(TAG, "Apadrinhamento criado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar apadrinhamento. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar apadrinhamento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Support>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar apadrinhamento", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateSupport(id: Int, support: Support, onSuccess: (Support) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar apadrinhamento ID: $id")
        Log.d(TAG, "Dados para atualização: $support")
        val call = RetrofitInitializer().supportService().updateSupport(id, support)
        call.enqueue(object : Callback<Support> {
            override fun onResponse(call: Call<Support>, response: Response<Support>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar apadrinhamento ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Apadrinhamento atualizado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar apadrinhamento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar apadrinhamento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Support>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar apadrinhamento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteSupport(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar apadrinhamento ID: $id")
        val call = RetrofitInitializer().supportService().deleteSupport(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar apadrinhamento ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar apadrinhamento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar apadrinhamento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar apadrinhamento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}