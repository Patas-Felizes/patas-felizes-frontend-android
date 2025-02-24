package com.example.patasfelizes.models

data class Adoption(
    val adocao_id: Int = 0,
    val animal_id: Int,
    val adotante_id: Int,
    val companha_id: Int,
    val data_devolucao: String,
    val motivo_devolucao: String,
    val data_adocao: String,
    val data_cadastro: String
)

data class AdoptionResponse(
    val status: Int,
    val data: Adoption? = null,
    val message: String? = null
)

data class AdoptionListResponse(
    val status: Int,
    val data: List<Adoption>? = null,
    val message: String? = null
)

fun Adoption.toBackendFormat(): Map<String, Any> = mapOf(
    "animal_id" to animal_id,
    "adotante_id" to adotante_id,
    "companha_id" to companha_id,
    "data_devolucao" to data_devolucao,
    "motivo_devolucao" to motivo_devolucao,
    "data_adocao" to data_adocao,
    "data_cadastro" to data_cadastro
)