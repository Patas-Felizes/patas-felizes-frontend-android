package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.task.TaskDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.task.TaskDetailsState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.team.TeamListViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTaskScreen(
    navController: NavHostController,
    taskId: Int,
    viewModel: TaskDetailsViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel(),
    teamViewModel: TeamListViewModel = viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val volunteers by teamViewModel.voluntarios.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
        animalViewModel.reloadAnimals()
        teamViewModel.reloadVoluntarios()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    BoxWithProgressBar(isLoading = uiState is TaskDetailsState.Loading) {
        when (val state = uiState) {
            is TaskDetailsState.Loading -> {
                // Loading já é mostrado pelo BoxWithProgressBar
            }
            is TaskDetailsState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigateUp() }) {
                        Text("Voltar")
                    }
                }
            }
            is TaskDetailsState.Success -> {
                val task = state.task
                val animal = task.animal_id?.let { animalId ->
                    animals.find { it.animal_id == animalId }
                }
                val volunteer = task.voluntario_id?.let { voluntarioId ->
                    volunteers.find { it.voluntario_id == voluntarioId }
                }

                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        )

                        // Aplicando AnimatedVisibility ao conteúdo principal
                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                            exit = fadeOut()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.97f)
                                        .padding(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        DetailRow(label = "Tipo", value = task.tipo ?: "")
                                        DetailRow(label = "Descrição", value = task.descricao ?: "")
                                        DetailRow(label = "Data da Tarefa", value = task.data_tarefa ?: "")
                                        DetailRow(
                                            label = "Animal Relacionado",
                                            value = animal?.nome ?: "Não associado"
                                        )
                                        DetailRow(
                                            label = "Voluntário Relacionado",
                                            value = volunteer?.nome ?: "Não associado"
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = { navController.navigateUp() },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary
                                        )
                                    ) {
                                        Text(
                                            text = "Voltar",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Button(
                                        onClick = { navController.navigate("editTask/${task.tarefa_id}") },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Editar",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(11.dp))

                                Button(
                                    onClick = { showDeleteConfirmation = true },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "Remover tarefa",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }

                        // Dialog mantido fora da animação
                        if (showDeleteConfirmation) {
                            AlertDialog(
                                onDismissRequest = { showDeleteConfirmation = false },
                                title = { Text("Confirmar Exclusão") },
                                text = { Text("Tem certeza que deseja remover esta tarefa permanentemente?") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDeleteConfirmation = false
                                            viewModel.deleteTask(task.tarefa_id) {
                                                navController.navigateUp()
                                            }
                                        },
                                        enabled = !state.isDeleting
                                    ) {
                                        Text("Confirmar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showDeleteConfirmation = false },
                                        enabled = !state.isDeleting
                                    ) {
                                        Text("Cancelar")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}