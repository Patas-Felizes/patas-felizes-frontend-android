package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeDetailsState
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.hosts.HostListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomesDetailsScreen(
    navController: NavHostController,
    tempHomeId: Int,
    viewModel: TempHomeDetailsViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel(),
    hostViewModel: HostListViewModel = viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val hosts by hostViewModel.hosts.collectAsState()

    LaunchedEffect(tempHomeId) {
        viewModel.loadTempHome(tempHomeId)
    }

    when (val state = uiState) {
        is TempHomeDetailsState.Loading -> {
            BoxWithProgressBar(isLoading = true) {}
        }
        is TempHomeDetailsState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Erro ao carregar detalhes do lar temporário: ${state.message}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Button(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Voltar")
                }
            }
        }
        is TempHomeDetailsState.Success -> {
            val tempHome = state.tempHome
            val animalName = animals.find { it.animal_id == tempHome.animal_id }?.nome ?: "Animal não encontrado"
            val hostName = hosts.find { it.hospedeiro_id == tempHome.hospedeiro_id }?.nome ?: "Hospedeiro não encontrado"

            BoxWithProgressBar(isLoading = state.isDeleting) {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = animalName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DetailRow(label = "Responsável", value = hostName)
                                DetailRow(label = "Período", value = tempHome.periodo)
                                DetailRow(label = "Data de Hospedagem", value = tempHome.data_hospedagem)
                                DetailRow(label = "Data de Cadastro", value = tempHome.data_cadastro)
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
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                ),
                            ) {
                                Text(
                                    text = "Voltar",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = { navController.navigate("editTemporaryHome/${tempHome.lar_temporario_id}") },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "Editar",
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Button(
                            onClick = { showDeleteConfirmation = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Remover lar temporário",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        if (showDeleteConfirmation) {
                            AlertDialog(
                                onDismissRequest = { showDeleteConfirmation = false },
                                title = { Text("Confirmar Exclusão") },
                                text = { Text("Tem certeza que deseja remover este lar temporário?") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.deleteTempHome(tempHome.lar_temporario_id) {
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

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
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