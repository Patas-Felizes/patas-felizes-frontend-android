package com.example.patasfelizes.ui.screens.team

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Voluntary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamEditScreen(
    navController: NavHostController,
    voluntary: Voluntary,
    onSave: (Voluntary) -> Unit
) {
    TeamFormScreen(
        navController = navController,
        initialVoluntary = voluntary,
        onSave = onSave,
        isEditMode = true
    )
}