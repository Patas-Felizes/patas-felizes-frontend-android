package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.GuardianTemp
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeFormScreen(
    navController: NavHostController,
    initialGuardian: GuardianTemp? = null,
    onSave: (GuardianTemp) -> Unit,
    isEditMode: Boolean = false
) {
    val pets = listOf("Tom", "Nazaré", "Panda") // Exemplos de pets
    var petName by remember { mutableStateOf(initialGuardian?.petNome ?: "") }

    var guardianName by remember { mutableStateOf(TextFieldValue(initialGuardian?.nome ?: "")) }
    var contactInfo by remember { mutableStateOf(TextFieldValue(initialGuardian?.telefone ?: "")) }
    var period by remember { mutableStateOf(TextFieldValue(initialGuardian?.periodo ?: "")) }
    var state by remember { mutableStateOf(initialGuardian?.estado ?: "") }
    var city by remember { mutableStateOf(initialGuardian?.cidade ?: "") }
    var address by remember { mutableStateOf(TextFieldValue(initialGuardian?.endereco ?: "")) }
    var neighborhood by remember { mutableStateOf(TextFieldValue(initialGuardian?.bairro ?: "")) }
    var number by remember { mutableStateOf(TextFieldValue(initialGuardian?.numero ?: "")) }
    var cep by remember { mutableStateOf(TextFieldValue(initialGuardian?.cep ?: "")) }

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
                CustomDropdown(
                    label = "Pet",
                    selectedOption = petName,
                    options = pets,
                    onOptionSelected = { petName = it },
                    placeholder = "Selecione o pet",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Nome do responsável",
                    value = guardianName,
                    onValueChange = { guardianName = it },
                    placeholder = "Informe o nome do responsável",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Contato",
                    value = contactInfo,
                    onValueChange = { contactInfo = it },
                    placeholder = "Informe o telefone de contato",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Período",
                    value = period,
                    onValueChange = { period = it },
                    placeholder = "Ex: 2 meses",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    label = "Estado",
                    selectedOption = state,
                    options = listOf("CE", "SP", "RJ", "MG"),
                    onOptionSelected = { state = it },
                    placeholder = "Selecione o estado",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    label = "Cidade",
                    selectedOption = city,
                    options = listOf("Quixadá", "Fortaleza", "Sobral", "Juazeiro do Norte"),
                    onOptionSelected = { city = it },
                    placeholder = "Selecione a cidade",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Endereço",
                    value = address,
                    onValueChange = { address = it },
                    placeholder = "Informe o endereço",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Bairro",
                    value = neighborhood,
                    onValueChange = { neighborhood = it },
                    placeholder = "Informe o bairro",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormField(
                        label = "Número",
                        value = number,
                        onValueChange = { number = it },
                        placeholder = "Número",
                        modifier = Modifier.weight(1f)
                    )
                    FormField(
                        label = "CEP",
                        value = cep,
                        onValueChange = { cep = it },
                        placeholder = "CEP",
                        modifier = Modifier.weight(1f)
                    )
                }

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
                            if (guardianName.text.isBlank() || contactInfo.text.isBlank() || period.text.isBlank()) {
                                return@Button
                            }

                            val newGuardian = GuardianTemp(
                                id = initialGuardian?.id ?: 0,
                                nome = guardianName.text,
                                petNome = petName,
                                telefone = contactInfo.text,
                                periodo = period.text,
                                estado = state,
                                cidade = city,
                                endereco = address.text,
                                bairro = neighborhood.text,
                                numero = number.text,
                                cep = cep.text,
                                dataCadastro = initialGuardian?.dataCadastro ?: LocalDate.now()
                            )

                            onSave(newGuardian)
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
