package com.example.patasfelizes.ui.screens.finances.extenses

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Extense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtenseEditScreen(
    navController: NavHostController,
    extense: Extense,
    onSave: (Extense) -> Unit
) {
    ExtenseFormScreen(
        navController = navController,
        initialExtense = extense,
        onSave = onSave,
        isEditMode = true
    )
}