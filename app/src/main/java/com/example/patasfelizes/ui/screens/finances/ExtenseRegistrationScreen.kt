package com.example.patasfelizes.ui.screens.finances

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Extense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtenseRegistrationScreen(
    navController: NavHostController,
    onSave: (Extense) -> Unit
) {
    ExtenseFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}