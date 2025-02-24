package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.task.TaskFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRegistrationScreen(
    navController: NavHostController,
    viewModel: TaskFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    TaskFormScreen(
        navController = navController,
        onSave = { task ->
            viewModel.createTask(task) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}