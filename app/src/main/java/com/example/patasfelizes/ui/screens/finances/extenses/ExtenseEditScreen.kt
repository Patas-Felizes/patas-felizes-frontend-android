package com.example.patasfelizes.ui.screens.finances.extenses

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.ui.viewmodels.extense.ExtenseFormState
import com.example.patasfelizes.ui.viewmodels.extense.ExtenseFormViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtenseEditScreen(
    navController: NavHostController,
    extense: Extense,
    viewModel: ExtenseFormViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is ExtenseFormState.Error -> {
                val errorMessage = (state as ExtenseFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    ExtenseFormScreen(
        navController = navController,
        initialExtense = extense,
        onSave = { updatedExtense ->
            viewModel.updateExtense(updatedExtense) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}