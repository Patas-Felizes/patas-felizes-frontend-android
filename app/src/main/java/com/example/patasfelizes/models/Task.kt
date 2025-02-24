package com.example.patasfelizes.models

import java.time.LocalDate

data class Task(
    val tarefa_id: Int = 0,  // Inicializa com 0, mas o backend vai sobrescrever com o ID real
    val tipo: String,
    val descricao: String,
    val data_tarefa: String,
    val voluntario_id: Int?,
    val animal_id: Int?,
    val data_cadastro: String = LocalDate.now().toString() // Mantém consistência com o formato do backend
)

// TaskResponse.kt - Para deserialização da resposta da API
data class TaskResponse(
    val status: Int,
    val data: Task? = null,
    val message: String? = null
)

data class TaskListResponse(
    val status: Int,
    val data: List<Task>? = null,
    val message: String? = null
)

// Extension function para converter Task do frontend para o formato do backend
fun Task.toBackendFormat(): Map<String, Any> = mapOf(
    "tipo" to tipo,
    "descricao" to descricao,
    "data_tarefa" to data_tarefa,
    "voluntario_id" to (voluntario_id ?: 0),
    "animal_id" to (animal_id ?: 0),
    "data_cadastro" to data_cadastro
)