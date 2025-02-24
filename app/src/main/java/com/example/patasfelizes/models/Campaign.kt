package com.example.patasfelizes.models

import java.time.LocalDate

data class Campaign(
    val campanha_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val nome: String,
    val tipo: String,
    val descricao: String,
    val data_inicio: String,
    val data_termino: String,
    val local: String,
    val data_cadastro: String = LocalDate.now().toString() // Mantém consistência com o formato do backend
)

// CampaignResponse.kt - Para deserialização da resposta da API
data class CampaignResponse(
    val status: Int,
    val data: Campaign? = null,
    val message: String? = null
)

data class CampaignListResponse(
    val status: Int,
    val data: List<Campaign>? = null,
    val message: String? = null
)

// Extension function para converter Campaign do frontend para o formato do backend
fun Campaign.toBackendFormat(): Map<String, Any> = mapOf(
    "nome" to nome,
    "tipo" to tipo,
    "descricao" to descricao,
    "data_inicio" to data_inicio,
    "data_termino" to data_termino,
    "local" to local
)