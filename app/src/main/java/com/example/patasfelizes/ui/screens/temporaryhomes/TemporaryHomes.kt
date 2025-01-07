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
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.GuardianTemp
import com.example.patasfelizes.models.GuardianTempList
import com.example.patasfelizes.ui.components.CustomFloatingActionButton
import com.example.patasfelizes.ui.components.CustomSearchBar
import com.example.patasfelizes.ui.components.FilterComponent
import com.example.patasfelizes.ui.components.FilterOption
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemporaryHomesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filterOptions = remember {
        listOf(
            FilterOption("Curto prazo"),
            FilterOption("Médio prazo"),
            FilterOption("Longo prazo")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

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
                    guardians = GuardianTempList.filter {
                        it.petNome.contains(searchQuery.text, ignoreCase = true) ||
                                it.nome.contains(searchQuery.text, ignoreCase = true)
                    },
                    onGuardianClick = { guardian ->
                        navController.navigate("temporaryHomeDetails/${guardian.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun TemporaryHomeList(
    guardians: List<GuardianTemp>,
    onGuardianClick: (GuardianTemp) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(guardians) { index, guardian ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary // Cor 1
            } else {
                MaterialTheme.colorScheme.background // Cor 2
            }
            TemporaryHomeListItem(
                guardian = guardian,
                backgroundColor = backgroundColor,
                onClick = { onGuardianClick(guardian) }
            )
        }
    }
}

@Composable
fun TemporaryHomeListItem(
    guardian: GuardianTemp,
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
                text = guardian.petNome,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Responsável: ${guardian.nome}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Período: ${guardian.periodo}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Data: ${
                    guardian.dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
                }",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
