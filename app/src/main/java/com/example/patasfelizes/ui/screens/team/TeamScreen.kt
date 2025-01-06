package com.example.patasfelizes.ui.screens.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.VoluntaryList
import com.example.patasfelizes.ui.components.*

// Tela Equipe
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // editar filtros conforme cada tela
    val filterOptions = remember {
        listOf(
            FilterOption("Para adoção"),
            FilterOption("Adotado"),
            FilterOption("Em lar temporário"),
            FilterOption("Em tratamento")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    val filteredVolunteers = VoluntaryList
        .filter { voluntary ->
            val matchesSearch = searchQuery.text.isEmpty() ||
                    voluntary.nome.contains(searchQuery.text, ignoreCase = true) ||
                    voluntary.email.contains(searchQuery.text, ignoreCase = true)

            val activeFilters = currentFilters.filter { it.isSelected }
            matchesSearch
        }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addTeam") },
                contentDescription = "Adicionar Membro da Equipe"
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

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredVolunteers) { voluntary ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        navController.navigate("voluntaryDetails/${voluntary.id}")
                                    }
                                    .fillMaxWidth(0.9f),
                                elevation = CardDefaults.cardElevation(3.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = voluntary.imageRes),
                                        contentDescription = voluntary.nome,
                                        modifier = Modifier
                                            .size(130.dp)
                                            .clip(RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp))
                                            .aspectRatio(1f),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(top = 9.dp),
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        Text(
                                            text = voluntary.nome,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            modifier = Modifier.padding(bottom = 2.dp)
                                        )

                                        Text(
                                            text = voluntary.email,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        Text(
                                            text = voluntary.telefone,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
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