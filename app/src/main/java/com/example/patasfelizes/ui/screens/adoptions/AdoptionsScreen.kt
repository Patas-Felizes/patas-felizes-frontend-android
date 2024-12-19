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
import com.example.patasfelizes.models.AdopterList
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionsScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Filtros (mantendo estrutura original)
    val filterOptions = remember {
        listOf(
            FilterOption("Para adoção"),
            FilterOption("Adotado"),
            FilterOption("Em lar temporário"),
            FilterOption("Em tratamento")
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
                // Barra de pesquisa
                CustomSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    placeholderText = "Pesquisar...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                // Componente de filtros
                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Listagem de adoções
                AdoptionList(
                    adopters = AdopterList.filter {
                        it.petNome.contains(searchQuery.text, ignoreCase = true) ||
                                it.nome.contains(searchQuery.text, ignoreCase = true)
                    },
                    onAdopterClick = { adopter ->
                        // Ação ao clicar em um item (navegar ou exibir detalhes)
                        navController.navigate("adoptionDetails/${adopter.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun AdoptionList(
    adopters: List<Adopter>,
    onAdopterClick: (Adopter) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

    ) {
        itemsIndexed(adopters) { index, adopter ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.surface // Cor 1
            } else {
                MaterialTheme.colorScheme.background // Cor 2
            }
            AdoptionListItem(
                adopter = adopter,
                backgroundColor = backgroundColor,
                onClick = { onAdopterClick(adopter) }
            )
        }
    }
}

@Composable
fun AdoptionListItem(
    adopter: Adopter,
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
                text = adopter.petNome,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Adotante: ${adopter.nome}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${
                    adopter.dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
                }",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
