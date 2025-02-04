package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AdoptionRegistrationScreen(
    navController: NavHostController,
    onSave: (String, String, String, String, String, String, String, String, String) -> Unit
) {
    AdoptionFormScreen(
        onSave = { pet, name, contact, state, city, address, neighborhood, number, cep ->
            onSave(pet, name, contact, state, city, address, neighborhood, number, cep)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
