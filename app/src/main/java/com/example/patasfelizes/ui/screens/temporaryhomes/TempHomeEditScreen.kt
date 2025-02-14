package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.GuardianTemp

@Composable
fun TempHomeEditScreen(
    navController: NavHostController,
    guardian: GuardianTemp,
    onSave: (GuardianTemp) -> Unit
) {
    TempHomeFormScreen(
        navController = navController,
        initialGuardian = guardian,
        onSave = { updatedGuardian ->
            onSave(updatedGuardian)
            navController.navigateUp()
        },
        isEditMode = true
    )
}
