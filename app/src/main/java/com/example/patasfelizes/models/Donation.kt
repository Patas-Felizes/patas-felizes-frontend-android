package com.example.patasfelizes.models

import java.time.LocalDate

data class Donation(
    val id: Int,
    val doador: String,
    val valor: String,
    val dataDoacao: String,
    val dataCadastro: LocalDate,
    val idAnimal: Animal? // Chave estrangeira opcional
)

val DonationList = mutableListOf(
    Donation(
        id = 1,
        doador = "João Silva",
        valor = "100.00",
        dataDoacao = "2023-12-01",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null
    ),
    Donation(
        id = 2,
        doador = "Maria Oliveira",
        valor = "50.00",
        dataDoacao = "2023-12-15",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null
    ),
    Donation(
        id = 3,
        doador = "Carlos Mendes",
        valor = "75.00",
        dataDoacao = "2024-01-05",
        dataCadastro = LocalDate.of(2024, 1, 6),
        idAnimal = null
    ),
    Donation(
        id = 4,
        doador = "Ana Paula",
        valor = "200.00",
        dataDoacao = "2024-01-10",
        dataCadastro = LocalDate.of(2024, 1, 11),
        idAnimal = null
    )
)
