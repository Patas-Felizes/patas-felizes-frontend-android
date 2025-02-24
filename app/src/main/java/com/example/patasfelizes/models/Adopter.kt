package com.example.patasfelizes.models

data class Adopter(
    val adotante_id: Int = 0,
    val nome: String,
    val telefone: String,
    val email: String,
    val moradia: String
)

data class AdopterResponse(
    val status: Int,
    val data: Adopter? = null,
    val message: String? = null
)

data class AdopterListResponse(
    val status: Int,
    val data: List<Adopter>? = null,
    val message: String? = null
)

fun Adopter.toBackendFormat(): Map<String, Any> = mapOf(
    "nome" to nome,
    "telefone" to telefone,
    "email" to email,
    "moradia" to moradia
)