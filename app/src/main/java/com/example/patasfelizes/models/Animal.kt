package com.example.patasfelizes.models

import com.example.patasfelizes.R
import java.time.LocalDate

data class Animal(
    val id: Int,
    val nome: String,
    val descricao: String,
    val idade: String,
    val sexo: String,
    val castracao: String,
    val status: String,
    val especie: String,
    val dataCadastro: LocalDate,
    val imageRes: Int
)

val AnimalList = listOf(
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",
        sexo = "Fêmea",
        castracao = "Sim",
        status = "Para adoção",
        especie = "Gato",
        dataCadastro = LocalDate.of(2023, 5, 15),
        imageRes = R.drawable.img1
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",
        sexo = "Macho",
        castracao = "Não",
        status = "Em lar temporário",
        especie = "Cachorro",
        dataCadastro = LocalDate.of(2024, 1, 1),
        imageRes = R.drawable.img2
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",
        sexo = "Fêmea",
        castracao = "Sim",
        status = "Para adoção",
        especie = "Gato",
        dataCadastro = LocalDate.of(2023, 5, 15),
        imageRes = R.drawable.img1
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",
        sexo = "Macho",
        castracao = "Não",
        status = "Em lar temporário",
        especie = "Cachorro",
        dataCadastro = LocalDate.of(2024, 1, 1),
        imageRes = R.drawable.img2
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",
        sexo = "Fêmea",
        castracao = "Sim",
        status = "Para adoção",
        especie = "Gato",
        dataCadastro = LocalDate.of(2023, 5, 15),
        imageRes = R.drawable.img1
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",
        sexo = "Macho",
        castracao = "Não",
        status = "Em lar temporário",
        especie = "Cachorro",
        dataCadastro = LocalDate.of(2024, 1, 1),
        imageRes = R.drawable.img2
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",
        sexo = "Fêmea",
        castracao = "Sim",
        status = "Para adoção",
        especie = "Gato",
        dataCadastro = LocalDate.of(2023, 5, 15),
        imageRes = R.drawable.img1
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",
        sexo = "Macho",
        castracao = "Não",
        status = "Em lar temporário",
        especie = "Cachorro",
        dataCadastro = LocalDate.of(2024, 1, 1),
        imageRes = R.drawable.img2
    )
)
