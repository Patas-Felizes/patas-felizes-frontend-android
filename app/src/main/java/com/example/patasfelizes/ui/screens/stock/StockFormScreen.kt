package com.example.patasfelizes.ui.screens.stock

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
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockFormScreen(
    navController: NavHostController,
    initialStock: Stock? = null,
    onSave: (Stock) -> Unit,
    isEditMode: Boolean = false
) {
    var category        by remember { mutableStateOf(initialStock?.categoria ?: "") }
    var type            by remember { mutableStateOf(initialStock?.tipo_item ?: "") }
    var description     by remember { mutableStateOf(TextFieldValue(initialStock?.descricao ?: "")) }
    var animalSpecies   by remember { mutableStateOf(initialStock?.especie_animal ?: "") }
    var quantity        by remember { mutableStateOf(TextFieldValue(initialStock?.quantidade ?: "")) }

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    // Lançar efeito para definir a visibilidade como true após a renderização inicial
    LaunchedEffect(Unit) {
        isVisible.value = true
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

            // Aplicando AnimatedVisibility ao conteúdo do formulário
            AnimatedVisibility(
                visible = isVisible.value,
                enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    CustomDropdown(
                        label = "Categoria",
                        selectedOption = category,
                        options = listOf("Alimentação", "Higiene", "Saúde"),
                        onOptionSelected = { category = it },
                        placeholder = "Selecione a categoria",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomDropdown(
                        label = "Tipo de item",
                        selectedOption = type,
                        options = listOf("Ração", "Medicamento", "Shampoo"),
                        onOptionSelected = { type = it },
                        placeholder = "Selecione o tipo de item",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    FormField(
                        label = "Descrição",
                        value = description,
                        onValueChange = { description = it },
                        placeholder = "Informe a descrição",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomDropdown(
                        label = "Espécie de animal",
                        selectedOption = animalSpecies,
                        options = listOf("Cachorro", "Gato"),
                        onOptionSelected = { animalSpecies = it },
                        placeholder = "Selecione a espécie de animal",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    FormField(
                        label = "Quantidade",
                        value = quantity,
                        onValueChange = { quantity = it },
                        placeholder = "Informe a quantidade atual",
                        modifier = Modifier.padding(bottom = 16.dp)
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

                                val newStock = Stock(
                                    estoque_id = initialStock?.estoque_id ?: 0,
                                    categoria = category,
                                    tipo_item = type,
                                    descricao = description.text,
                                    especie_animal = animalSpecies,
                                    quantidade = quantity.text
                                )

                                onSave(newStock)
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
}