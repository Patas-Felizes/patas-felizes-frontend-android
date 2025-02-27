package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel
import com.example.patasfelizes.ui.viewmodels.donation.DonationDetailsState
import com.example.patasfelizes.ui.viewmodels.donation.DonationDetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

// Componente DetailRow local que aceita valores nulos para evitar o erro
@Composable
private fun DonationDetailRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value ?: "Não informado",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.6f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsDonationScreen(
    navController: NavHostController,
    donationId: Int,
    viewModel: DonationDetailsViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel(),
    campaignViewModel: CampaignListViewModel = viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(donationId) {
        viewModel.loadDonation(donationId)
        animalViewModel.reloadAnimals()
        campaignViewModel.reloadCampaigns()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    BoxWithProgressBar(isLoading = uiState is DonationDetailsState.Loading) {
        when (val state = uiState) {
            is DonationDetailsState.Loading -> {
                // Loading já é mostrado pelo BoxWithProgressBar
            }
            is DonationDetailsState.Error -> {
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
            is DonationDetailsState.Success -> {
                val donation = state.donation
                val animal = donation.animal_id?.let { animalId ->
                    animals.find { it.animal_id == animalId }
                }
                val campaign = donation.companha_id?.let { campanhaId ->
                    campaigns.find { it.campanha_id == campanhaId }
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
                                Spacer(modifier = Modifier.height(24.dp))

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.97f)
                                        .padding(horizontal = 16.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        // Usando nosso próprio componente DonationDetailRow que trata valores nulos
                                        DonationDetailRow(label = "Doador", value = donation.doador)
                                        DonationDetailRow(label = "Valor", value = "R$ ${donation.valor}")
                                        DonationDetailRow(label = "Data da Doação", value = donation.data_doacao)
                                        DonationDetailRow(label = "Data de Cadastro", value = donation.data_cadastro)
                                        DonationDetailRow(
                                            label = "Animal Relacionado",
                                            value = animal?.nome ?: "Doação Geral"
                                        )
                                        DonationDetailRow(
                                            label = "Campanha",
                                            value = campaign?.nome ?: "Sem campanha associada"
                                        )
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
                                        )
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
                                        onClick = { navController.navigate("editDonation/${donation.doacao_id}") },
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

                                Spacer(modifier = Modifier.height(11.dp))

                                Button(
                                    onClick = { showDeleteConfirmation = true },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(horizontal = 16.dp),
                                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "Remover doação",
                                        style = MaterialTheme.typography.labelSmall,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }

                        // Dialog mantido fora da animação
                        if (showDeleteConfirmation) {
                            AlertDialog(
                                onDismissRequest = { showDeleteConfirmation = false },
                                title = { Text("Confirmar Exclusão") },
                                text = {
                                    Text("Tem certeza que deseja remover esta doação permanentemente?")
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDeleteConfirmation = false
                                            viewModel.deleteDonation(donation.doacao_id) {
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