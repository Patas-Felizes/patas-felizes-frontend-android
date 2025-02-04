package com.example.patasfelizes.repository

import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Animal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimalsRepository {

    fun listAnimals(onSuccess: (List<Animal>) -> Unit, onError: (String) -> Unit) {
        val call = RetrofitInitializer().animalsService().list()
        call.enqueue(object : Callback<List<Animal>?> {
            override fun onResponse(call: Call<List<Animal>?>, response: Response<List<Animal>?>) {
                if (response.isSuccessful) {
                    response.body()?.let { animals ->
                        onSuccess(animals)
                    } ?: run {
                        onError("Resposta vazia da API")
                    }
                } else {
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Animal>?>, t: Throwable) {
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}