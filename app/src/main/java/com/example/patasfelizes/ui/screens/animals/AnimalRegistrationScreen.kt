package com.example.patasfelizes.ui.screens.animals

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.repository.AnimalsRepository
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalRegistrationScreen(
    navController: NavHostController,
    viewModel: AnimalFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    AnimalFormScreen(
        navController = navController,
        onSave = { animal ->
            viewModel.createAnimal(
                animal = animal,
                onComplete = {
                    navController.navigateUp()
                }
            )
        },
        isEditMode = false
    )
}
