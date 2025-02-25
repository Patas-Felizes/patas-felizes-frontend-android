package com.example.patasfelizes.models

data class Adoption(
    val adocao_id: Int = 0,
    val animal_id: Int,
    val adotante_id: Int,
    val data_adocao: String,
    val data_cadastro: String
)

fun Adoption.toBackendFormat(): Map<String, Any> = mapOf(
    "animal_id" to animal_id,
    "adotante_id" to adotante_id,
    "data_adocao" to data_adocao,
    "data_cadastro" to data_cadastro
)