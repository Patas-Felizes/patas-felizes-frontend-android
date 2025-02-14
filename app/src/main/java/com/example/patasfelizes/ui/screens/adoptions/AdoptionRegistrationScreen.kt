package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Adopter

@Composable
fun AdoptionRegistrationScreen(
    navController: NavHostController,
    onSave: (Adopter) -> Unit
) {
    AdoptionFormScreen(
        navController = navController,
        initialAdopter = null,
        onSave = { adopter ->
            onSave(adopter)
            navController.navigateUp()
        },
        isEditMode = false
    )
}
