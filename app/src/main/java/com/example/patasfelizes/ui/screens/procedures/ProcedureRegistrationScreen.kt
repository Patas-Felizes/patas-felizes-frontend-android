package com.example.patasfelizes.ui.screens.procedures

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.viewmodels.procedure.ProcedureFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureRegistrationScreen(
    navController: NavHostController,
    viewModel: ProcedureFormViewModel = viewModel()
) {
    ProcedureFormScreen(
        navController = navController,
        onSave = { procedure ->
            viewModel.createProcedure(procedure) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}