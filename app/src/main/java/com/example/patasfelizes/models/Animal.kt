package com.example.patasfelizes.models

import com.example.patasfelizes.R
import java.time.LocalDate
import androidx.compose.runtime.mutableStateListOf

data class Animal(
    val id: Int,
    val nome: String,
    val descricao: String,
    val idade: String,
    val sexo: String,
    val castracao: String,
    val status: String,
    val especie: String,
    val dataCadastro: LocalDate = LocalDate.now(),
    val imageRes: Int = R.drawable.default_image,
    val imageUris: List<String> = emptyList() // Modificado para suportar múltiplas imagens
)

val AnimalList = mutableStateListOf<Animal>()