package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Host
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostRepository {
    private val TAG = "HostRepository"

    fun listHosts(onSuccess: (List<Host>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar hospedeiros")
        val call = RetrofitInitializer().hostService().listHosts()
        call.enqueue(object : Callback<List<Host>> {
            override fun onResponse(call: Call<List<Host>>, response: Response<List<Host>>) {
                if (response.isSuccessful) {
                    response.body()?.let { hosts ->
                        Log.d(TAG, "Hospedeiros recebidos: ${hosts.size}")
                        onSuccess(hosts)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar hospedeiros")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar hospedeiros. Status: ${response.code()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Host>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar hospedeiros", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getHost(id: Int, onSuccess: (Host) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar hospedeiro ID: $id")
        val call = RetrofitInitializer().hostService().getHost(id)
        call.enqueue(object : Callback<Host> {
            override fun onResponse(call: Call<Host>, response: Response<Host>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao buscar hospedeiro ID: $id. Status: ${response.code()}")
                    onError("Erro ao buscar hospedeiro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Host>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar hospedeiro ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createHost(host: Host, onSuccess: (Host) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar hospedeiro: $host")
        val call = RetrofitInitializer().hostService().createHost(host)
        call.enqueue(object : Callback<Host> {
            override fun onResponse(call: Call<Host>, response: Response<Host>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao criar hospedeiro. Status: ${response.code()}")
                    onError("Erro ao criar hospedeiro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Host>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar hospedeiro", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateHost(id: Int, host: Host, onSuccess: (Host) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar hospedeiro ID: $id")
        Log.d(TAG, "Dados para atualização: $host")
        val call = RetrofitInitializer().hostService().updateHost(id, host)
        call.enqueue(object : Callback<Host> {
            override fun onResponse(call: Call<Host>, response: Response<Host>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao atualizar hospedeiro ID: $id. Status: ${response.code()}")
                    onError("Erro ao atualizar hospedeiro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Host>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar hospedeiro ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteHost(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar hospedeiro ID: $id")
        val call = RetrofitInitializer().hostService().deleteHost(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Hospedeiro deletado com sucesso. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar hospedeiro ID: $id. Status: ${response.code()}")
                    onError("Erro ao deletar hospedeiro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar hospedeiro ID: $id", t)
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}
