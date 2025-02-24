package com.example.patasfelizes.ui.screens.support

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.support.SupportFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportRegistrationScreen(
    navController: NavHostController,
    viewModel: SupportFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    SupportFormScreen(
        navController = navController,
        onSave = { support ->
            viewModel.createSupport(support) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}