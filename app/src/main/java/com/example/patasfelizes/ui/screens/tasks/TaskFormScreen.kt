package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    navController: NavHostController,
    initialTask: Task? = null,
    onSave: (Task) -> Unit,
    isEditMode: Boolean = false
) {
    var tipo by remember { mutableStateOf(TextFieldValue(initialTask?.tipo ?: "")) }
    var descricao by remember { mutableStateOf(TextFieldValue(initialTask?.descricao ?: "")) }
    var dataTarefa by remember { mutableStateOf(TextFieldValue(initialTask?.dataTarefa ?: "")) }

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

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                FormField(
                    label = "Tipo",
                    placeholder = "Informe o tipo da tarefa...",
                    value = tipo,
                    onValueChange = { tipo = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Descrição",
                    placeholder = "Descreva a tarefa...",
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Data da Tarefa",
                    placeholder = "YYYY-MM-DD",
                    value = dataTarefa,
                    onValueChange = { dataTarefa = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                            text = if (isEditMode) "Cancelar" else "Voltar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (tipo.text.isBlank() || descricao.text.isBlank() || dataTarefa.text.isBlank()) {
                                return@Button
                            }

                            val newTask = Task(
                                id = initialTask?.id ?: 0,
                                tipo = tipo.text.trim(),
                                descricao = descricao.text.trim(),
                                dataTarefa = dataTarefa.text.trim(),
                                dataCadastro = LocalDate.now(),
                                idAnimal = null,
                                idVoluntary = null
                            )

                            onSave(newTask)
                            navController.navigateUp()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Salvar",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}