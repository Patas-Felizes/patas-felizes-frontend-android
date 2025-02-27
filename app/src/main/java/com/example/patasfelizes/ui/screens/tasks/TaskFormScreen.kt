package com.example.patasfelizes.ui.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.DatePickerField
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.team.TeamListViewModel
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    navController: NavHostController,
    initialTask: Task? = null,
    onSave: (Task) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel(),
    teamViewModel: TeamListViewModel = viewModel()
) {
    var tipo by remember { mutableStateOf(TextFieldValue(initialTask?.tipo ?: "")) }
    var descricao by remember { mutableStateOf(TextFieldValue(initialTask?.descricao ?: "")) }
    var dataTarefa by remember { mutableStateOf(TextFieldValue(initialTask?.data_tarefa ?: "")) }

    var selectedAnimalId by remember { mutableStateOf(initialTask?.animal_id) }
    var selectedVoluntaryId by remember { mutableStateOf(initialTask?.voluntario_id) }

    val animals by animalViewModel.animals.collectAsState()
    val volunteers by teamViewModel.voluntarios.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    // Carregar dados ao iniciar a tela
    LaunchedEffect(Unit) {
        animalViewModel.reloadAnimals()
        teamViewModel.reloadVoluntarios()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    BoxWithProgressBar(isLoading = false) {
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

                // Aplicando AnimatedVisibility ao conteúdo do formulário
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                    exit = fadeOut()
                ) {
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

                        DatePickerField(
                            label = "Data da Tarefa",
                            placeholder = "XX-XX-XXXX",
                            value = dataTarefa.text,
                            onDateSelected = { newDate ->
                                dataTarefa = TextFieldValue(newDate)
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        CustomDropdown(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome ?: "Selecione um animal",
                            options = animals.map { it.nome },
                            onOptionSelected = { selectedOption ->
                                selectedAnimalId = animals.find { it.nome == selectedOption }?.animal_id
                            },
                            label = "Animal",
                            placeholder = "Selecione um animal"
                        )

                        CustomDropdown(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            selectedOption = volunteers.find { it.voluntario_id == selectedVoluntaryId }?.nome ?: "Selecione um voluntário",
                            options = volunteers.map { it.nome },
                            onOptionSelected = { selectedOption ->
                                selectedVoluntaryId = volunteers.find { it.nome == selectedOption }?.voluntario_id
                            },
                            label = "Voluntário",
                            placeholder = "Selecione um voluntário"
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

                                    val task = Task(
                                        tarefa_id = initialTask?.tarefa_id ?: 0,
                                        tipo = tipo.text.trim(),
                                        descricao = descricao.text.trim(),
                                        data_tarefa = dataTarefa.text.trim(),
                                        data_cadastro = LocalDate.now().toString(),
                                        animal_id = selectedAnimalId,
                                        voluntario_id = selectedVoluntaryId
                                    )

                                    onSave(task)
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
    }
}