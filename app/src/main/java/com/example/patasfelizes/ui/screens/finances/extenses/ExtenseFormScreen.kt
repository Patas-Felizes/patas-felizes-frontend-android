package com.example.patasfelizes.ui.screens.finances.extenses

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
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.DatePickerField
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtenseFormScreen(
    navController: NavHostController,
    initialExtense: Extense? = null,
    onSave: (Extense) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel()
) {
    val tipoOptions = listOf("Veterinário", "Medicamentos", "Cirurgia", "Ração", "Outros")

    var valor by remember { mutableStateOf(TextFieldValue(initialExtense?.valor ?: "")) }
    var tipo by remember { mutableStateOf(initialExtense?.tipo ?: "") }
    var dataDespesa by remember { mutableStateOf(TextFieldValue(initialExtense?.data_despesa ?: "")) }
    var selectedAnimalId by remember { mutableStateOf(initialExtense?.animal_id) }

    val animals by animalViewModel.animals.collectAsState()

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
                    label = "Valor",
                    placeholder = "Informe o valor...",
                    value = valor,
                    onValueChange = { valor = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    trailingIcon = { EditIcon() }
                )

                CustomDropdown(
                    selectedOption = tipo,
                    placeholder = "Selecione o tipo de despesa...",
                    options = tipoOptions,
                    onOptionSelected = { tipo = it },
                    label = "Tipo",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DatePickerField(
                    label = "Data da Despesa",
                    placeholder = "XX-XX-XXXX",
                    value = dataDespesa.text,
                    onDateSelected = { newDate ->
                        dataDespesa = TextFieldValue(newDate)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome ?: "",
                    placeholder = "Selecione o animal (opcional)...",
                    options = animals.map { it.nome },
                    onOptionSelected = { nome ->
                        selectedAnimalId = animals.find { it.nome == nome }?.animal_id
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
                            if (valor.text.isBlank() || tipo.isBlank() || dataDespesa.text.isBlank()) {
                                return@Button
                            }

                            val extense = Extense(
                                despesa_id = initialExtense?.despesa_id ?: 0,
                                valor = valor.text.trim(),
                                tipo = tipo,
                                data_despesa = dataDespesa.text.trim(),
                                animal_id = selectedAnimalId,
                                data_cadastro = LocalDate.now().toString()
                            )

                            onSave(extense)
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