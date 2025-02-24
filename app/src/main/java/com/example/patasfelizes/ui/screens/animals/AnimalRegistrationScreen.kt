package com.example.patasfelizes.ui.screens.animals

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalRegistrationScreen(
    navController: NavHostController,
    viewModel: AnimalFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    AnimalFormScreen(
        navController = navController,
        onSave = { animal ->
            viewModel.createAnimal(animal) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}
