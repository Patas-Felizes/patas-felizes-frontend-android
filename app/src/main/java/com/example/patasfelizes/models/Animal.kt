package com.example.patasfelizes.models

import com.example.patasfelizes.R
import java.time.LocalDate

data class Animal(
    val id: Int,
    val nome: String,
    val descricao: String,
    val idade: String,  // Composto por valor e unidade
    val sexo: String,  // Com valores pré-definidos
    val castracao: String,  // Sim/Não
    val status: String,  // Opções restritas
    val especie: String,  // Gato ou Cachorro
    val dataCadastro: LocalDate,  // Data de cadastro
    val imageRes: Int  // Referência para a imagem (caso seja um recurso do aplicativo)
)


val AnimalList = listOf(
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",  // Exemplo de idade: 2 anos
        sexo = "Fêmea",  // Exemplo de sexo: Fêmea
        castracao = "Sim",  // Exemplo de castração: Sim
        status = "Para adoção",  // Exemplo de status: Para adoção
        especie = "Gato",  // Exemplo de espécie: Cachorro
        dataCadastro = LocalDate.of(2023, 5, 15),  // Exemplo de data de cadastro: 15 de maio de 2023
        imageRes = R.drawable.img1  // Referência para a imagem do cachorro
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",  // Exemplo de idade: 1 mês
        sexo = "Macho",  // Exemplo de sexo: Macho
        castracao = "Não",  // Exemplo de castração: Não
        status = "Em lar temporário",  // Exemplo de status: Em lar temporário
        especie = "Cachorro",  // Exemplo de espécie: Gato
        dataCadastro = LocalDate.of(2024, 1, 1),  // Exemplo de data de cadastro: 1 de janeiro de 2024
        imageRes = R.drawable.img2  // Referência para a imagem do gato
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",  // Exemplo de idade: 2 anos
        sexo = "Fêmea",  // Exemplo de sexo: Fêmea
        castracao = "Sim",  // Exemplo de castração: Sim
        status = "Para adoção",  // Exemplo de status: Para adoção
        especie = "Gato",  // Exemplo de espécie: Cachorro
        dataCadastro = LocalDate.of(2023, 5, 15),  // Exemplo de data de cadastro: 15 de maio de 2023
        imageRes = R.drawable.img1  // Referência para a imagem do cachorro
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",  // Exemplo de idade: 1 mês
        sexo = "Macho",  // Exemplo de sexo: Macho
        castracao = "Não",  // Exemplo de castração: Não
        status = "Em lar temporário",  // Exemplo de status: Em lar temporário
        especie = "Cachorro",  // Exemplo de espécie: Gato
        dataCadastro = LocalDate.of(2024, 1, 1),  // Exemplo de data de cadastro: 1 de janeiro de 2024
        imageRes = R.drawable.img2  // Referência para a imagem do gato
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",  // Exemplo de idade: 2 anos
        sexo = "Fêmea",  // Exemplo de sexo: Fêmea
        castracao = "Sim",  // Exemplo de castração: Sim
        status = "Para adoção",  // Exemplo de status: Para adoção
        especie = "Gato",  // Exemplo de espécie: Cachorro
        dataCadastro = LocalDate.of(2023, 5, 15),  // Exemplo de data de cadastro: 15 de maio de 2023
        imageRes = R.drawable.img1  // Referência para a imagem do cachorro
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",  // Exemplo de idade: 1 mês
        sexo = "Macho",  // Exemplo de sexo: Macho
        castracao = "Não",  // Exemplo de castração: Não
        status = "Em lar temporário",  // Exemplo de status: Em lar temporário
        especie = "Cachorro",  // Exemplo de espécie: Gato
        dataCadastro = LocalDate.of(2024, 1, 1),  // Exemplo de data de cadastro: 1 de janeiro de 2024
        imageRes = R.drawable.img2  // Referência para a imagem do gato
    ),
    Animal(
        id = 1,
        nome = "Nazaré",
        descricao = "Gatinha carinhosa, busca um lar temporário.",
        idade = "2 anos",  // Exemplo de idade: 2 anos
        sexo = "Fêmea",  // Exemplo de sexo: Fêmea
        castracao = "Sim",  // Exemplo de castração: Sim
        status = "Para adoção",  // Exemplo de status: Para adoção
        especie = "Gato",  // Exemplo de espécie: Cachorro
        dataCadastro = LocalDate.of(2023, 5, 15),  // Exemplo de data de cadastro: 15 de maio de 2023
        imageRes = R.drawable.img1  // Referência para a imagem do cachorro
    ),
    Animal(
        id = 2,
        nome = "Tom",
        descricao = "Cachorro muito ativo, adora brincar.",
        idade = "4 anos",  // Exemplo de idade: 1 mês
        sexo = "Macho",  // Exemplo de sexo: Macho
        castracao = "Não",  // Exemplo de castração: Não
        status = "Em lar temporário",  // Exemplo de status: Em lar temporário
        especie = "Cachorro",  // Exemplo de espécie: Gato
        dataCadastro = LocalDate.of(2024, 1, 1),  // Exemplo de data de cadastro: 1 de janeiro de 2024
        imageRes = R.drawable.img2  // Referência para a imagem do gato
    )
)
