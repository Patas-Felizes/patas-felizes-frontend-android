package com.example.patasfelizes.models

import java.time.LocalDate

data class Procedure(
    val procedimento_id: Int = 0, // Inicializa com 0; o backend deverá atribuir o ID real
    val tipo: String,
    val descricao: String,
    val valor: String,
    val data_procedimento: String,
    val animal_id: Int?,
    val voluntario_id: Int?,
)

// ProcedureResponse.kt - Para deserialização da resposta da API
data class ProcedureResponse(
    val status: Int,
    val data: Procedure? = null,
    val message: String? = null
)

// ProcedureListResponse.kt - Para deserialização da resposta da API quando se retorna uma lista de Procedures
data class ProcedureListResponse(
    val status: Int,
    val data: List<Procedure>? = null,
    val message: String? = null
)

// Extension function para converter Procedure do frontend para o formato do backend
fun Procedure.toBackendFormat(): Map<String, Any?> = mapOf(
    "tipo" to tipo,
    "descricao" to descricao,
    "valor" to valor,
    "data_procedimento" to data_procedimento,
    "animal_id" to animal_id,
    "voluntario_id" to voluntario_id,
)
