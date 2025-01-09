package com.example.patasfelizes.ui.screens.support

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Sponsor
import com.example.patasfelizes.models.SponsorList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportDetailsScreen(
    navController: NavHostController,
    sponsorId: Int,
    onDelete: (Int) -> Unit
) {
    val sponsor = SponsorList.find { it.id == sponsorId } ?: return

    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    sponsor.idAnimal?.let { DetailRow("Pet", it.nome) }
                    DetailRow("Padrinho", sponsor.padrinhoNome)
                    DetailRow("Valor", "R$ ${sponsor.valor}")
                    DetailRow("Data de Cadastro", sponsor.dataCadastro.toString())
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Voltar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { navController.navigate("editSupport/$sponsorId") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
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
                    .padding(horizontal = 16.dp)
            ) {
                Text("Remover apadrinhamento")
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Confirmar Exclusão") },
                    text = { Text("Tem certeza que deseja remover este apadrinhamento permanentemente?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDelete(sponsor.id)
                                navController.navigateUp()
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmation = false }) {
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
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
