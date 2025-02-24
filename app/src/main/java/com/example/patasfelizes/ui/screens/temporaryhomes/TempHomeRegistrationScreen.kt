package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeRegistrationScreen(
    navController: NavHostController,
    viewModel: TempHomeFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    TempHomeFormScreen(
        navController = navController,
        onSave = { tempHome ->
            viewModel.createTempHome(tempHome) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}