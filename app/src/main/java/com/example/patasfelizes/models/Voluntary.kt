package com.example.patasfelizes.models

data class Voluntary(
    val voluntario_id: Int = 0,  // Alterado de 'id' para 'voluntario_id'
    val nome: String,
    val foto: String = "",       // Alterado de imageRes/imageUris para foto
    val email: String,
    val telefone: String
)

// VoluntaryResponse.kt - Para deserialização da resposta da API
data class VoluntaryResponse(
    val status: Int,
    val data: Voluntary? = null,
    val message: String? = null
)

data class VoluntaryListResponse(
    val status: Int,
    val data: List<Voluntary>? = null,
    val message: String? = null
)

// Extension function para converter Voluntary do frontend para o formato do backend
fun Voluntary.toBackendFormat(): Map<String, Any> = mapOf(
    "nome" to nome,
    "foto" to foto,
    "email" to email,
    "telefone" to telefone
)