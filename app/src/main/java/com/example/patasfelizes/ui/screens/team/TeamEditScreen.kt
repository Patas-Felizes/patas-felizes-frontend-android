package com.example.patasfelizes.ui.screens.team

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.ui.screens.animals.AnimalFormScreen
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalFormViewModel
import com.example.patasfelizes.ui.viewmodels.team.TeamFormState
import com.example.patasfelizes.ui.viewmodels.team.TeamFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamEditScreen(
    navController: NavHostController,
    voluntary: Voluntary,
    viewModel: TeamFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is TeamFormState.Error -> {
                val errorMessage = (state as AnimalFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    TeamFormScreen(
        navController = navController,
        initialVoluntary = voluntary,
        onSave = { updatedVoluntario ->
            viewModel.updateVoluntario(updatedVoluntario) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}