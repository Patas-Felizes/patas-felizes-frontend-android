package com.example.patasfelizes.ui.screens.animals

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalEditScreen(
    navController: NavHostController,
    animal: Animal,
    viewModel: AnimalFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is AnimalFormState.Error -> {
                val errorMessage = (state as AnimalFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    AnimalFormScreen(
        navController = navController,
        initialAnimal = animal,
        onSave = { updatedAnimal ->
            viewModel.updateAnimal(updatedAnimal) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}