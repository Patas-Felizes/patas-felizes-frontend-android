package com.example.patasfelizes.models

import java.time.LocalDate

data class Extense(
    val despesa_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val valor: String,
    val data_despesa: String,
    val tipo: String,
    val animal_id: Int? = null,
    val procedimento_id: Int? = null,
    val comprovante: String = "",
    val data_cadastro: String = LocalDate.now().toString() // Mantém consistência com o formato do backend
)

// ExtenseResponse.kt - Para deserialização da resposta da API
data class ExtenseResponse(
    val status: Int,
    val data: Extense? = null,
    val message: String? = null
)

data class ExtenseListResponse(
    val status: Int,
    val data: List<Extense>? = null,
    val message: String? = null
)

// Extension function para converter Extense do frontend para o formato do backend
fun Extense.toBackendFormat(): Map<String, Any> = mapOf(
    "valor" to valor,
    "data_despesa" to data_despesa,
    "tipo" to tipo,
    "animal_id" to (animal_id ?: 0),
    "procedimento_id" to (procedimento_id ?: 0),
    "comprovante" to comprovante,
    "data_cadastro" to data_cadastro
)