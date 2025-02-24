package com.example.patasfelizes.models

import java.time.LocalDate

data class TempHome(
    val lar_temporario_id: Int = 0,  // Inicializa com 0; o backend irá atribuir o ID real
    val animal_id: Int,
    val hospedeiro_id: Int,
    val periodo: String,
    val data_hospedagem: String,
    val data_cadastro: String = LocalDate.now().toString()
)

// TempHomeResponse.kt - Para deserialização da resposta da API
data class TempHomeResponse(
    val status: Int,
    val data: TempHome? = null,
    val message: String? = null
)

data class TempHomeListResponse(
    val status: Int,
    val data: List<TempHome>? = null,
    val message: String? = null
)

// Função de extensão para converter TempHome do frontend para o formato esperado pelo backend
fun TempHome.toBackendFormat(): Map<String, Any> = mapOf(
    "animal_id" to animal_id,
    "hospedeiro_id" to hospedeiro_id,
    "periodo" to periodo,
    "data_hospedagem" to data_hospedagem,
    "data_cadastro" to data_cadastro
)
