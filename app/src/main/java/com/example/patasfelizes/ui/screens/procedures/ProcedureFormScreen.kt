package com.example.patasfelizes.ui.screens.procedures

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.*
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureFormScreen(
    navController: NavHostController,
    initialProcedure: Procedure? = null,
    onSave: (Procedure) -> Unit,
    isEditMode: Boolean = false
) {
    var tipo by remember { mutableStateOf(TextFieldValue(initialProcedure?.tipo ?: "")) }
    var descricao by remember { mutableStateOf(TextFieldValue(initialProcedure?.descricao ?: "")) }
    var valor by remember { mutableStateOf(TextFieldValue(initialProcedure?.valor ?: "")) }
    var dataProcedimento by remember { mutableStateOf(TextFieldValue(initialProcedure?.dataProcedimento ?: "")) }

    // Dropdowns para Animal e Voluntário
    var selectedAnimalId by remember { mutableStateOf(initialProcedure?.idAnimal?.id) }
    var selectedVoluntaryId by remember { mutableStateOf(initialProcedure?.idVoluntary?.id) }
    var expandedAnimal by remember { mutableStateOf(false) }
    var expandedVoluntary by remember { mutableStateOf(false) }

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
                    label = "Tipo",
                    placeholder = "Informe o tipo do procedimento...",
                    value = tipo,
                    onValueChange = { tipo = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Descrição",
                    placeholder = "Descreva o procedimento...",
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Valor",
                    placeholder = "Informe o valor...",
                    value = valor,
                    onValueChange = { valor = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Data do Procedimento",
                    placeholder = "YYYY-MM-DD",
                    value = dataProcedimento,
                    onValueChange = { dataProcedimento = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    selectedOption = AnimalList.find { it.id == selectedAnimalId }?.nome ?: "Selecione um animal",
                    options = AnimalList.map { it.nome },
                    onOptionSelected = { selectedOption ->
                        selectedAnimalId = AnimalList.find { it.nome == selectedOption }?.id
                    },
                    label = "Animal",
                    placeholder = "Selecione um animal"
                )

                CustomDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    selectedOption = VoluntaryList.find { it.id == selectedVoluntaryId }?.nome ?: "Selecione um voluntário",
                    options = VoluntaryList.map { it.nome },
                    onOptionSelected = { selectedOption ->
                        selectedVoluntaryId = VoluntaryList.find { it.nome == selectedOption }?.id
                    },
                    label = "Voluntário",
                    placeholder = "Selecione um voluntário"
                )



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                            text = if (isEditMode) "Cancelar" else "Voltar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (tipo.text.isBlank() || descricao.text.isBlank() ||
                                valor.text.isBlank() || dataProcedimento.text.isBlank()) {
                                return@Button
                            }

                            val newProcedure = Procedure(
                                id = initialProcedure?.id ?: 0,
                                tipo = tipo.text.trim(),
                                descricao = descricao.text.trim(),
                                valor = valor.text.trim(),
                                dataProcedimento = dataProcedimento.text.trim(),
                                dataCadastro = LocalDate.now(),
                                idAnimal = AnimalList.find { it.id == selectedAnimalId },
                                idVoluntary = VoluntaryList.find { it.id == selectedVoluntaryId },
                                idExtense = null
                            )

                            onSave(newProcedure)
                            navController.navigateUp()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Salvar",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}