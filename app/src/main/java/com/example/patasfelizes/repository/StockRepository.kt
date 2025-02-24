package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.models.StockResponse
import com.example.patasfelizes.models.StockListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockRepository {
    private val TAG = "StockRepository"

    fun listStock(onSuccess: (List<Stock>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar itens do estoque")
        val call = RetrofitInitializer().stockService().listStock()
        call.enqueue(object : Callback<List<Stock>> {
            override fun onResponse(call: Call<List<Stock>>, response: Response<List<Stock>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar itens do estoque. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { stock ->
                        Log.d(TAG, "Quantidade de itens recebidos: ${stock.size}")
                        onSuccess(stock)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar estoque")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar estoque. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Stock>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar estoque", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getStock(id: Int, onSuccess: (Stock) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar item de estoque ID: $id")
        val call = RetrofitInitializer().stockService().getStock(id)
        call.enqueue(object : Callback<Stock> {
            override fun onResponse(call: Call<Stock>, response: Response<Stock>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar item ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados do item: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar item ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Stock>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar item ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createStock(stock: Stock, onSuccess: (Stock) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar item de estoque: $stock")
        val call = RetrofitInitializer().stockService().createStock(stock)
        call.enqueue(object : Callback<Stock> {
            override fun onResponse(call: Call<Stock>, response: Response<Stock>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar item. Status: ${response.code()}")
                    Log.d(TAG, "Item criado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar item. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar item: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Stock>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar item", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateStock(id: Int, stock: Stock, onSuccess: (Stock) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar item ID: $id")
        Log.d(TAG, "Dados para atualização: $stock")
        val call = RetrofitInitializer().stockService().updateStock(id, stock)
        call.enqueue(object : Callback<Stock> {
            override fun onResponse(call: Call<Stock>, response: Response<Stock>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar item ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Item atualizado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar item ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar item: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Stock>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar item ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteStock(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar item ID: $id")
        val call = RetrofitInitializer().stockService().deleteStock(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar item ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar item ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar item: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar item ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}