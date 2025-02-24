package com.example.patasfelizes.ui.screens.animals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.animals.AnimalDetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.R
import com.example.patasfelizes.ui.viewmodels.animals.AnimalDetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsAnimalScreen(
    navController: NavHostController,
    animalId: Int,
    viewModel: AnimalDetailsViewModel = viewModel()
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var showExpandedImage by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(animalId) {
        viewModel.loadAnimal(animalId)
    }

    when (val state = uiState) {
        is AnimalDetailsState.Loading -> {
            BoxWithProgressBar(isLoading = true) {}
        }
        is AnimalDetailsState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Erro ao carregar detalhes do pet: ${state.message}",
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
        is AnimalDetailsState.Success -> {
            val animal = state.animal

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

                        Spacer(modifier = Modifier.height(16.dp))

                        // Imagem única do animal
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .border(
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                    CircleShape
                                )
                                .clip(CircleShape)
                                .clickable { showExpandedImage = true }
                        ) {
                            AsyncImage(
                                model = animal.foto.ifEmpty { R.drawable.default_image },
                                contentDescription = animal.nome,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        if (showExpandedImage) {
                            Dialog(onDismissRequest = { showExpandedImage = false }) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        AsyncImage(
                                            model = animal.foto.ifEmpty { R.drawable.default_image },
                                            contentDescription = "${animal.nome} - Expandida",
                                            modifier = Modifier.padding(16.dp),
                                            contentScale = ContentScale.Crop
                                        )

                                        IconButton(
                                            onClick = { showExpandedImage = false },
                                            modifier = Modifier.align(Alignment.TopEnd)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Fechar"
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = animal.nome,
                            style = MaterialTheme.typography.titleSmall,
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
                                DetailRow(label = "Descrição", value = animal.descricao)
                                DetailRow(label = "Idade", value = animal.idade)
                                DetailRow(label = "Sexo", value = animal.sexo)
                                DetailRow(label = "Castração", value = animal.castracao)
                                DetailRow(label = "Status", value = animal.status)
                                DetailRow(label = "Espécie", value = animal.especie)
                                DetailRow(label = "Data de Cadastro", value = animal.data_cadastro)
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
                                onClick = { navController.navigate("editAnimal/${animal.animal_id}") },
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
                                text = "Remover pet",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        if (showDeleteConfirmation) {
                            AlertDialog(
                                onDismissRequest = { showDeleteConfirmation = false },
                                title = { Text("Confirmar Exclusão") },
                                text = { Text("Tem certeza que deseja remover ${animal.nome}?") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.deleteAnimal(animal.animal_id) {
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