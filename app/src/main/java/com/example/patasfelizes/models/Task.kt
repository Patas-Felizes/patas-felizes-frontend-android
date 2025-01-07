package com.example.patasfelizes.models

import java.time.LocalDate

data class Task(
    val id: Int,
    val tipo: String,
    val descricao: String,
    val dataTarefa: String,
    val dataCadastro: LocalDate,
    val idAnimal: Animal?,
    val idVoluntary: Voluntary?
)

val TaskList = mutableListOf(
    Task(
        id = 1,
        tipo = "Medicação",
        descricao = "Aplicar vermífugo",
        dataTarefa = "2023-12-01",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = AnimalList.find { it.id == 1 },
        idVoluntary = VoluntaryList.find { it.id == 1 }
    ),
    Task(
        id = 2,
        tipo = "Alimentação",
        descricao = "Preparar e distribuir ração da manhã",
        dataTarefa = "2023-12-02",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null,
        idVoluntary = VoluntaryList.find { it.id == 2 }
    ),
    Task(
        id = 3,
        tipo = "Limpeza",
        descricao = "Higienizar canil número 5",
        dataTarefa = "2023-12-03",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null,
        idVoluntary = VoluntaryList.find { it.id == 2 }
    ),
    Task(
        id = 4,
        tipo = "Veterinário",
        descricao = "Levar para consulta de rotina",
        dataTarefa = "2023-12-04",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = AnimalList.find { it.id == 2 },
        idVoluntary = VoluntaryList.find { it.id == 1 }
    ),
    Task(
        id = 5,
        tipo = "Socialização",
        descricao = "Passeio com os cães",
        dataTarefa = "2023-12-05",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null,
        idVoluntary = VoluntaryList.find { it.id == 1 }
    ),
    Task(
        id = 6,
        tipo = "Manutenção",
        descricao = "Consertar portão do canil 3",
        dataTarefa = "2023-12-06",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null,
        idVoluntary = VoluntaryList.find { it.id == 1 }
    ),
    Task(
        id = 7,
        tipo = "Adoção",
        descricao = "Organizar feira de adoção",
        dataTarefa = "2023-12-07",
        dataCadastro = LocalDate.of(2023, 12, 16),
        idAnimal = null,
        idVoluntary = VoluntaryList.find { it.id == 1 }
    )
)