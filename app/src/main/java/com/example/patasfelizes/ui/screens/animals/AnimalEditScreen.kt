package com.example.patasfelizes.ui.screens.animals

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalEditScreen(
    navController: NavHostController,
    animal: Animal,
    onSave: (Animal) -> Unit
) {
    AnimalFormScreen(
        navController = navController,
        initialAnimal = animal,
        onSave = onSave,
        isEditMode = true
    )
}