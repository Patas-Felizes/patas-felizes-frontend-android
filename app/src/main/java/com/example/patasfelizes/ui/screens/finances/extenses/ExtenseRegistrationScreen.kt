package com.example.patasfelizes.ui.screens.finances.extenses

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.viewmodels.extense.ExtenseFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtenseRegistrationScreen(
    navController: NavHostController,
    viewModel: ExtenseFormViewModel = viewModel()
) {
    ExtenseFormScreen(
        navController = navController,
        onSave = { extense ->
            viewModel.createExtense(extense) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}