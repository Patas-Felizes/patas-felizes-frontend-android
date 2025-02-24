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
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.hosts.HostListViewModel
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempHomeFormScreen(
    navController: NavHostController,
    initialTempHome: TempHome? = null,
    onSave: (TempHome) -> Unit,
    isEditMode: Boolean = false,
    animalViewModel: AnimalListViewModel = viewModel(),
    hostViewModel: HostListViewModel = viewModel()
) {
    var selectedAnimalId by remember { mutableStateOf(initialTempHome?.animal_id) }
    var selectedHostId by remember { mutableStateOf(initialTempHome?.hospedeiro_id) }
    var periodo by remember { mutableStateOf(TextFieldValue(initialTempHome?.periodo ?: "")) }
    var dataHospedagem by remember { mutableStateOf(TextFieldValue(initialTempHome?.data_hospedagem ?: LocalDate.now().toString())) }

    val animals by animalViewModel.animals.collectAsState()
    val hosts by hostViewModel.hosts.collectAsState()

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
                        label = "Animal",
                        selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome ?: "Selecione o animal",
                        options = animals.map { it.nome },
                        onOptionSelected = { selectedOption ->
                            selectedAnimalId = animals.find { it.nome == selectedOption }?.animal_id
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomDropdown(
                        label = "Hospedeiro",
                        selectedOption = hosts.find { it.hospedeiro_id == selectedHostId }?.nome ?: "Selecione o hospedeiro",
                        options = hosts.map { it.nome },
                        onOptionSelected = { selectedOption ->
                            selectedHostId = hosts.find { it.nome == selectedOption }?.hospedeiro_id
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormField(
                        label = "Período",
                        placeholder = "Período da hospedagem",
                        value = periodo,
                        onValueChange = { periodo = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormField(
                        label = "Data de Hospedagem",
                        placeholder = "Data de início da hospedagem",
                        value = dataHospedagem,
                        onValueChange = { dataHospedagem = it }
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
                                if (selectedAnimalId == null || selectedHostId == null ||
                                    periodo.text.isBlank() || dataHospedagem.text.isBlank()) {
                                    return@Button
                                }

                                val tempHome = TempHome(
                                    lar_temporario_id = initialTempHome?.lar_temporario_id ?: 0,
                                    animal_id = selectedAnimalId!!,
                                    hospedeiro_id = selectedHostId!!,
                                    periodo = periodo.text,
                                    data_hospedagem = dataHospedagem.text,
                                    data_cadastro = initialTempHome?.data_cadastro ?: LocalDate.now().toString()
                                )

                                onSave(tempHome)
                            },
                            enabled = selectedAnimalId != null && selectedHostId != null,
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