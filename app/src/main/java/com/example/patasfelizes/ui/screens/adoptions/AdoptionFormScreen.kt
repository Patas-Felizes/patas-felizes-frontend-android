package com.example.patasfelizes.ui.screens.adoptions

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
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionFormScreen(
    navController: NavHostController,
    initialAdopter: Adopter? = null,
    onSave: (Adopter) -> Unit,
    isEditMode: Boolean = false
) {
    val availablePets = AnimalList.map { it.nome }

    var selectedPet by remember { mutableStateOf(initialAdopter?.petNome ?: "") }
    var adopterName by remember { mutableStateOf(TextFieldValue(initialAdopter?.nome ?: "")) }
    var contactInfo by remember { mutableStateOf(TextFieldValue(initialAdopter?.telefone ?: "")) }
    var state by remember { mutableStateOf(initialAdopter?.estado ?: "") }
    var city by remember { mutableStateOf(initialAdopter?.cidade ?: "") }
    var address by remember { mutableStateOf(TextFieldValue(initialAdopter?.endereco ?: "")) }
    var neighborhood by remember { mutableStateOf(TextFieldValue(initialAdopter?.bairro ?: "")) }
    var number by remember { mutableStateOf(TextFieldValue(initialAdopter?.numero ?: "")) }
    var cep by remember { mutableStateOf(TextFieldValue(initialAdopter?.cep ?: "")) }

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
                    selectedOption = selectedPet,
                    options = availablePets,
                    onOptionSelected = { selectedPet = it },
                    placeholder = "Selecione o pet",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Nome do adotante",
                    value = adopterName,
                    onValueChange = { adopterName = it },
                    placeholder = "Insira o nome do adotante",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Informações de contato",
                    value = contactInfo,
                    onValueChange = { contactInfo = it },
                    placeholder = "(xx) x xxxx-xxxx",
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
                            if (adopterName.text.isBlank() || contactInfo.text.isBlank()) {
                                return@Button
                            }

                            val newAdopter = Adopter(
                                id = initialAdopter?.id ?: 0,
                                nome = adopterName.text,
                                petNome = selectedPet,
                                telefone = contactInfo.text,
                                estado = state,
                                cidade = city,
                                endereco = address.text,
                                bairro = neighborhood.text,
                                numero = number.text,
                                cep = cep.text,
                                dataCadastro = initialAdopter?.dataCadastro ?: LocalDate.now()
                            )

                            onSave(newAdopter)
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
