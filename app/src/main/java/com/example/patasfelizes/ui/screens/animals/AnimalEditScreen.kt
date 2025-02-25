package com.example.patasfelizes.ui.screens.animals

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.animals.AnimalDetailsState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalEditScreen(
    navController: NavHostController,
    animalId: Int,
    detailsViewModel: AnimalDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    formViewModel: AnimalFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by detailsViewModel.uiState.collectAsState()
    val formState by formViewModel.state.collectAsState()
    val context = LocalContext.current

    // Carregar o animal pelo ID
    LaunchedEffect(animalId) {
        detailsViewModel.loadAnimal(animalId)
    }

    // Monitorar os estados para tratar erros e sucesso
    LaunchedEffect(formState) {
        when (formState) {
            is AnimalFormState.Error -> {
                val errorMessage = (formState as AnimalFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is AnimalFormState.Success -> {
                navController.navigateUp()
            }
            else -> {}
        }
    }

    // Mostrar o formulário quando o animal for carregado com sucesso
    when (val state = uiState) {
        is AnimalDetailsState.Success -> {
            AnimalFormScreen(
                navController = navController,
                initialAnimal = state.animal,
                onSave = { updatedAnimal ->
                    formViewModel.updateAnimal(updatedAnimal) {
                        navController.navigateUp()
                    }
                },
                isEditMode = true
            )
        }
        is AnimalDetailsState.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
        else -> {
            // Loading state is handled by the AnimalFormScreen
        }
    }
}