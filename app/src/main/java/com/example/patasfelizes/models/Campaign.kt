package com.example.patasfelizes.models

import java.time.LocalDate

data class Campaign(
    val id: Int,
    val nome: String,
    val tipo: String,
    val descricao: String,
    val dataInicio: String,
    val dataTermino: String,
    val dataCadastro: LocalDate,
)

val CampaignList = mutableListOf(
    Campaign(
        id = 1,
        nome = "Bazar Beneficente",
        tipo = "Evento",
        descricao = "Venda de itens doados para arrecadação de fundos.",
        dataInicio = "2024-05-01",
        dataTermino = "2024-05-10",
        dataCadastro = LocalDate.of(2024, 4, 20)
    ),
    Campaign(
        id = 2,
        nome = "Arrecadação de Fundos",
        tipo = "Financeira",
        descricao = "Campanha para coletar doações para a organização.",
        dataInicio = "2024-06-15",
        dataTermino = "2024-07-15",
        dataCadastro = LocalDate.of(2024, 6, 1)
    ),
    Campaign(
        id = 3,
        nome = "Vacinação contra Raiva",
        tipo = "Saúde",
        descricao = "Campanha de vacinação para animais contra a raiva.",
        dataInicio = "2024-04-01",
        dataTermino = "2024-04-30",
        dataCadastro = LocalDate.of(2024, 3, 25)
    ),
    Campaign(
        id = 4,
        nome = "Doação de Ração",
        tipo = "Alimentação",
        descricao = "Coleta de ração para alimentar animais necessitados.",
        dataInicio = "2024-07-01",
        dataTermino = "2024-07-31",
        dataCadastro = LocalDate.of(2024, 6, 20)
    ),
    // Novas instâncias adicionadas
    Campaign(
        id = 5,
        nome = "Maratona de Adoção",
        tipo = "Adoção",
        descricao = "Evento para promover a adoção de animais resgatados.",
        dataInicio = "2024-08-10",
        dataTermino = "2024-08-20",
        dataCadastro = LocalDate.of(2024, 7, 15)
    ),
    Campaign(
        id = 6,
        nome = "Campanha de Esterilização",
        tipo = "Saúde",
        descricao = "Oferecer serviços de esterilização a baixo custo para controlar a população de animais.",
        dataInicio = "2024-09-05",
        dataTermino = "2024-09-25",
        dataCadastro = LocalDate.of(2024, 8, 25)
    )
)