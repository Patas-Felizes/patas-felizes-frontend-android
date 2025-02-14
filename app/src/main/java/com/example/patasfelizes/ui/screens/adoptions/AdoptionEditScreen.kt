package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Adopter

@Composable
fun AdoptionEditScreen(
    navController: NavHostController,
    adopter: Adopter,
    onSave: (Adopter) -> Unit
) {
    AdoptionFormScreen(
        navController = navController,
        initialAdopter = adopter,
        onSave = { updatedAdopter ->
            onSave(updatedAdopter)
            navController.navigateUp()
        },
        isEditMode = true
    )
}
