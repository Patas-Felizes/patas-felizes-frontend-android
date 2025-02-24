package com.example.patasfelizes.ui.screens.temporaryhomes

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormState
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeEditScreen(
    navController: NavHostController,
    tempHome: TempHome,
    viewModel: TempHomeFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is TempHomeFormState.Error -> {
                val errorMessage = (state as TempHomeFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    TempHomeFormScreen(
        navController = navController,
        initialTempHome = tempHome,
        onSave = { updatedTempHome ->
            viewModel.updateTempHome(updatedTempHome) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}