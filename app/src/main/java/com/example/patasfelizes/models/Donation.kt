package com.example.patasfelizes.models

import java.time.LocalDate

data class Donation(
    val doacao_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val doador: String,
    val valor: String,
    val data_doacao: String,
    val animal_id: Int? = null,
    val companha_id: Int? = null,
    val estoque_id: Int? = null,
    val comprovante: String = "",
    val data_cadastro: String = LocalDate.now().toString() // Mantém consistência com o formato do backend
)

// DonationResponse.kt - Para deserialização da resposta da API
data class DonationResponse(
    val status: Int,
    val data: Donation? = null,
    val message: String? = null
)

data class DonationListResponse(
    val status: Int,
    val data: List<Donation>? = null,
    val message: String? = null
)

// Extension function para converter Donation do frontend para o formato do backend
fun Donation.toBackendFormat(): Map<String, Any> = mapOf(
    "doador" to doador,
    "valor" to valor,
    "data_doacao" to data_doacao,
    "animal_id" to (animal_id ?: 0),
    "companha_id" to (companha_id ?: 0),
    "estoque_id" to (estoque_id ?: 0),
    "comprovante" to comprovante,
    "data_cadastro" to data_cadastro
)