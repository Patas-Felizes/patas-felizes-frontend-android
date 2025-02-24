package com.example.patasfelizes.ui.screens.team

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.team.TeamFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRegistrationScreen(
    navController: NavHostController,
    viewModel: TeamFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    TeamFormScreen(
        navController = navController,
        onSave = { voluntary ->
            viewModel.createVoluntario(voluntary) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}
