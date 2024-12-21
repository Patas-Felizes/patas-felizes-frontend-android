package com.example.patasfelizes.ui.screens.animals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import com.example.patasfelizes.models.AnimalList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsAnimalScreen(
    navController: NavHostController,
    animalId: Int
) {
    val animal = AnimalList.find { it.id == animalId }
        ?: return

    var showDeleteConfirmation by remember { mutableStateOf(false) }

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

            Image(
                painter = painterResource(id = animal.imageRes),
                contentDescription = animal.nome,
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(300.dp)),
                contentScale = ContentScale.Crop
            )

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
                    onClick = { navController.navigate("editAnimal/${animal.id}") },
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
                    text = "Remover pet",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirmar Exclusão") },
                    text = {
                        Text("Tem certeza que deseja remover ${animal.nome} permanentemente?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                navController.navigateUp()
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