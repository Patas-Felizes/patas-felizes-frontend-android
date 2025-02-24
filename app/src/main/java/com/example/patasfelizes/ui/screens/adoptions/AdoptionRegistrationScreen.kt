package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionRegistrationScreen(
    navController: NavHostController,
    viewModel: AdoptionFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    AdoptionFormScreen(
        navController = navController,
        onSave = { adoption ->
            viewModel.createAdoption(adoption) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}