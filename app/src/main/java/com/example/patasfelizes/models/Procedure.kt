package com.example.patasfelizes.models

import java.time.LocalDate

data class Procedure(
    val id: Int,
    val tipo: String,
    val descricao: String,
    val valor: String,
    val dataProcedimento: String,
    val dataCadastro: LocalDate,
    val idAnimal: Animal?,
    val idVoluntary: Voluntary?,
    val idExtense: Extense?
)

val ProcedureList = mutableListOf(
    Procedure(
        id = 1,
        tipo = "Castração",
        descricao = "Realizada após 4 meses de idade",
        valor = "100,00",
        dataProcedimento = "2023-12-01",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = AnimalList.find { it.id == 1 },
        idVoluntary = VoluntaryList.find { it.id == 1 },
        idExtense = null,
    ),
    Procedure(
        id = 2,
        tipo = "Cirurgia",
        descricao = "Amputação da perna",
        valor = "100,00",
        dataProcedimento = "2023-12-01",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = AnimalList.find { it.id == 1 },
        idVoluntary = VoluntaryList.find { it.id == 1 },
        idExtense = null,
    ),
    Procedure(
        id = 3,
        tipo = "Vacinação",
        descricao = "Vacina contra raiva",
        valor = "50,00",
        dataProcedimento = "2023-11-15",
        dataCadastro = LocalDate.of(2023, 11, 20),
        idAnimal = AnimalList.find { it.id == 2 },
        idVoluntary = VoluntaryList.find { it.id == 2 },
        idExtense = null,
    ),
    Procedure(
        id = 4,
        tipo = "Consulta",
        descricao = "Exame geral de rotina",
        valor = "80,00",
        dataProcedimento = "2023-10-10",
        dataCadastro = LocalDate.of(2023, 10, 15),
        idAnimal = AnimalList.find { it.id == 2 },
        idVoluntary = VoluntaryList.find { it.id == 1 },
        idExtense = null,
    ),
    Procedure(
        id = 5,
        tipo = "Exame de Sangue",
        descricao = "Coleta de amostra para análise",
        valor = "70,00",
        dataProcedimento = "2023-09-05",
        dataCadastro = LocalDate.of(2023, 9, 10),
        idAnimal = AnimalList.find { it.id == 1 },
        idVoluntary = VoluntaryList.find { it.id == 2 },
        idExtense = null,
    ),
    Procedure(
        id = 6,
        tipo = "Microchipagem",
        descricao = "Implantação de microchip para identificação",
        valor = "90,00",
        dataProcedimento = "2023-08-25",
        dataCadastro = LocalDate.of(2023, 8, 30),
        idAnimal = AnimalList.find { it.id == 2 },
        idVoluntary = VoluntaryList.find { it.id == 1 },
        idExtense = null,
    )
)