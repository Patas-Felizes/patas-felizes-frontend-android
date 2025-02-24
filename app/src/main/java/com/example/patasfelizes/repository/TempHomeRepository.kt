package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.TempHome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TempHomeRepository {
    private val TAG = "TempHomeRepository"

    fun listTempHomes(onSuccess: (List<TempHome>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar lares temporários")
        val call = RetrofitInitializer().tempHomeService().listTempHomes()
        call.enqueue(object : Callback<List<TempHome>> {
            override fun onResponse(call: Call<List<TempHome>>, response: Response<List<TempHome>>) {
                if (response.isSuccessful) {
                    response.body()?.let { tempHomes ->
                        Log.d(TAG, "Lares temporários recebidos: ${tempHomes.size}")
                        onSuccess(tempHomes)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar lares temporários")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar lares temporários. Status: ${response.code()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TempHome>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar lares temporários", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getTempHome(id: Int, onSuccess: (TempHome) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar lar temporário ID: $id")
        val call = RetrofitInitializer().tempHomeService().getTempHome(id)
        call.enqueue(object : Callback<TempHome> {
            override fun onResponse(call: Call<TempHome>, response: Response<TempHome>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao buscar lar temporário ID: $id. Status: ${response.code()}")
                    onError("Erro ao buscar lar temporário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TempHome>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar lar temporário ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createTempHome(tempHome: TempHome, onSuccess: (TempHome) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar lar temporário: $tempHome")
        val call = RetrofitInitializer().tempHomeService().createTempHome(tempHome)
        call.enqueue(object : Callback<TempHome> {
            override fun onResponse(call: Call<TempHome>, response: Response<TempHome>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao criar lar temporário. Status: ${response.code()}")
                    onError("Erro ao criar lar temporário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TempHome>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar lar temporário", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateTempHome(id: Int, tempHome: TempHome, onSuccess: (TempHome) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar lar temporário ID: $id")
        Log.d(TAG, "Dados para atualização: $tempHome")
        val call = RetrofitInitializer().tempHomeService().updateTempHome(id, tempHome)
        call.enqueue(object : Callback<TempHome> {
            override fun onResponse(call: Call<TempHome>, response: Response<TempHome>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao atualizar lar temporário ID: $id. Status: ${response.code()}")
                    onError("Erro ao atualizar lar temporário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TempHome>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar lar temporário ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteTempHome(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar lar temporário ID: $id")
        val call = RetrofitInitializer().tempHomeService().deleteTempHome(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Lar temporário deletado com sucesso. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar lar temporário ID: $id. Status: ${response.code()}")
                    onError("Erro ao deletar lar temporário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar lar temporário ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}
