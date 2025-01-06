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
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionRegistrationScreen(
    navController: NavHostController,
    onSave: (String, String, String, String, String, String, String, String, String) -> Unit
) {
    val availablePets = AnimalList.map { it.nome }
    var selectedPet by remember { mutableStateOf("") }
    var adopterName by remember { mutableStateOf(TextFieldValue("")) }
    var contactInfo by remember { mutableStateOf(TextFieldValue("")) }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var neighborhood by remember { mutableStateOf(TextFieldValue("")) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    var cep by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown para selecionar o Pet
            CustomDropdown(
                label = "Pet",
                selectedOption = selectedPet,
                options = availablePets,
                onOptionSelected = { selectedPet = it },
                placeholder = "Selecione o pet"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para o nome do adotante
            FormField(
                label = "Nome do adotante",
                placeholder = "Insira o nome do adotante",
                value = adopterName,
                onValueChange = { adopterName = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para informações de contato
            FormField(
                label = "Informações de contato",
                placeholder = "(xx) x xxxx-xxxx",
                value = contactInfo,
                onValueChange = { contactInfo = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Informações de moradia
            Text(
                text = "Informações de moradia",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            CustomDropdown(
                label = "Estado",
                selectedOption = state,
                options = listOf("CE", "SP", "RJ", "MG"), // Exemplo de estados
                onOptionSelected = { state = it },
                placeholder = "Selecione o estado"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDropdown(
                label = "Cidade",
                selectedOption = city,
                options = listOf("Quixadá", "Fortaleza", "Sobral", "Juazeiro do Norte"), // Exemplo de cidades
                onOptionSelected = { city = it },
                placeholder = "Selecione a cidade"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                label = "Endereço",
                placeholder = "Informe o endereço",
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                label = "Bairro",
                placeholder = "Informe o bairro",
                value = neighborhood,
                onValueChange = { neighborhood = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FormField(
                    label = "Número",
                    placeholder = "Número",
                    value = number,
                    onValueChange = { number = it },
                    modifier = Modifier.weight(1f)
                )
                FormField(
                    label = "CEP",
                    placeholder = "CEP",
                    value = cep,
                    onValueChange = { cep = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(text = "Voltar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (selectedPet.isNotEmpty() && adopterName.text.isNotEmpty() && contactInfo.text.isNotEmpty()) {
                            onSave(
                                selectedPet,
                                adopterName.text,
                                contactInfo.text,
                                state,
                                city,
                                address.text,
                                neighborhood.text,
                                number.text,
                                cep.text
                            )
                            navController.navigateUp()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Salvar")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
