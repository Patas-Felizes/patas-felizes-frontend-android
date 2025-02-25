// AdoptionEditScreen.kt
package com.example.patasfelizes.ui.screens.adoptions

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
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionFormState
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionEditScreen(
    navController: NavHostController,
    adoptionId: Int,
    viewModel: AdoptionFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val (adoptionLoaded, setAdoptionLoaded) = remember { mutableStateOf<Adoption?>(null) }

    // Carregar a adoção quando a tela for inicializada
    LaunchedEffect(adoptionId) {
        viewModel.loadAdoption(adoptionId) { loadedAdoption ->
            setAdoptionLoaded(loadedAdoption)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is AdoptionFormState.Error -> {
                val errorMessage = (state as AdoptionFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Aguardar a adoção ser carregada antes de mostrar o formulário
    adoptionLoaded?.let { adoption ->
        AdoptionFormScreen(
            navController = navController,
            initialAdoption = adoption,
            onSave = { updatedAdoption -> viewModel.updateAdoption(adoptionId, updatedAdoption) { navController.navigateUp() } },
            isEditMode = true
        )
    }
}