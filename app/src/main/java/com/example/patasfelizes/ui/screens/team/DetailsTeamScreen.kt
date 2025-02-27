package com.example.patasfelizes.ui.screens.team

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.patasfelizes.R
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.viewmodels.team.TeamDetailsState
import com.example.patasfelizes.ui.viewmodels.team.TeamDetailsViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTeamScreen(
    navController: NavHostController,
    voluntarioId: Int,
    viewModel: TeamDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(voluntarioId) {
        viewModel.loadVoluntario(voluntarioId)

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

    when (val state = uiState) {
        is TeamDetailsState.Loading -> {
            BoxWithProgressBar(isLoading = true) {}
        }
        is TeamDetailsState.Error -> {
            Text(text = "Erro ao carregar voluntário: ${state.message}")
        }
        is TeamDetailsState.Success -> {
            val voluntary = state.voluntario
            BoxWithProgressBar(isLoading = state.isDeleting) {
                Scaffold { innerPadding ->
                    // Envolvendo o conteúdo com Box e AnimatedVisibility
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                            exit = fadeOut()
                        ) {
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

                                AsyncImage(
                                    model = voluntary.foto,
                                    contentDescription = voluntary.nome,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .clip(RoundedCornerShape(300.dp)),
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(id = R.drawable.default_image)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = voluntary.nome,
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
                                        DetailRow(label = "Email", value = voluntary.email)
                                        DetailRow(label = "Telefone", value = voluntary.telefone)
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
                                            containerColor = MaterialTheme.colorScheme.secondary,
                                        ),
                                    ) {
                                        Text(
                                            text = "Voltar",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Button(
                                        onClick = { navController.navigate("editVoluntary/${voluntary.voluntario_id}") },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                                    ) {
                                        Text(
                                            text = "Editar",
                                            style = MaterialTheme.typography.labelSmall,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(11.dp))

                                Button(
                                    onClick = { showDeleteConfirmation = true },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(horizontal = 16.dp),
                                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "Remover voluntário",
                                        style = MaterialTheme.typography.labelSmall,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                if (showDeleteConfirmation) {
                                    AlertDialog(
                                        onDismissRequest = { showDeleteConfirmation = false },
                                        title = { Text("Confirmar Exclusão") },
                                        text = {
                                            Text("Tem certeza que deseja remover ${voluntary.nome} permanentemente?")
                                        },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    viewModel.deleteVoluntario(
                                                        voluntary.voluntario_id
                                                    ) {
                                                        navController.navigateUp()
                                                    }
                                                    showDeleteConfirmation = false
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
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
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