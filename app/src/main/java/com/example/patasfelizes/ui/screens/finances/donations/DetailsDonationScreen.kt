package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.screens.adoptions.DetailRow
import com.example.patasfelizes.ui.viewmodels.donation.DonationDetailsState
import com.example.patasfelizes.ui.viewmodels.donation.DonationDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsDonationScreen(
    navController: NavHostController,
    donationId: Int,
    viewModel: DonationDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(donationId) {
        viewModel.loadDonation(donationId)
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

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Detalhes da Doação",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.97f)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DetailRow(label = "Doador", value = donation.doador)
                                DetailRow(label = "Valor", value = "R$ ${donation.valor}")
                                DetailRow(label = "Data da Doação", value = donation.data_doacao)
                                DetailRow(label = "Data de Cadastro", value = donation.data_cadastro)
                                DetailRow(
                                    label = "Animal Relacionado",
                                    value = if (donation.animal_id != null && donation.animal_id != 0)
                                        donation.animal_id.toString()
                                    else
                                        "Doação Geral"
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

