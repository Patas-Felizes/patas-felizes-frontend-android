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
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeFormScreen(
    initialPetName: String = "",
    initialGuardianName: String = "",
    initialContactInfo: String = "",
    initialPeriod: String = "",
    initialState: String = "",
    initialCity: String = "",
    initialAddress: String = "",
    initialNeighborhood: String = "",
    initialNumber: String = "",
    initialCep: String = "",
    onSave: (String, String, String, String, String, String, String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    val pets = listOf("Tom", "Nazaré", "Panda") // Exemplos de pets
    var petName by remember { mutableStateOf(initialPetName) }
    var guardianName by remember { mutableStateOf(TextFieldValue(initialGuardianName)) }
    var contactInfo by remember { mutableStateOf(TextFieldValue(initialContactInfo)) }
    var period by remember { mutableStateOf(TextFieldValue(initialPeriod)) }
    var state by remember { mutableStateOf(initialState) }
    var city by remember { mutableStateOf(initialCity) }
    var address by remember { mutableStateOf(TextFieldValue(initialAddress)) }
    var neighborhood by remember { mutableStateOf(TextFieldValue(initialNeighborhood)) }
    var number by remember { mutableStateOf(TextFieldValue(initialNumber)) }
    var cep by remember { mutableStateOf(TextFieldValue(initialCep)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        CustomDropdown(
            label = "Pet",
            selectedOption = petName,
            options = pets,
            onOptionSelected = { petName = it },
            placeholder = "Selecione o pet"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Nome do responsável",
            placeholder = "Insira o nome do responsável",
            value = guardianName,
            onValueChange = { guardianName = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Informações de contato",
            placeholder = "(xx) x xxxx-xxxx",
            value = contactInfo,
            onValueChange = { contactInfo = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Período",
            placeholder = "Ex: 2 meses",
            value = period,
            onValueChange = { period = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdown(
            label = "Estado",
            selectedOption = state,
            options = listOf("CE", "SP", "RJ", "MG"),
            onOptionSelected = { state = it },
            placeholder = "Selecione o estado"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdown(
            label = "Cidade",
            selectedOption = city,
            options = listOf("Quixadá", "Fortaleza", "Sobral", "Juazeiro do Norte"),
            onOptionSelected = { city = it },
            placeholder = "Selecione a cidade"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Endereço",
            placeholder = "Informe o endereço",
            value = address,
            onValueChange = { address = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Bairro",
            placeholder = "Informe o bairro",
            value = neighborhood,
            onValueChange = { neighborhood = it }
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("Cancelar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onSave(
                        petName,
                        guardianName.text,
                        contactInfo.text,
                        period.text,
                        state,
                        city,
                        address.text,
                        neighborhood.text,
                        number.text,
                        cep.text
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Salvar")
            }
        }
    }
}
