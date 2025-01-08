package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TempHomeRegistrationScreen(
    navController: NavHostController,
    onSave: (String, String, String, String, String, String, String, String, String, String) -> Unit
) {
    TempHomeFormScreen(
        onSave = { petName, guardianName, contactInfo, period, state, city, address, neighborhood, number, cep ->
            onSave(petName, guardianName, contactInfo, period, state, city, address, neighborhood, number, cep)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
