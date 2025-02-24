package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptionsRepository {
    private val TAG = "AdoptionsRepository"

    fun listAdoptions(onSuccess: (List<Adoption>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar adoções")
        val call = RetrofitInitializer().adoptionsService().listAdoptions()
        call.enqueue(object : Callback<List<Adoption>> {
            override fun onResponse(call: Call<List<Adoption>>, response: Response<List<Adoption>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar adoções. Status: ${response.code()}")
                    Log.d(TAG, "Resposta completa: ${response.body()}")
                    response.body()?.let { adoptions ->
                        Log.d(TAG, "Quantidade de adoções recebidas: ${adoptions.size}")
                        Log.d(TAG, "Lista de adoções: $adoptions")
                        onSuccess(adoptions)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar adoções")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar adoções. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Adoption>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar adoções", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getAdoption(id: Int, onSuccess: (Adoption) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar adoção ID: $id")
        val call = RetrofitInitializer().adoptionsService().getAdoption(id)
        call.enqueue(object : Callback<Adoption> {
            override fun onResponse(call: Call<Adoption>, response: Response<Adoption>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar adoção ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados da adoção: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Adoção encontrada: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados da adoção vieram nulos")
                        onError("Dados da adoção não encontrados")
                    }
                } else {
                    Log.e(TAG, "Erro ao buscar adoção ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adoption>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar adoção ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createAdoption(adoption: Adoption, onSuccess: (Adoption) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar adoção")
        Log.d(TAG, "Dados da adoção a ser criada: $adoption")
        val call = RetrofitInitializer().adoptionsService().createAdoption(adoption)
        call.enqueue(object : Callback<Adoption> {
            override fun onResponse(call: Call<Adoption>, response: Response<Adoption>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar adoção. Status: ${response.code()}")
                    Log.d(TAG, "Adoção criada: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Dados da adoção criada: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados da adoção criada vieram nulos")
                        onError("Erro ao criar adoção: dados nulos")
                    }
                } else {
                    Log.e(TAG, "Erro ao criar adoção. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar adoção: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adoption>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar adoção", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateAdoption(id: Int, adoption: Adoption, onSuccess: (Adoption) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar adoção ID: $id")
        Log.d(TAG, "Dados para atualização: $adoption")
        val call = RetrofitInitializer().adoptionsService().updateAdoption(id, adoption)
        call.enqueue(object : Callback<Adoption> {
            override fun onResponse(call: Call<Adoption>, response: Response<Adoption>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar adoção ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Adoção atualizada: ${response.body()}")
                    response.body()?.let {
                        Log.d(TAG, "Dados atualizados: $it")
                        onSuccess(it)
                    } ?: run {
                        Log.e(TAG, "Dados da adoção atualizada vieram nulos")
                        onError("Erro ao atualizar adoção: dados nulos")
                    }
                } else {
                    Log.e(TAG, "Erro ao atualizar adoção ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar adoção: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Adoption>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar adoção ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteAdoption(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar adoção ID: $id")
        val call = RetrofitInitializer().adoptionsService().deleteAdoption(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar adoção ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar adoção ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar adoção: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar adoção ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}