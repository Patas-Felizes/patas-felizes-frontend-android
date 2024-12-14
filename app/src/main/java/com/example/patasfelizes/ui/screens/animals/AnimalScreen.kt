package com.example.patasfelizes.ui.screens.animals

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val filterOptions = remember {
        listOf(
            FilterOption("Todos", true),
            FilterOption("Disponível"),
            FilterOption("Adotado"),
            FilterOption("Em Tratamento")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    // Lógica de filtros
    val filteredAnimals = AnimalList.filter { animal ->
        (animal.nome.contains(searchQuery.text, ignoreCase = true) ||
                animal.especie.contains(searchQuery.text, ignoreCase = true)) &&
                (currentFilters.first { it.name == "Todos" }.isSelected ||
                        currentFilters.any { it.isSelected && it.name == animal.status })
    }

    FilterComponent(
        filterOptions = filterOptions,
        onFilterChanged = { updatedFilters ->
            currentFilters = updatedFilters
        },
        drawerState = drawerState
    ) {
        Scaffold(
            floatingActionButton = {
                CustomFloatingActionButton(
                    onClick = { navController.navigate("addAnimal") },
                    contentDescription = "Adicionar Animal"
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
                        placeholderText = "Pesquisar pet...",
                        onClearSearch = { searchQuery = TextFieldValue("") }
                    )
                    
                    FilterHeader(
                        appliedFilters = currentFilters,
                        onFilterIconClick = {
                            // Open the drawer
                            drawerState.apply {
                                //if (isClosed) open() else close()
                            }
                        }
                    )

                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        items(filteredAnimals) { animal ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 6.dp)
                                        .clickable {
                                            navController.navigate("animalDetails/${animal.id}")
                                        }
                                        .width(340.dp),
                                    elevation = CardDefaults.cardElevation(3.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        // Foto do animal
                                        Image(
                                            painter = painterResource(id = animal.imageRes),
                                            contentDescription = animal.nome,
                                            modifier = Modifier
                                                .size(130.dp)
                                                .clip(RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp))
                                                .aspectRatio(1f),
                                            contentScale = ContentScale.Crop
                                        )

                                        Spacer(modifier = Modifier.width(16.dp))
                                        // Informações do animal
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(top = 18.dp),
                                            verticalArrangement = Arrangement.Top
                                        ) {
                                            Text(
                                                text = animal.nome,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.onTertiary,
                                                modifier = Modifier.padding(bottom = 2.dp)
                                            )

                                            Text(
                                                text = "${animal.idade}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onTertiary
                                            )

                                            Text(
                                                text = "${animal.sexo}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onTertiary
                                            )

                                            Text(
                                                text = "${animal.status}",
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
}