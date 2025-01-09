package com.example.patasfelizes.models

import java.time.LocalDate

data class Sponsor(
    val id: Int,
    val idAnimal: Animal?,
    val padrinhoNome: String,
    val valor: String,
    val regularidade: String,
    val dataCadastro: LocalDate
)

val SponsorList = mutableListOf(
    Sponsor(
        id = 1,
        idAnimal = AnimalList.find { it.id == 1 },
        padrinhoNome = "José Bezerra",
        valor = "100,00",
        regularidade = "Mensalmente",
        dataCadastro = LocalDate.of(2023, 10, 15),
    ),
    Sponsor(
        id = 2,
        idAnimal = AnimalList.find { it.id == 1 },
        padrinhoNome = "Carlos Alberto",
        valor = "20,00",
        regularidade = "Semanalmente",
        dataCadastro = LocalDate.of(2023, 12, 16),
    ),
    Sponsor(
        id = 3,
        idAnimal = AnimalList.find { it.id == 2 },
        padrinhoNome = "Maria Gomes",
        valor = "10,00",
        regularidade = "Semanalmente",
        dataCadastro = LocalDate.of(2023, 12, 16),
    ),
    Sponsor(
        id = 4,
        idAnimal = AnimalList.find { it.id == 1 },
        padrinhoNome = "Paula Maciel",
        valor = "250,00",
        regularidade = "Mensalmente",
        dataCadastro = LocalDate.of(2023, 11, 20),
    )
)