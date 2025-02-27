package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.DatePickerField
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.hosts.HostListViewModel
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.models.Host
import com.example.patasfelizes.repository.HostRepository
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeFormScreen(
    navController: NavHostController,
    initialTempHome: TempHome? = null,
    onSave: (TempHome) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel(),
    hostViewModel: HostListViewModel = viewModel()
) {
    var selectedAnimalId by remember { mutableStateOf(initialTempHome?.animal_id) }
    var selectedHostId by remember { mutableStateOf(initialTempHome?.hospedeiro_id) }
    var periodo by remember { mutableStateOf(TextFieldValue(initialTempHome?.periodo ?: "")) }
    var dataHospedagem by remember { mutableStateOf(TextFieldValue(initialTempHome?.data_hospedagem ?: LocalDate.now().toString())) }

    // Estados para o diálogo de adicionar novo hospedeiro
    var showAddHostDialog by remember { mutableStateOf(false) }
    var newHostName by remember { mutableStateOf("") }
    var newHostEmail by remember { mutableStateOf("") }
    var newHostPhone by remember { mutableStateOf("") }

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    // Repositório para criação do hospedeiro (similar ao AdoptersRepository)
    val hostRepository = remember { HostRepository() }

    val animals by animalViewModel.animals.collectAsState()
    val hosts by hostViewModel.hosts.collectAsState()

    // Ativar a visibilidade após carregar
    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    BoxWithProgressBar(isLoading = false) {
        Scaffold { innerPadding ->
            // Envolvendo o conteúdo com Box e AnimatedVisibility
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            CustomDropdown(
                                label = "Animal",
                                selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome
                                    ?: "Selecione o animal",
                                options = animals.map { it.nome },
                                onOptionSelected = { selectedOption ->
                                    selectedAnimalId = animals.find { it.nome == selectedOption }?.animal_id
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Row com dropdown de Hospedeiro e botão para adicionar novo hospedeiro
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomDropdown(
                                    label = "Hospedeiro",
                                    selectedOption = hosts.find { it.hospedeiro_id == selectedHostId }?.nome
                                        ?: "Selecione o hospedeiro",
                                    options = hosts.map { it.nome },
                                    onOptionSelected = { selectedOption ->
                                        selectedHostId = hosts.find { it.nome == selectedOption }?.hospedeiro_id
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                IconButton(
                                    onClick = { showAddHostDialog = true },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Adicionar hospedeiro",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            FormField(
                                label = "Período",
                                placeholder = "Período da hospedagem",
                                value = periodo,
                                onValueChange = { periodo = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            DatePickerField(
                                label = "Data de Hospedagem",
                                placeholder = "YYYY-MM-DD",
                                value = dataHospedagem.text,
                                onDateSelected = { newDate ->
                                    dataHospedagem = TextFieldValue(newDate)
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { navController.navigateUp() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Cancelar")
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = {
                                        if (selectedAnimalId == null || selectedHostId == null ||
                                            periodo.text.isBlank() || dataHospedagem.text.isBlank()
                                        ) {
                                            return@Button
                                        }

                                        val tempHome = TempHome(
                                            lar_temporario_id = initialTempHome?.lar_temporario_id ?: 0,
                                            animal_id = selectedAnimalId!!,
                                            hospedeiro_id = selectedHostId!!,
                                            periodo = periodo.text,
                                            data_hospedagem = dataHospedagem.text,
                                            data_cadastro = initialTempHome?.data_cadastro ?: LocalDate.now().toString()
                                        )

                                        onSave(tempHome)
                                    },
                                    enabled = selectedAnimalId != null && selectedHostId != null,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Salvar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddHostDialog) {
        AlertDialog(
            onDismissRequest = { showAddHostDialog = false },
            title = { Text("Adicionar Hospedeiro") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newHostName,
                        onValueChange = { newHostName = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newHostEmail,
                        onValueChange = { newHostEmail = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newHostPhone,
                        onValueChange = { newHostPhone = it },
                        label = { Text("Telefone") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newHost = Host(
                            nome = newHostName,
                            email = newHostEmail,
                            telefone = newHostPhone,
                            moradia = "" // You might want to add a moradia field to the dialog
                        )
                        // Cria o novo hospedeiro utilizando o repositório
                        hostRepository.createHost(newHost,
                            onSuccess = { createdHost ->
                                hostViewModel.reloadHosts()
                                showAddHostDialog = false
                                newHostName = ""
                                newHostEmail = ""
                                newHostPhone = ""
                            },
                            onError = { errorMessage ->
                                // Trate o erro (ex.: exibir uma mensagem)
                            }
                        )
                    }
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddHostDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}