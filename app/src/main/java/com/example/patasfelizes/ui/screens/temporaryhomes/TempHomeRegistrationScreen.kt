package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.GuardianTemp

@Composable
fun TempHomeRegistrationScreen(
    navController: NavHostController,
    onSave: (GuardianTemp) -> Unit
) {
    TempHomeFormScreen(
        navController = navController,
        initialGuardian = null,
        onSave = { guardian ->
            onSave(guardian)
            navController.navigateUp()
        },
        isEditMode = false
    )
}
