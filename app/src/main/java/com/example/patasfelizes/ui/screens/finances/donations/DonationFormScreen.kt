package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationFormScreen(
    navController: NavHostController,
    initialDonation: Donation? = null,
    onSave: (Donation) -> Unit,
    isEditMode: Boolean = false
) {
    var doador by remember { mutableStateOf(TextFieldValue(initialDonation?.doador ?: "")) }
    var valor by remember { mutableStateOf(TextFieldValue(initialDonation?.valor ?: "")) }
    var dataDoacao by remember { mutableStateOf(TextFieldValue(initialDonation?.dataDoacao ?: "")) }
    var animalSelecionado by remember { mutableStateOf(initialDonation?.idAnimal) }

    @Composable
    fun EditIcon() {
        if (isEditMode) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }

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

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                FormField(
                    label = "Doador",
                    placeholder = "Nome do doador...",
                    value = doador,
                    onValueChange = { doador = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    trailingIcon = { EditIcon() }
                )

                FormField(
                    label = "Valor",
                    placeholder = "Informe o valor...",
                    value = valor,
                    onValueChange = { valor = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    trailingIcon = { EditIcon() }
                )

                FormField(
                    label = "Data da Doação",
                    placeholder = "AAAA-MM-DD",
                    value = dataDoacao,
                    onValueChange = { dataDoacao = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    trailingIcon = { EditIcon() }
                )

                CustomDropdown(
                    selectedOption = animalSelecionado?.nome ?: "",
                    placeholder = "Selecione o animal (opcional)...",
                    options = AnimalList.map { it.nome },
                    onOptionSelected = { nome ->
                        animalSelecionado = AnimalList.find { it.nome == nome }
                    },
                    label = "Animal",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = if (isEditMode) "Cancelar" else "Voltar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (doador.text.isBlank() || valor.text.isBlank() || dataDoacao.text.isBlank()) {
                                return@Button
                            }

                            val donation = if (isEditMode) {
                                initialDonation!!.copy(
                                    doador = doador.text.trim(),
                                    valor = valor.text.trim(),
                                    dataDoacao = dataDoacao.text.trim(),
                                    idAnimal = animalSelecionado
                                )
                            } else {
                                Donation(
                                    id = 0,
                                    doador = doador.text.trim(),
                                    valor = valor.text.trim(),
                                    dataDoacao = dataDoacao.text.trim(),
                                    dataCadastro = LocalDate.now(),
                                    idAnimal = animalSelecionado
                                )
                            }
                            onSave(donation)
                            navController.navigateUp()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Salvar",
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}