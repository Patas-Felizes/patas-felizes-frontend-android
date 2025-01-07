package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRegistrationScreen(
    navController: NavHostController,
    onSave: (Task) -> Unit
) {
    TaskFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}