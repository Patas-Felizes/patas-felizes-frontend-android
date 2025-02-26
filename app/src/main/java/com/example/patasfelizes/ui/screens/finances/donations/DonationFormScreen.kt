package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.DatePickerField
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationFormScreen(
    navController: NavHostController,
    initialDonation: Donation? = null,
    onSave: (Donation) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel(),
    campaignViewModel: CampaignListViewModel = viewModel()
) {
    var doador by remember { mutableStateOf(TextFieldValue(initialDonation?.doador ?: "")) }
    var valor by remember { mutableStateOf(TextFieldValue(initialDonation?.valor ?: "")) }
    var dataDoacao by remember { mutableStateOf(TextFieldValue(initialDonation?.data_doacao ?: "")) }
    var selectedAnimalId by remember { mutableStateOf(initialDonation?.animal_id) }
    var selectedCampaignId by remember { mutableStateOf(initialDonation?.companha_id) }

    val animals by animalViewModel.animals.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    // Carregar dados ao iniciar a tela
    LaunchedEffect(Unit) {
        animalViewModel.reloadAnimals()
        campaignViewModel.reloadCampaigns()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    @Composable
    fun EditIcon() {
        if (isEditMode) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.8f)
            )
        }
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
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormField(
                        label = "Doador",
                        placeholder = "Nome do doador...",
                        value = doador,
                        onValueChange = { doador = it },
                        modifier = Modifier.padding(bottom = 16.dp),
                        trailingIcon = { EditIcon() }
                    )

                    FormField(
                        label = "Valor",
                        placeholder = "Informe o valor...",
                        value = valor,
                        onValueChange = { valor = it },
                        modifier = Modifier.padding(bottom = 16.dp),
                        trailingIcon = { EditIcon() }
                    )

                    DatePickerField(
                        label = "Data da Doação",
                        placeholder = "XX-XX-XXXX",
                        value = dataDoacao.text,
                        onDateSelected = { newDate ->
                            dataDoacao = TextFieldValue(newDate)
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomDropdown(
                        selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome ?: "",
                        placeholder = "Selecione o animal (opcional)...",
                        options = animals.map { it.nome },
                        onOptionSelected = { nome ->
                            selectedAnimalId = animals.find { it.nome == nome }?.animal_id
                        },
                        label = "Animal",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomDropdown(
                        selectedOption = campaigns.find { it.campanha_id == selectedCampaignId }?.nome ?: "",
                        placeholder = "Selecione a campanha (opcional)...",
                        options = campaigns.map { it.nome },
                        onOptionSelected = { nome ->
                            selectedCampaignId = campaigns.find { it.nome == nome }?.campanha_id
                        },
                        label = "Campanha",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = if (isEditMode) "Cancelar" else "Voltar",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onTertiary,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                if (doador.text.isBlank() || valor.text.isBlank() || dataDoacao.text.isBlank()) {
                                    return@Button
                                }

                                val donation = Donation(
                                    doacao_id = initialDonation?.doacao_id ?: 0,
                                    doador = doador.text.trim(),
                                    valor = valor.text.trim(),
                                    data_doacao = dataDoacao.text.trim(),
                                    animal_id = selectedAnimalId,
                                    companha_id = selectedCampaignId,
                                    data_cadastro = LocalDate.now().toString()
                                )

                                onSave(donation)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Salvar",
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}