package com.example.patasfelizes.ui.screens.adoptions


import android.widget.Toast
import androidx.compose.foundation.background
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
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.viewmodels.adopters.AdopterListViewModel
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel
import java.time.LocalDate
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.platform.LocalContext
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.repository.AdoptersRepository
import com.example.patasfelizes.ui.components.DatePickerField
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionFormScreen(
    navController: NavHostController,
    initialAdoption: Adoption? = null,
    initialCampaing: Campaign? = null,
    initialAnimal: Animal? = null,
    onSave: (Adoption) -> Unit,
    isEditMode: Boolean = false,
    adopterViewModel: AdopterListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    campaignViewModel: CampaignListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    animalViewModel: AnimalListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

) {
    var isLoading by remember { mutableStateOf(false) }
    var selectedAdopterId by remember { mutableStateOf(initialAdoption?.adotante_id) }
    var selectedAnimalId by remember {
        mutableStateOf(initialAnimal?.animal_id ?: initialAdoption?.animal_id)
    }

    var dataAdocao by remember { mutableStateOf(TextFieldValue(initialAdoption?.data_adocao ?: "")) }

    var showAddAdopterDialog by remember { mutableStateOf(false) }
    var newAdopterName by remember { mutableStateOf("") }
    var newAdopterEmail by remember { mutableStateOf("") }
    var newAdopterPhone by remember { mutableStateOf("") }

    val adopters by adopterViewModel.adopters.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val context = LocalContext.current
    val adopterRepository = remember { AdoptersRepository() }

    BoxWithProgressBar(isLoading = isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomDropdown(
                label = "Animal",
                selectedOption = animals.find { it.animal_id == selectedAnimalId }?.nome ?: "Selecione o animal",
                options = animals.map { it.nome },
                onOptionSelected = { selectedOption ->
                    selectedAnimalId = animals.find { it.nome == selectedOption }?.animal_id
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CustomDropdown(
                    label = "Adotante",
                    selectedOption = adopters.find { it.adotante_id == selectedAdopterId }?.nome ?: "Selecione o adotante",
                    options = adopters.map { it.nome },
                    onOptionSelected = { selectedOption ->
                        selectedAdopterId = adopters.find { it.nome == selectedOption }?.adotante_id
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = { showAddAdopterDialog = true },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar adotante",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            DatePickerField(
                label = "Data da Adoção",
                placeholder = "YYYY-MM-DD",
                value = dataAdocao.text,
                onDateSelected = { newDate ->
                    dataAdocao = TextFieldValue(newDate)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigateUp() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isEditMode) "Cancelar" else "Voltar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        val adoption = Adoption(
                            adocao_id = initialAdoption?.adocao_id ?: 0,
                            animal_id = selectedAnimalId ?: 0,
                            adotante_id = selectedAdopterId ?: 0,
                            data_adocao = dataAdocao.text,
                            data_cadastro = LocalDate.now().toString()
                        )
                        onSave(adoption)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading &&
                            selectedAnimalId != null &&
                            selectedAdopterId != null &&
                            dataAdocao.text.isNotBlank()
                ) {
                    Text("Salvar")
                }
            }
        }

        if (showAddAdopterDialog) {
            AlertDialog(
                onDismissRequest = { showAddAdopterDialog = false },
                title = { Text("Adicionar Adotante") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newAdopterName,
                            onValueChange = { newAdopterName = it },
                            label = { Text("Nome") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newAdopterEmail,
                            onValueChange = { newAdopterEmail = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newAdopterPhone,
                            onValueChange = { newAdopterPhone = it },
                            label = { Text("Telefone") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val newAdopter = Adopter(
                                nome = newAdopterName,
                                email = newAdopterEmail,
                                telefone = newAdopterPhone,
                                moradia = "" // You might want to add a moradia field to the dialog
                            )

                            adopterRepository.createAdopter(newAdopter,
                                onSuccess = { createdAdopter ->
                                    // Reload adopters to update the list
                                    adopterViewModel.reloadAdopters()
                                    showAddAdopterDialog = false
                                    newAdopterName = ""
                                    newAdopterEmail = ""
                                    newAdopterPhone = ""
                                },
                                onError = { errorMessage ->
                                    // Handle error (e.g., show a Toast)
                                    Toast.makeText(context, "Erro ao criar adotante: $errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    ) {
                        Text("Adicionar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddAdopterDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}