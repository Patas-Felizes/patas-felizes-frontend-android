package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navController: NavHostController,
    task: Task,
    onSave: (Task) -> Unit
) {
    TaskFormScreen(
        navController = navController,
        initialTask = task,
        onSave = onSave,
        isEditMode = true
    )
}