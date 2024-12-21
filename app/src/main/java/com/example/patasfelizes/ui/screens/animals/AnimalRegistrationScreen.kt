package com.example.patasfelizes.ui.screens.animals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.R
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.components.ToggleSwitch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalRegistrationScreen(
    navController: NavHostController,
    onSave: (Animal) -> Unit
) {
    var nome by remember { mutableStateOf(TextFieldValue("")) }
    var idade by remember { mutableStateOf(TextFieldValue("")) }
    var unidadeIdade by remember { mutableStateOf("") }
    var sexoIndex by remember { mutableStateOf(0) } // 0: Fêmea, 1: Macho
    var castracaoIndex by remember { mutableStateOf(0) } // 0: Não, 1: Sim
    var status by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var imageRes by remember { mutableStateOf(R.drawable.img1) }

    val statusOptions = listOf("Para adoção", "Em tratamento", "Em lar temporário", "Adotado", "Falecido", "Desaparecido")
    val especieOptions = listOf("Gato", "Cachorro", "Outro")
    val idadeOptions = listOf("Dias", "Meses", "Anos")

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
                    label = "Nome",
                    placeholder = "Informe o nome do pet...",
                    value = nome,
                    onValueChange = { nome = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Idade",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormField(
                        placeholder = "Ex.: 2...",
                        value = idade,
                        onValueChange = { idade = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    CustomDropdown(
                        selectedOption = unidadeIdade,
                        placeholder = "Ex.: Meses...",
                        options = idadeOptions,
                        onOptionSelected = { unidadeIdade = it },
                        label = null,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Sexo",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ToggleSwitch(
                    options = listOf("Fêmea", "Macho"),
                    selectedOptionIndex = sexoIndex,
                    onOptionSelected = { sexoIndex = it },
                    horizontalPadding = 0.dp, // Remove o padding horizontal do ToggleSwitch
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Castração",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ToggleSwitch(
                    options = listOf("Não", "Sim"),
                    selectedOptionIndex = castracaoIndex,
                    onOptionSelected = { castracaoIndex = it },
                    horizontalPadding = 0.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    selectedOption = status,
                    placeholder = "Selecione o status do pet...",
                    options = statusOptions,
                    onOptionSelected = { status = it },
                    label = "Status",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomDropdown(
                    selectedOption = especie,
                    placeholder = "Selecione a espécie do pet...",
                    options = especieOptions,
                    onOptionSelected = { especie = it },
                    label = "Espécie",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Descrição",
                    placeholder = "Descreva a situação em que o pet foi encontrado...",
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.padding(bottom = 20.dp)
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
                            text = "Voltar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (nome.text.isBlank() || especie.isBlank() || status.isBlank()) {
                                return@Button
                            }
                            val animal = Animal(
                                id = 0,
                                nome = nome.text.trim(),
                                descricao = descricao.text.trim(),
                                idade = "${idade.text.trim()} $unidadeIdade",
                                sexo = if (sexoIndex == 0) "Fêmea" else "Macho",
                                castracao = if (castracaoIndex == 1) "Sim" else "Não",
                                status = status,
                                especie = especie,
                                dataCadastro = LocalDate.now(),
                                imageRes = imageRes
                            )
                            onSave(animal)
                            navController.navigateUp()
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
                Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
