package com.example.patasfelizes.ui.screens.campaigns

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
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.ui.components.DatePickerField
import com.example.patasfelizes.ui.components.FormField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignFormScreen(
    navController: NavHostController,
    initialCampaign: Campaign? = null,
    onSave: (Campaign) -> Unit,
    isEditMode: Boolean = false
) {
    var nome by remember { mutableStateOf(TextFieldValue(initialCampaign?.nome ?: "")) }
    var tipo by remember { mutableStateOf(TextFieldValue(initialCampaign?.tipo ?: "")) }
    var descricao by remember { mutableStateOf(TextFieldValue(initialCampaign?.descricao ?: "")) }
    var dataInicio by remember { mutableStateOf(TextFieldValue(initialCampaign?.data_inicio ?: "")) }
    var dataTermino by remember { mutableStateOf(TextFieldValue(initialCampaign?.data_termino ?: "")) }
    var local by remember { mutableStateOf(TextFieldValue(initialCampaign?.local ?: "")) }

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
                    placeholder = "Digite o nome da campanha...",
                    value = nome,
                    onValueChange = { nome = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Tipo",
                    placeholder = "Informe o tipo da campanha...",
                    value = tipo,
                    onValueChange = { tipo = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Descrição",
                    placeholder = "Descreva a campanha...",
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormField(
                    label = "Local",
                    placeholder = "Informe o local da campanha...",
                    value = local,
                    onValueChange = { local = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DatePickerField(
                    label = "Data de Início",
                    placeholder = "YYYY-MM-DD",
                    value = dataInicio.text,
                    onDateSelected = { newDate ->
                        dataInicio = TextFieldValue(newDate)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DatePickerField(
                    label = "Data de Término",
                    placeholder = "YYYY-MM-DD",
                    value = dataTermino.text,
                    onDateSelected = { newDate ->
                        dataTermino = TextFieldValue(newDate)
                    },
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
                            if (nome.text.isBlank() || tipo.text.isBlank() || descricao.text.isBlank() ||
                                dataInicio.text.isBlank() || dataTermino.text.isBlank() || local.text.isBlank()
                            ) {
                                return@Button
                            }

                            val campaign = Campaign(
                                campanha_id = initialCampaign?.campanha_id ?: 0,
                                nome = nome.text.trim(),
                                tipo = tipo.text.trim(),
                                descricao = descricao.text.trim(),
                                data_inicio = dataInicio.text.trim(),
                                data_termino = dataTermino.text.trim(),
                                local = local.text.trim(),
                                data_cadastro = initialCampaign?.data_cadastro ?: LocalDate.now().toString()
                            )

                            onSave(campaign)
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