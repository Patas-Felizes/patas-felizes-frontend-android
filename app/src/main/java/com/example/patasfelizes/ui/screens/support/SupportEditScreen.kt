package com.example.patasfelizes.ui.screens.support

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.ui.viewmodels.support.SupportDetailsState
import com.example.patasfelizes.ui.viewmodels.support.SupportDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.support.SupportFormState
import com.example.patasfelizes.ui.viewmodels.support.SupportFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportEditScreen(
    navController: NavHostController,
    supportId: Int,
    viewModel: SupportDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    formViewModel: SupportFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState by formViewModel.state.collectAsState()
    val context = LocalContext.current

    // Carregar o support pelo ID
    LaunchedEffect(supportId) {
        viewModel.loadSupport(supportId)
    }

    // Monitorar os estados para tratar erros e sucesso
    LaunchedEffect(formState) {
        when (formState) {
            is SupportFormState.Error -> {
                val errorMessage = (formState as SupportFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is SupportFormState.Success -> {
                navController.navigateUp()
            }
            else -> {}
        }
    }

    // Mostrar o formulário quando o support for carregado com sucesso
    when (val state = uiState) {
        is SupportDetailsState.Success -> {
            SupportFormScreen(
                navController = navController,
                initialSupport = state.support,
                onSave = { updatedSupport ->
                    formViewModel.updateSupport(updatedSupport) {
                        navController.navigateUp()
                    }
                },
                isEditMode = true
            )
        }
        is SupportDetailsState.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
        else -> {
            // Loading state is handled by the SupportFormScreen
        }
    }
}