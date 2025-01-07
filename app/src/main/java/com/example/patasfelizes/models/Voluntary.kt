package com.example.patasfelizes.models

import com.example.patasfelizes.R

data class Voluntary(
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val imageRes: Int = R.drawable.default_image,
    val imageUris: List<String> = emptyList()
)

val VoluntaryList = mutableListOf(
    Voluntary(
        id = 1,
        nome = "João Silva",
        email = "joao.silva@email.com",
        telefone = "(11) 98765-4321",
        imageRes = R.drawable.img1
    ),
    Voluntary(
        id = 2,
        nome = "Maria Santos",
        email = "maria.santos@email.com",
        telefone = "(11) 91234-5678",
        imageRes = R.drawable.img2
    )
)