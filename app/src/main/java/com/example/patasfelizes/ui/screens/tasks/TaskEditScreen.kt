package com.example.patasfelizes.ui.screens.tasks

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
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.ui.viewmodels.task.TaskFormState
import com.example.patasfelizes.ui.viewmodels.task.TaskFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navController: NavHostController,
    taskId: Int,
    viewModel: TaskFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val (taskLoaded, setTaskLoaded) = remember { mutableStateOf<Task?>(null) }

    // Carregar a tarefa quando a tela for inicializada
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId) { task ->
            setTaskLoaded(task)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is TaskFormState.Error -> {
                val errorMessage = (state as TaskFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Aguardar a tarefa ser carregada antes de mostrar o formulário
    taskLoaded?.let { task ->
        TaskFormScreen(
            navController = navController,
            initialTask = task,
            onSave = { updatedTask ->
                viewModel.updateTask(updatedTask) {
                    navController.navigateUp()
                }
            },
            isEditMode = true
        )
    }
}