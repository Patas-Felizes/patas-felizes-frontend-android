// 1. Primeiro, vamos atualizar ReportsScreen.kt

package com.example.patasfelizes.ui.screens.reports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.viewmodels.reports.ReportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val reportTypes = listOf(
        "Procedimentos",
        "Animais",
        "Doações",
        "Despesas",
        "Campanhas",
        "Adoções"
    )

    var selectedReportType by remember { mutableStateOf(reportTypes[0]) }
    val isGenerating by viewModel.isGenerating.collectAsState()
    val generationMessage by viewModel.generationMessage.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card para seleção de relatório
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Tipo de Relatório",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    // Dropdown para escolher o tipo de relatório
                    CustomDropdown(
                        selectedOption = selectedReportType,
                        options = reportTypes,
                        onOptionSelected = { selectedReportType = it },
                        placeholder = "Selecione o tipo de relatório"
                    )

                    // Opções de data (para implementações futuras)
                    Text(
                        text = "Período de Tempo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DatePickerField(
                            label = "Data Inicial",
                            value = viewModel.startDate.value,
                            onDateSelected = { viewModel.setStartDate(it) },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        DatePickerField(
                            label = "Data Final",
                            value = viewModel.endDate.value,
                            onDateSelected = { viewModel.setEndDate(it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Botão para gerar relatório
            Button(
                onClick = {
                    viewModel.generateReport(context, selectedReportType)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 24.dp),
                enabled = !isGenerating,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (isGenerating) "Gerando..." else "Gerar Relatório",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            // Mensagem de status
            if (generationMessage.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isSuccess.value)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = generationMessage,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (viewModel.isSuccess.value)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}