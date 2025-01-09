package com.example.patasfelizes.ui.screens.stock

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockFormScreen(
    initialCategory: String = "",
    initialType: String = "",
    initialAnimalSpecies: String = "",
    initialQuantity: String = "",
    onSave: (String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var category by remember { mutableStateOf(initialCategory) }
    var type by remember { mutableStateOf(initialType) }
    var animalSpecies by remember { mutableStateOf(initialAnimalSpecies) }
    var quantity by remember { mutableStateOf(TextFieldValue(initialQuantity)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CustomDropdown(
            label = "Categoria",
            selectedOption = category,
            options = listOf("Alimentação", "Higiene", "Saúde"),
            onOptionSelected = { category = it },
            placeholder = "Selecione a categoria"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdown(
            label = "Tipo de item",
            selectedOption = type,
            options = listOf("Ração", "Medicamento", "Shampoo"),
            onOptionSelected = { type = it },
            placeholder = "Selecione o tipo de item"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdown(
            label = "Espécie de animal",
            selectedOption = animalSpecies,
            options = listOf("Cachorro", "Gato"),
            onOptionSelected = { animalSpecies = it },
            placeholder = "Selecione a espécie de animal"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            label = "Quantidade (em kg)",
            placeholder = "Informe a quantidade",
            value = quantity,
            onValueChange = { quantity = it } // Atualiza o TextFieldValue corretamente
        )

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
                Text("Voltar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onSave(category, type, animalSpecies, quantity.text) }, // Usando quantity.text para extrair a String
                modifier = Modifier.weight(1f)
            ) {
                Text("Salvar")
            }
        }
    }
}
