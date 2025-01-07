package com.example.patasfelizes.ui.screens.finances

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Donation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationRegistrationScreen(
    navController: NavHostController,
    onSave: (Donation) -> Unit
) {
    DonationFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}