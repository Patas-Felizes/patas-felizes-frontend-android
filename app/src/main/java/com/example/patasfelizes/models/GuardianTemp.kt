package com.example.patasfelizes.models

import java.time.LocalDate

data class GuardianTemp(
    val id: Int,
    val nome: String,
    val petNome: String,
    val telefone: String,
    val periodo: String,
    val estado: String,
    val cidade: String,
    val endereco: String,
    val bairro: String,
    val numero: String,
    val cep: String,
    val dataCadastro: LocalDate
)

val GuardianTempList = mutableListOf(
    GuardianTemp(
        id = 1,
        nome ="Igor Andrade",
        petNome = "Tom",
        periodo = "2 meses",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Quixadá",
        endereco = "Rua Francisco Eneas de Lima",
        bairro = "Centro",
        numero = "1502",
        cep = "62685-000",
        dataCadastro = LocalDate.of(2024,6,23),
    ),
    GuardianTemp(
        id = 2,
        nome ="Rafael Carvalho",
        petNome = "Nazaré",
        periodo = "3 semanas",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Quixadá",
        endereco = "Rua Francisco Eneas de Lima",
        bairro = "Centro",
        numero = "1502",
        cep = "62685-000",
        dataCadastro = LocalDate.of(2024,6,23),
    ),
    GuardianTemp(
        id = 3,
        nome ="Eduarda Gomes",
        petNome = "Panda",
        periodo = "1 mês",
        telefone = "(85)99934-5665",
        estado = "CE",
        cidade = "Quixadá",
        endereco = "Rua Francisco Eneas de Lima",
        bairro = "Centro",
        numero = "1502",
        cep = "62685-000",
        dataCadastro = LocalDate.of(2024,6,23),
    )
)


