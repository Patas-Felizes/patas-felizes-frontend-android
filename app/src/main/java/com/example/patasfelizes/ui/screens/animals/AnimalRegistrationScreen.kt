package com.example.patasfelizes.ui.screens.animals

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalRegistrationScreen(
    navController: NavHostController,
    onSave: (Animal) -> Unit
) {
    AnimalFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}
