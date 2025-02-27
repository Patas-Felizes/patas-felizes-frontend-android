package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.ui.viewmodels.task.TaskListViewModel
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.team.TeamListViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    navController: NavHostController,
    viewModel: TaskListViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel(),
    teamViewModel: TeamListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val tasks by viewModel.tasks.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val volunteers by teamViewModel.voluntarios.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    val filterOptions = remember {
        listOf(
            FilterOption("Medicação"),
            FilterOption("Alimentação"),
            FilterOption("Limpeza"),
            FilterOption("Veterinário"),
            FilterOption("Socialização")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    val filteredTasks = tasks.filter {
        it.tipo.contains(searchQuery.text, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.reloadTasks()
        animalViewModel.reloadAnimals()
        teamViewModel.reloadVoluntarios()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addTask") },
                contentDescription = "Adicionar Tarefa"
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                CustomSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    placeholderText = "Pesquisar tarefa...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Envolvendo a lista com Box e AnimatedVisibility
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Usando qualificação completa para evitar conflitos de escopo
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isVisible.value,
                        enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                        exit = fadeOut()
                    ) {
                        TaskList(
                            tasks = tasks.filter { task ->
                                (searchQuery.text.isEmpty() ||
                                        task.descricao.contains(searchQuery.text, ignoreCase = true) ||
                                        task.tipo.contains(searchQuery.text, ignoreCase = true)) &&
                                        (currentFilters.none { it.isSelected } ||
                                                currentFilters.any { filter -> filter.isSelected && filter.name == task.tipo })
                            },
                            animals = animals,
                            volunteers = volunteers,
                            onTaskClick = { task ->
                                navController.navigate("taskDetails/${task.tarefa_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    animals: List<com.example.patasfelizes.models.Animal>,
    volunteers: List<com.example.patasfelizes.models.Voluntary>,
    onTaskClick: (Task) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.onPrimary
            }
            val animal = task.animal_id?.let { animalId ->
                animals.find { it.animal_id == animalId }
            }
            val volunteer = task.voluntario_id?.let { voluntarioId ->
                volunteers.find { it.voluntario_id == voluntarioId }
            }
            TaskListItem(
                task = task,
                animalName = animal?.nome,
                volunteerName = volunteer?.nome,
                backgroundColor = backgroundColor,
                onClick = { onTaskClick(task) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TaskListItem(
    task: Task,
    animalName: String?,
    volunteerName: String?,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = task.tipo?: "",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = task.descricao?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${task.data_tarefa?: ""}",
                style = MaterialTheme.typography.bodySmall
            )
            animalName?.let {
                Text(
                    text = "Animal: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            volunteerName?.let {
                Text(
                    text = "Voluntário: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}