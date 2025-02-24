package com.example.patasfelizes.models

data class Stock(
    val estoque_id: Int = 0,  // Alterado de 'id' para 'estoque_id' para corresponder ao backend
    val categoria: String,
    val tipo_item: String,    // Alterado de 'tipoItem' para 'tipo_item' para corresponder ao backend
    val descricao: String,    // Adicionado para corresponder ao backend
    val especie_animal: String, // Alterado de 'animalEspecie' para 'especie_animal' para corresponder ao backend
    val quantidade: String,
    val quantidade_total: String // Adicionado para corresponder ao backend
)

// StockResponse.kt - Para deserialização da resposta da API, seguindo o padrão do AnimalResponse
data class StockResponse(
    val status: Int,
    val data: Stock? = null,
    val message: String? = null
)

data class StockListResponse(
    val status: Int,
    val data: List<Stock>? = null,
    val message: String? = null
)

// Extension function para converter Stock do frontend para o formato do backend
fun Stock.toBackendFormat(): Map<String, Any> = mapOf(
    "categoria" to categoria,
    "tipo_item" to tipo_item,
    "descricao" to descricao,
    "especie_animal" to especie_animal,
    "quantidade" to quantidade,
    "quantidade_total" to quantidade_total
)