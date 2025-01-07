package com.example.patasfelizes.models

import java.time.LocalDate

data class Extense(
    val id: Int,
    val valor: String,
    val tipo: String,
    val dataDespesa: String,
    val dataCadastro: LocalDate,
    val idAnimal: Animal? // Chave estrangeira opcional
)

val ExtenseList = mutableListOf(
    Extense(
        id = 1,
        valor = "300.00",
        tipo = "Veterinário",
        dataDespesa = "2023-06-01",
        dataCadastro = LocalDate.of(2023, 6, 2),
        idAnimal = AnimalList.find { it.id == 1 }
    ),
    Extense(
        id = 2,
        valor = "150.00",
        tipo = "Medicamentos",
        dataDespesa = "2024-01-05",
        dataCadastro = LocalDate.of(2024, 1, 6),
        idAnimal = AnimalList.find { it.id == 2 }
    ),
    Extense(
        id = 3,
        valor = "500.00",
        tipo = "Cirurgia",
        dataDespesa = "2024-01-15",
        dataCadastro = LocalDate.of(2024, 1, 16),
        idAnimal = AnimalList.find { it.id == 1 }
    ),
    Extense(
        id = 4,
        valor = "200.00",
        tipo = "Ração",
        dataDespesa = "2023-12-20",
        dataCadastro = LocalDate.of(2023, 12, 21),
        idAnimal = null
    )
)
