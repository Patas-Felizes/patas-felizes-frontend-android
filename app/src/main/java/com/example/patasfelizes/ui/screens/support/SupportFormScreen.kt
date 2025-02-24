package com.example.patasfelizes.ui.screens.support

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
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportFormScreen(
    navController: NavHostController,
    initialSupport: Support? = null,
    onSave: (Support) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel()
) {
    var padrinhoNome by remember { mutableStateOf(TextFieldValue(initialSupport?.nome_apadrinhador ?: "")) }
    var valor by remember { mutableStateOf(TextFieldValue(initialSupport?.valor ?: "")) }
    var selectedPetId by remember { mutableStateOf(initialSupport?.animal_id) }

    val animals by animalViewModel.animals.collectAsState()

    BoxWithProgressBar(isLoading = false) {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomDropdown(
                        label = "Pet",
                        selectedOption = animals.find { it.animal_id == selectedPetId }?.nome ?: "Selecione o pet",
                        options = animals.map { it.nome },
                        onOptionSelected = { selectedOption ->
                            selectedPetId = animals.find { it.nome == selectedOption }?.animal_id
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormField(
                        label = "Padrinho",
                        placeholder = "Nome do padrinho",
                        value = padrinhoNome,
                        onValueChange = { padrinhoNome = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormField(
                        label = "Valor",
                        placeholder = "Valor do apadrinhamento",
                        value = valor,
                        onValueChange = { valor = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                if (selectedPetId == null || padrinhoNome.text.isBlank() || valor.text.isBlank()) {
                                    return@Button
                                }

                                val support = Support(
                                    apadrinhamento_id = initialSupport?.apadrinhamento_id ?: 0,
                                    animal_id = selectedPetId!!,
                                    nome_apadrinhador = padrinhoNome.text,
                                    valor = valor.text,
                                    regularidade = "Mensalmente",
                                    data_cadastro = LocalDate.now().toString()
                                )

                                onSave(support)
                            },
                            enabled = selectedPetId != null,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Salvar")
                        }
                    }
                }
            }
        }
    }
}