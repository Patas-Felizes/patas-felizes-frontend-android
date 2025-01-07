package com.example.patasfelizes.ui.screens.team

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Voluntary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRegistrationScreen(
    navController: NavHostController,
    onSave: (Voluntary) -> Unit
) {
    TeamFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}
