package com.example.patasfelizes.models

data class Host(
    val hospedeiro_id: Int = 0,  // Inicializa com 0; o backend deve definir o ID real
    val nome: String,
    val telefone: String,
    val email: String,
    val moradia: String
)

// HostResponse.kt - Para deserialização da resposta da API
data class HostResponse(
    val status: Int,
    val data: Host? = null,
    val message: String? = null
)

data class HostListResponse(
    val status: Int,
    val data: List<Host>? = null,
    val message: String? = null
)

// Extension function para converter Host do frontend para o formato do backend
fun Host.toBackendFormat(): Map<String, Any> = mapOf(
    "nome" to nome,
    "telefone" to telefone,
    "email" to email,
    "moradia" to moradia
)
