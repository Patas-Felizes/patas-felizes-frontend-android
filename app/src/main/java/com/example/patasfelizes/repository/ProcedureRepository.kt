package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Procedure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcedureRepository {
    private val TAG = "ProcedureRepository"

    fun listProcedures(onSuccess: (List<Procedure>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar procedimentos")
        val call = RetrofitInitializer().procedureService().listProcedures()
        call.enqueue(object : Callback<List<Procedure>> {
            override fun onResponse(call: Call<List<Procedure>>, response: Response<List<Procedure>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar procedimentos. Status: ${response.code()}")
                    response.body()?.let { procedures ->
                        Log.d(TAG, "Quantidade de procedimentos recebidos: ${procedures.size}")
                        onSuccess(procedures)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar procedimentos")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar procedimentos. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Procedure>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar procedimentos", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getProcedure(id: Int, onSuccess: (Procedure) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar procedimento ID: $id")
        val call = RetrofitInitializer().procedureService().getProcedure(id)
        call.enqueue(object : Callback<Procedure> {
            override fun onResponse(call: Call<Procedure>, response: Response<Procedure>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar procedimento ID: $id. Status: ${response.code()}")
                    response.body()?.let { onSuccess(it) } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao buscar procedimento ID: $id")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao buscar procedimento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Procedure>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar procedimento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createProcedure(procedure: Procedure, onSuccess: (Procedure) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar procedimento: $procedure")
        val call = RetrofitInitializer().procedureService().createProcedure(procedure)
        call.enqueue(object : Callback<Procedure> {
            override fun onResponse(call: Call<Procedure>, response: Response<Procedure>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar procedimento. Status: ${response.code()}")
                    response.body()?.let { onSuccess(it) } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao criar procedimento")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao criar procedimento. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar procedimento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Procedure>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar procedimento", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateProcedure(id: Int, procedure: Procedure, onSuccess: (Procedure) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar procedimento ID: $id")
        Log.d(TAG, "Dados para atualização: $procedure")
        val call = RetrofitInitializer().procedureService().updateProcedure(id, procedure)
        call.enqueue(object : Callback<Procedure> {
            override fun onResponse(call: Call<Procedure>, response: Response<Procedure>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar procedimento ID: $id. Status: ${response.code()}")
                    response.body()?.let { onSuccess(it) } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao atualizar procedimento ID: $id")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao atualizar procedimento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar procedimento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Procedure>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar procedimento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteProcedure(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar procedimento ID: $id")
        val call = RetrofitInitializer().procedureService().deleteProcedure(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar procedimento ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar procedimento ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar procedimento: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar procedimento ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}
