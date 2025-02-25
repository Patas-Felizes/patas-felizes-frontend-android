package com.example.patasfelizes.ui.screens.team

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.ui.viewmodels.team.TeamFormState
import com.example.patasfelizes.ui.viewmodels.team.TeamFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamEditScreen(
    navController: NavHostController,
    voluntarioId: Int,
    viewModel: TeamFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val (voluntaryLoaded, setVoluntaryLoaded) = remember { mutableStateOf<Voluntary?>(null) }

    // Carregar o voluntário quando a tela for inicializada
    LaunchedEffect(voluntarioId) {
        viewModel.loadVoluntario(voluntarioId) { voluntary ->
            setVoluntaryLoaded(voluntary)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is TeamFormState.Error -> {
                val errorMessage = (state as TeamFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Aguardar o voluntário ser carregado antes de mostrar o formulário
    voluntaryLoaded?.let { voluntary ->
        TeamFormScreen(
            navController = navController,
            initialVoluntary = voluntary,
            onSave = { updatedVoluntary ->
                viewModel.updateVoluntario(updatedVoluntary) {
                    navController.navigateUp()
                }
            },
            isEditMode = true
        )
    }
}