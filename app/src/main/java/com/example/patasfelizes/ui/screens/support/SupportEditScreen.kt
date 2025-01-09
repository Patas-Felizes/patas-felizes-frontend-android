package com.example.patasfelizes.ui.screens.support

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Sponsor

@Composable
fun SupportEditScreen(
    navController: NavHostController,
    sponsor: Sponsor,
    onSave: (Sponsor) -> Unit
) {
    SupportFormScreen(
        navController = navController,
        initialSponsor = sponsor,
        onSave = onSave,
        isEditMode = true
    )
}
