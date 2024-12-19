package com.example.patasfelizes.models

import java.time.LocalDate

data class Adopter(
    val id: Int,
    val nome: String,
    val petNome: String,
    val telefone: String,
    val estado: String,
    val cidade: String,
    val endereco: String,
    val bairro: String,
    val numero: String,
    val cep: String,
    val dataCadastro: LocalDate
)

val AdopterList = listOf(
    Adopter(
        id = 1,
        nome ="Carlos Ferreira",
        petNome = "Tom",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Quixadá",
        endereco = "Rua Francisco Eneas de Lima",
        bairro = "Centro",
        numero = "1502",
        cep = "62685-000",
        dataCadastro = LocalDate.of(2024,6,23),
    ),
    Adopter(
        id = 2,
        nome ="Michael Rodrigues",
        petNome = "Nazaré",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Quixadá",
        endereco = "Rua Rosa Vieira Lopes",
        bairro = "Baviera",
        numero = "152A",
        cep = "63905-100",
        dataCadastro = LocalDate.of(2024,7,8),
    ),
    Adopter(
        id = 3,
        nome ="Pedro Sousa",
        petNome = "Panda",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Campos Sales",
        endereco = "Rua Celerino Nepomuceno de Carvalho",
        bairro = "Guarani",
        numero = "128",
        cep = "63150-000",
        dataCadastro = LocalDate.of(2024,11,9),
    )
)
