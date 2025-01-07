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
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.models.TaskList
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

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

                TaskList(
                    tasks = TaskList.filter {
                        it.descricao.contains(searchQuery.text, ignoreCase = true) ||
                                it.tipo.contains(searchQuery.text, ignoreCase = true)
                    },
                    onTaskClick = { task ->
                        navController.navigate("taskDetails/${task.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
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
                MaterialTheme.colorScheme.background
            }
            TaskListItem(
                task = task,
                backgroundColor = backgroundColor,
                onClick = { onTaskClick(task) }
            )
        }
    }
}

@Composable
fun TaskListItem(
    task: Task,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = task.tipo,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = task.descricao,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${task.dataTarefa}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}