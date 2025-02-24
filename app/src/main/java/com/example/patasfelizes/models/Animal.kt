// Animal.kt
package com.example.patasfelizes.models

import java.time.LocalDate

data class Animal(
    val animal_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val nome: String,
    val idade: String,
    val foto: String = "",   // Alterado de imageRes/imageUris para corresponder ao backend
    val descricao: String,
    val sexo: String,
    val castracao: String,
    val status: String,
    val especie: String,
    val data_cadastro: String = LocalDate.now().toString() // Alterado para String para corresponder ao backend
)

// AnimalResponse.kt - Para deserialização da resposta da API
data class AnimalResponse(
    val status: Int,
    val data: Animal? = null,
    val message: String? = null
)

data class AnimalListResponse(
    val status: Int,
    val data: List<Animal>? = null,
    val message: String? = null
)

// Extension function para converter LocalDate para String no formato do backend
fun LocalDate.toBackendString(): String = this.toString()

// Extension function para converter Animal do frontend para o formato do backend
fun Animal.toBackendFormat(): Map<String, Any> = mapOf(
    "nome" to nome,
    "idade" to idade,
    "foto" to foto,
    "descricao" to descricao,
    "sexo" to sexo,
    "castracao" to castracao,
    "status" to status,
    "especie" to especie,
    "data_cadastro" to data_cadastro
)