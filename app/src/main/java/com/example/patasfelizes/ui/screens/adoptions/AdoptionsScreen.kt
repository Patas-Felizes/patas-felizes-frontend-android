// AdoptionsScreen.kt
package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.ui.viewmodels.adopters.AdopterListViewModel
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionListViewModel
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionsScreen(
    navController: NavHostController,
    viewModel: AdoptionListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    animalViewModel: AnimalListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    adopterViewModel: AdopterListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    campaignViewModel: CampaignListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val adoptions by viewModel.adoptions.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val adopters by adopterViewModel.adopters.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadAdoptions()
        animalViewModel.reloadAnimals()
        adopterViewModel.reloadAdopters()
        campaignViewModel.reloadCampaigns()
    }

    val filterOptions = remember {
        listOf(
            FilterOption("Com devolução"),
            FilterOption("Sem devolução")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addAdoption") },
                contentDescription = "Adicionar Adoção"
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                CustomSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    placeholderText = "Pesquisar...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Box(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)
                    ) {
                        items(adoptions) { adoption ->
                            val animal = animals.find { it.animal_id == adoption.animal_id }
                            val adopter = adopters.find { it.adotante_id == adoption.adotante_id }
                            val campaign = campaigns.find { it.campanha_id == adoption.companha_id }

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 6.dp)
                                        .clickable {
                                            navController.navigate("adoptionDetails/${adoption.adocao_id}")
                                        }
                                        .fillMaxWidth(0.9f),
                                    elevation = CardDefaults.cardElevation(3.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = animal?.nome ?: "Animal não encontrado",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        Text(
                                            text = "Adotante: ${adopter?.nome ?: "Não encontrado"}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        Text(
                                            text = "Campanha: ${campaign?.nome ?: "Não encontrada"}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        Text(
                                            text = "Data da Adoção: ${adoption.data_adocao}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        if (adoption.data_devolucao.isNotEmpty()) {
                                            Text(
                                                text = "Devolvido em: ${adoption.data_devolucao}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}