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
import com.example.patasfelizes.ui.viewmodels.support.SupportFormState
import com.example.patasfelizes.ui.viewmodels.support.SupportFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportEditScreen(
    navController: NavHostController,
    support: Support,
    viewModel: SupportFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is SupportFormState.Error -> {
                val errorMessage = (state as SupportFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    SupportFormScreen(
        navController = navController,
        initialSupport = support,
        onSave = { updatedSupport ->
            viewModel.updateSupport(updatedSupport) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}