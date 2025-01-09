package com.example.patasfelizes.ui.screens.stock

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun StockRegistrationScreen(
    navController: NavHostController,
    onSave: (String, String, String, String) -> Unit
) {
    StockFormScreen(
        onSave = { category, type, animalSpecies, quantity ->
            onSave(category, type, animalSpecies, quantity)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
