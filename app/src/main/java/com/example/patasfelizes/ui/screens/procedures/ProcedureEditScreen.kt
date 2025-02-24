package com.example.patasfelizes.ui.screens.procedures

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.ui.viewmodels.procedure.ProcedureFormState
import com.example.patasfelizes.ui.viewmodels.procedure.ProcedureFormViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureEditScreen(
    navController: NavHostController,
    procedure: Procedure,
    viewModel: ProcedureFormViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is ProcedureFormState.Error -> {
                val errorMessage = (state as ProcedureFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    ProcedureFormScreen(
        navController = navController,
        initialProcedure = procedure,
        onSave = { updatedProcedure ->
            viewModel.updateProcedure(updatedProcedure) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}