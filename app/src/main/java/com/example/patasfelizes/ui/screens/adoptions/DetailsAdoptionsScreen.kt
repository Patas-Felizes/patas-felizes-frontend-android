package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.adopters.AdopterListViewModel
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionDetailsState
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionDetailsViewModel
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsAdoptionsScreen(
    navController: NavHostController,
    adoptionId: Int,
    viewModel: AdoptionDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    animalViewModel: AnimalListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    adopterViewModel: AdopterListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    campaignViewModel: CampaignListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val adopters by adopterViewModel.adopters.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()

    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(adoptionId) {
        viewModel.loadAdoption(adoptionId)
        animalViewModel.reloadAnimals()
        adopterViewModel.reloadAdopters()
        campaignViewModel.reloadCampaigns()

        isVisible.value = true
    }

    when (val state = uiState) {
        is AdoptionDetailsState.Loading -> {
            BoxWithProgressBar(isLoading = true) {}
        }
        is AdoptionDetailsState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Erro ao carregar detalhes da adoção: ${state.message}",
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
        is AdoptionDetailsState.Success -> {
            val adoption = state.adoption
            val animal = animals.find { it.animal_id == adoption.animal_id }
            val adopter = adopters.find { it.adotante_id == adoption.adotante_id }

            BoxWithProgressBar(isLoading = state.isDeleting) {
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

                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                            exit = fadeOut()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .padding(horizontal = 16.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        DetailRow(label = "Animal", value = animal?.nome ?: "Animal não encontrado")
                                        DetailRow(label = "Adotante", value = adopter?.nome ?: "Adotante não encontrado")
                                        DetailRow(label = "Data da Adoção", value = adoption.data_adocao)
                                        DetailRow(label = "Data de Cadastro", value = adoption.data_cadastro)
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
                                        onClick = { navController.navigate("editAdoption/${adoption.adocao_id}") },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Editar",
                                            style = MaterialTheme.typography.labelSmall
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
                                        text = "Remover adoção",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }

                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }
                    }
                }
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirmar Exclusão") },
                    text = { Text("Tem certeza que deseja remover esta adoção?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.deleteAdoption(adoption.adocao_id) {
                                    navController.navigateUp()
                                }
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDeleteConfirmation = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
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