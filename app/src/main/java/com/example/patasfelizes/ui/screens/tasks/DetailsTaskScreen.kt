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
import com.example.patasfelizes.models.TaskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTaskScreen(
    navController: NavHostController,
    taskId: Int
) {
    val task = TaskList.find { it.id == taskId }
        ?: return

    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
                    DetailRow(label = "Tipo", value = task.tipo)
                    DetailRow(label = "Descrição", value = task.descricao)
                    DetailRow(label = "Data da Tarefa", value = task.dataTarefa)
                    DetailRow(
                        label = "Animal Relacionado",
                        value = task.idAnimal?.nome ?: "Não associado"
                    )
                    DetailRow(
                        label = "Voluntário Relacionado",
                        value = task.idVoluntary?.nome ?: "Não associado"
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
                    onClick = { navController.navigate("editTask/${task.id}") },
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

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirmar Exclusão") },
                    text = { Text("Tem certeza que deseja remover esta tarefa permanentemente?") },
                    confirmButton = {
                        TextButton(onClick = { navController.navigateUp() }) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmation = false }) {
                            Text("Cancelar")
                        }
                    }
                )
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