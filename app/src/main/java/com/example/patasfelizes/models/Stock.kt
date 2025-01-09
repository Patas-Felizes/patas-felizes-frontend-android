package com.example.patasfelizes.models

//Adicionar Vencimento
data class Stock(
    val id: Int,
    val categoria: String,
    val tipoItem: String,
    val animalEspecie: String,
    val quantidade: String
)

val StockList = mutableListOf(
    Stock(
        id = 1,
        categoria = "Alimentação",
        tipoItem = "Ração",
        animalEspecie = "Cachorro",
        quantidade = "10 quilos"
    ),
    Stock(
        id = 2,
        categoria = "Saúde",
        tipoItem = "Medicamento para pulgas",
        animalEspecie = "Gato",
        quantidade = "5 unidades"
    ),
    Stock(
        id = 3,
        categoria = "Alimentação",
        tipoItem = "Ração",
        animalEspecie = "Gato",
        quantidade = "5 quilos"
    ),
    Stock(
        id = 4,
        categoria = "Higiene",
        tipoItem = "Shampoo",
        animalEspecie = "Cachorro",
        quantidade = "1 unidade"
    ),
    Stock(
        id = 5,
        categoria = "Saúde",
        tipoItem = "Vacina Raiva",
        animalEspecie = "Cachorro",
        quantidade = "2 unidades"
    )
)