package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.ui.components.CustomFloatingActionButton
import com.example.patasfelizes.ui.components.CustomSearchBar
import com.example.patasfelizes.ui.components.FilterComponent
import com.example.patasfelizes.ui.components.FilterOption
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.hosts.HostListViewModel
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemporaryHomesScreen(
    navController: NavHostController,
    viewModel: TempHomeListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val tempHomes by viewModel.tempHomes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadTempHomes()
    }

    val filterOptions = remember {
        listOf(
            FilterOption("Curto prazo"),
            FilterOption("Médio prazo"),
            FilterOption("Longo prazo")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    val filteredTempHomes = tempHomes.filter { tempHome ->
        // Implement search logic based on available fields
        searchQuery.text.isEmpty() || tempHome.periodo.contains(searchQuery.text, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addTemporaryHome") },
                contentDescription = "Adicionar Lar Temporário"
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

                Spacer(modifier = Modifier.height(8.dp))

                TemporaryHomeList(
                    tempHomes = filteredTempHomes,
                    onTempHomeClick = { tempHome ->
                        navController.navigate("temporaryHomeDetails/${tempHome.lar_temporario_id}")
                    }
                )
            }
        }
    }
}

@Composable
fun TemporaryHomeList(
    tempHomes: List<TempHome>,
    onTempHomeClick: (TempHome) -> Unit,
    animalViewModel: AnimalListViewModel = viewModel(),
    hostViewModel: HostListViewModel = viewModel()
) {
    val animals by animalViewModel.animals.collectAsState()
    val hosts by hostViewModel.hosts.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(tempHomes) { index, tempHome ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }

            val animalName = animals.find { it.animal_id == tempHome.animal_id }?.nome ?: "Animal não encontrado"
            val hostName = hosts.find { it.hospedeiro_id == tempHome.hospedeiro_id }?.nome ?: "Hospedeiro não encontrado"

            TemporaryHomeListItem(
                tempHome = tempHome,
                animalName = animalName,
                hostName = hostName,
                backgroundColor = backgroundColor,
                onClick = { onTempHomeClick(tempHome) }
            )
        }
    }
}

@Composable
fun TemporaryHomeListItem(
    tempHome: TempHome,
    animalName: String,
    hostName: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = animalName,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Responsável: $hostName",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Período: ${tempHome.periodo}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Data de Hospedagem: ${tempHome.data_hospedagem}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}