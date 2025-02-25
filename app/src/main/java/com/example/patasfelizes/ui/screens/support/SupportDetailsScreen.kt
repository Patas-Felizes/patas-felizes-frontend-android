package com.example.patasfelizes.ui.screens.support

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.support.SupportDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.support.SupportDetailsState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportDetailsScreen(
    navController: NavHostController,
    supportId: Int,
    viewModel: SupportDetailsViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

    LaunchedEffect(supportId) {
        viewModel.loadSupport(supportId)
        animalViewModel.reloadAnimals()
    }

    BoxWithProgressBar(isLoading = uiState is SupportDetailsState.Loading) {
        when (val state = uiState) {
            is SupportDetailsState.Loading -> {
                // Loading já é mostrado pelo BoxWithProgressBar
            }
            is SupportDetailsState.Error -> {
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
            is SupportDetailsState.Success -> {
                val support = state.support
                val animal = animals.find { it.animal_id == support.animal_id }

                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .padding(horizontal = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DetailRow("Animal", animal?.nome ?: "Animal não encontrado")
                                DetailRow("Padrinho", support.nome_apadrinhador)
                                DetailRow("Valor", "R$ ${support.valor}")
                                DetailRow("Regularidade", support.regularidade)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { navController.navigateUp() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Voltar")
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = { navController.navigate("editSupport/${support.apadrinhamento_id}") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }
                        }

                        Spacer(modifier = Modifier.height(11.dp))

                        Button(
                            onClick = { showDeleteConfirmation = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Remover apadrinhamento")
                        }

                        if (showDeleteConfirmation) {
                            AlertDialog(
                                onDismissRequest = { showDeleteConfirmation = false },
                                title = { Text("Confirmar Exclusão") },
                                text = { Text("Tem certeza que deseja remover este apadrinhamento permanentemente?") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDeleteConfirmation = false
                                            viewModel.deleteSupport(support.apadrinhamento_id) {
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
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}