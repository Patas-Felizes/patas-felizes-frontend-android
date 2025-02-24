package com.example.patasfelizes.ui.screens.campaigns

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignDetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsCampaignScreen(
    navController: NavHostController,
    campaignId: Int,
    viewModel: CampaignDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(campaignId) {
        viewModel.loadCampaign(campaignId)
    }

    Scaffold { innerPadding ->
        when (uiState) {
            is CampaignDetailsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CampaignDetailsState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = (uiState as CampaignDetailsState.Error).message)
                }
            }
            is CampaignDetailsState.Success -> {
                val campaign = (uiState as CampaignDetailsState.Success).campaign

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
                            DetailRow(label = "Nome", value = campaign.nome)
                            DetailRow(label = "Tipo", value = campaign.tipo)
                            DetailRow(label = "Descrição", value = campaign.descricao)
                            DetailRow(label = "Local", value = campaign.local)
                            DetailRow(label = "Data de Início", value = campaign.data_inicio)
                            DetailRow(label = "Data de Término", value = campaign.data_termino)
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
                            onClick = { navController.navigate("editCampaign/${campaign.campanha_id}") },
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
                            text = "Remover campanha",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    if (showDeleteConfirmation) {
                        AlertDialog(
                            onDismissRequest = { showDeleteConfirmation = false },
                            title = { Text("Confirmar Exclusão") },
                            text = { Text("Tem certeza que deseja remover esta campanha permanentemente?") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewModel.deleteCampaign(campaign.campanha_id) {
                                            navController.navigateUp()
                                        }
                                        showDeleteConfirmation = false
                                    }
                                ) {
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