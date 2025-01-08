package com.example.patasfelizes.ui.screens.procedures

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Procedure

@Composable
fun ProcedureEditScreen(
    navController: NavHostController,
    procedure: Procedure,
    onSave: (Procedure) -> Unit
) {
    ProcedureFormScreen(
        navController = navController,
        initialProcedure = procedure,
        onSave = onSave,
        isEditMode = true
    )
}