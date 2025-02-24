package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Animal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimalsRepository {
    private val TAG = "AnimalsRepository"

    fun listAnimals(onSuccess: (List<Animal>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar animais")
        val call = RetrofitInitializer().animalsService().listAnimals()
        call.enqueue(object : Callback<List<Animal>> {
            override fun onResponse(call: Call<List<Animal>>, response: Response<List<Animal>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar animais. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { animals ->
                        Log.d(TAG, "Quantidade de animais recebidos: ${animals.size}")
                        onSuccess(animals)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar animais")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar animais. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Animal>?>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar animais", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getAnimal(id: Int, onSuccess: (Animal) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar animal ID: $id")
        val call = RetrofitInitializer().animalsService().getAnimal(id)
        call.enqueue(object : Callback<Animal> {
            override fun onResponse(call: Call<Animal>, response: Response<Animal>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar animal ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados do animal: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar animal ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Animal>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar animal ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createAnimal(animal: Animal, onSuccess: (Animal) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar animal: $animal")
        val call = RetrofitInitializer().animalsService().createAnimal(animal)
        call.enqueue(object : Callback<Animal> {
            override fun onResponse(call: Call<Animal>, response: Response<Animal>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar animal. Status: ${response.code()}")
                    Log.d(TAG, "Animal criado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar animal. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar animal: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Animal>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar animal", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateAnimal(id: Int, animal: Animal, onSuccess: (Animal) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar animal ID: $id")
        Log.d(TAG, "Dados para atualização: $animal")
        val call = RetrofitInitializer().animalsService().updateAnimal(id, animal)
        call.enqueue(object : Callback<Animal> {
            override fun onResponse(call: Call<Animal>, response: Response<Animal>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar animal ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Animal atualizado: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar animal ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar animal: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Animal>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar animal ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteAnimal(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar animal ID: $id")
        val call = RetrofitInitializer().animalsService().deleteAnimal(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar animal ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar animal ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar animal: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar animal ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}