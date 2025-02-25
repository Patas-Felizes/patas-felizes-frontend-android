package com.example.patasfelizes.ui.screens.temporaryhomes

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeDetailsState
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormState
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeEditScreen(
    navController: NavHostController,
    tempHomeId: Int,
    viewModel: TempHomeDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    formViewModel: TempHomeFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState by formViewModel.state.collectAsState()
    val context = LocalContext.current

    // Carregar o lar temporário pelo ID
    LaunchedEffect(tempHomeId) {
        viewModel.loadTempHome(tempHomeId)
    }

    // Monitorar os estados para tratar erros e sucesso
    LaunchedEffect(formState) {
        when (formState) {
            is TempHomeFormState.Error -> {
                val errorMessage = (formState as TempHomeFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is TempHomeFormState.Success -> {
                navController.navigateUp()
            }
            else -> {}
        }
    }

    // Mostrar o formulário quando o lar temporário for carregado com sucesso
    when (val state = uiState) {
        is TempHomeDetailsState.Success -> {
            TempHomeFormScreen(
                navController = navController,
                initialTempHome = state.tempHome,
                onSave = { updatedTempHome ->
                    formViewModel.updateTempHome(updatedTempHome) {
                        navController.navigateUp()
                    }
                },
                isEditMode = true
            )
        }
        is TempHomeDetailsState.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
        else -> {
            // Loading state is handled by the TempHomeFormScreen
        }
    }
}