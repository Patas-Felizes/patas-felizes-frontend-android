package com.example.patasfelizes.models

import java.time.LocalDate

data class Support(
    val apadrinhamento_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val animal_id: Int,
    val nome_apadrinhador: String,
    val valor: String,
    val regularidade: String,
    val data_cadastro: String = LocalDate.now().toString() // Mantém consistência com o formato do backend
)

// SupportResponse.kt - Para deserialização da resposta da API
data class SupportResponse(
    val status: Int,
    val data: Support? = null,
    val message: String? = null
)

data class SupportListResponse(
    val status: Int,
    val data: List<Support>? = null,
    val message: String? = null
)

// Extension function para converter Support do frontend para o formato do backend
fun Support.toBackendFormat(): Map<String, Any> = mapOf(
    "animal_id" to animal_id,
    "nome_apadrinhador" to nome_apadrinhador,
    "valor" to valor,
    "regularidade" to regularidade,
    "data_cadastro" to data_cadastro
)