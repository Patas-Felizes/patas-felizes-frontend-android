package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Adopter

@Composable
fun AdoptionEditScreen(
    navController: NavHostController,
    adopter: Adopter,
    onSave: (String, String, String, String, String, String, String, String, String) -> Unit
) {
    AdoptionFormScreen(
        initialPet = adopter.petNome,
        initialAdopterName = adopter.nome,
        initialContactInfo = adopter.telefone,
        initialState = adopter.estado,
        initialCity = adopter.cidade,
        initialAddress = adopter.endereco,
        initialNeighborhood = adopter.bairro,
        initialNumber = adopter.numero,
        initialCep = adopter.cep,
        onSave = { pet, name, contact, state, city, address, neighborhood, number, cep ->
            onSave(pet, name, contact, state, city, address, neighborhood, number, cep)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
