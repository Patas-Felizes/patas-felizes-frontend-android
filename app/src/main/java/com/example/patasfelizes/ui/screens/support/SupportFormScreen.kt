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
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.models.Sponsor
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportFormScreen(
    navController: NavHostController,
    initialSponsor: Sponsor? = null,
    onSave: (Sponsor) -> Unit,
    isEditMode: Boolean = false
) {
    var padrinhoNome by remember { mutableStateOf(TextFieldValue(initialSponsor?.padrinhoNome ?: "")) }
    var valor by remember { mutableStateOf(TextFieldValue(initialSponsor?.valor ?: "")) }
    var selectedPetId by remember { mutableStateOf(initialSponsor?.idAnimal?.id) }

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
                    selectedOption = AnimalList.find { it.id == selectedPetId }?.nome ?: "Selecione o pet",
                    options = AnimalList.map { it.nome },
                    onOptionSelected = { selectedOption ->
                        selectedPetId = AnimalList.find { it.nome == selectedOption }?.id
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

                            val sponsor = Sponsor(
                                id = initialSponsor?.id ?: 0,
                                idAnimal = AnimalList.find { it.id == selectedPetId },
                                padrinhoNome = padrinhoNome.text,
                                valor = valor.text,
                                regularidade = "Mensalmente",
                                dataCadastro = LocalDate.now()
                            )
                            onSave(sponsor)
                            navController.navigateUp()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}
