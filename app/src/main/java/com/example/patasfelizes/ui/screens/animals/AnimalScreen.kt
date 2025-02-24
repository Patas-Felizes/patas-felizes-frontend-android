package com.example.patasfelizes.ui.screens.animals

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*
import coil.compose.AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import com.example.patasfelizes.R
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalScreen(
    navController: NavHostController,
    viewModel: AnimalListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val animals by viewModel.animals.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadAnimals()
    }

    val filterOptions = remember {
        listOf(
            FilterOption("Para adoção"),
            FilterOption("Adotado"),
            FilterOption("Em lar temporário"),
            FilterOption("Em tratamento")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    val filteredAnimals = animals.filter { animal ->
        val matchesSearch = searchQuery.text.isEmpty() ||
                animal.nome.contains(searchQuery.text, ignoreCase = true) ||
                animal.especie.contains(searchQuery.text, ignoreCase = true)

        val activeFilters = currentFilters.filter { it.isSelected }
        val matchesFilter = activeFilters.isEmpty() || activeFilters.any { it.name == animal.status }

        matchesSearch && matchesFilter
    }.sortedByDescending { animal ->
        try {
            LocalDate.parse(animal.data_cadastro)
        } catch (e: Exception) {
            LocalDate.MIN
        }
    }

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
                    val isVisible = remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        isVisible.value = true
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                            exit = fadeOut()
                        ) {
                            if (filteredAnimals.isEmpty()) {
                                Text(
                                    text = "Nenhum animal encontrado.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    contentPadding = PaddingValues(bottom = 20.dp)
                                ) {
                                    items(filteredAnimals) { animal ->
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .padding(vertical = 6.dp)
                                                    .clickable {
                                                        navController.navigate("animalDetails/${animal.animal_id}")
                                                    }
                                                    .fillMaxWidth(0.9f),
                                                elevation = CardDefaults.cardElevation(3.dp),
                                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    AsyncImage(
                                                        model = if (animal.foto.isNotEmpty()) animal.foto else R.drawable.default_image,
                                                        contentDescription = animal.nome,
                                                        modifier = Modifier
                                                            .size(130.dp)
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    topEnd = 0.dp,
                                                                    bottomEnd = 0.dp
                                                                )
                                                            )
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
                                                            text = animal.nome,
                                                            style = MaterialTheme.typography.titleSmall,
                                                            color = MaterialTheme.colorScheme.onTertiary,
                                                            modifier = Modifier.padding(bottom = 2.dp)
                                                        )

                                                        Text(
                                                            text = animal.especie,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.onTertiary
                                                        )
                                                        Text(
                                                            text = animal.idade,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.onTertiary
                                                        )
                                                        Text(
                                                            text = animal.sexo,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.onTertiary
                                                        )
                                                        Text(
                                                            text = animal.status,
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
        }
    }
}