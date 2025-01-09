package com.example.patasfelizes.ui.screens.support

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Sponsor

@Composable
fun SupportRegistrationScreen(
    navController: NavHostController,
    onSave: (Sponsor) -> Unit
) {
    SupportFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}
