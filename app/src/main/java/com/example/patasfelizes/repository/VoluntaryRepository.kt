package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.models.VoluntaryResponse
import com.example.patasfelizes.models.VoluntaryListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoluntaryRepository {
    private val TAG = "VoluntaryRepository"

    fun listVoluntarios(onSuccess: (List<Voluntary>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar voluntários")
        val call = RetrofitInitializer().voluntaryService().listVoluntarios()
        call.enqueue(object : Callback<List<Voluntary>> {
            override fun onResponse(call: Call<List<Voluntary>>, response: Response<List<Voluntary>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar voluntários. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { voluntarios ->
                        Log.d(TAG, "Quantidade de voluntários recebidos: ${voluntarios.size}")
                        onSuccess(voluntarios)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar voluntários")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar voluntários. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Voluntary>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar voluntários", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getVoluntario(id: Int, onSuccess: (Voluntary) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar voluntário ID: $id")
        val call = RetrofitInitializer().voluntaryService().getVoluntario(id)
        call.enqueue(object : Callback<Voluntary> {
            override fun onResponse(call: Call<Voluntary>, response: Response<Voluntary>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar voluntário ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados do voluntário: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar voluntário ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Voluntary>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar voluntário ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createVoluntario(voluntario: Voluntary, onSuccess: (Voluntary) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar voluntário: $voluntario")
        val call = RetrofitInitializer().voluntaryService().createVoluntario(voluntario)
        call.enqueue(object : Callback<Voluntary> {
            override fun onResponse(call: Call<Voluntary>, response: Response<Voluntary>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar voluntário. Status: ${response.code()}")
                    Log.d(TAG, "Voluntário criado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar voluntário. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar voluntário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Voluntary>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar voluntário", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateVoluntario(id: Int, voluntario: Voluntary, onSuccess: (Voluntary) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar voluntário ID: $id")
        Log.d(TAG, "Dados para atualização: $voluntario")
        val call = RetrofitInitializer().voluntaryService().updateVoluntario(id, voluntario)
        call.enqueue(object : Callback<Voluntary> {
            override fun onResponse(call: Call<Voluntary>, response: Response<Voluntary>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar voluntário ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Voluntário atualizado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar voluntário ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar voluntário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Voluntary>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar voluntário ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteVoluntario(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar voluntário ID: $id")
        val call = RetrofitInitializer().voluntaryService().deleteVoluntario(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar voluntário ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar voluntário ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar voluntário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar voluntário ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}