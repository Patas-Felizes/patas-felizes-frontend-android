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
    var selectedCampaignId by remember { mutableStateOf(initialCampaing?.campanha_id) }
    var selectedAnimalId by remember { mutableStateOf(initialAnimal?.animal_id) }
    var dataAdocao by remember { mutableStateOf(TextFieldValue(initialAdoption?.data_adocao ?: "")) }
    var dataDevolucao by remember { mutableStateOf(TextFieldValue(initialAdoption?.data_devolucao ?: "")) }
    var motivoDevolucao by remember { mutableStateOf(TextFieldValue(initialAdoption?.motivo_devolucao ?: "")) }

    val adopters by adopterViewModel.adopters.collectAsState()
    val campaigns by campaignViewModel.campaigns.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

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

            CustomDropdown(
                label = "Adotante",
                selectedOption = adopters.find { it.adotante_id == selectedAdopterId }?.nome ?: "Selecione o adotante",
                options = adopters.map { it.nome },
                onOptionSelected = { selectedOption ->
                    selectedAdopterId = adopters.find { it.nome == selectedOption }?.adotante_id
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDropdown(
                label = "Campanha",
                selectedOption = campaigns.find { it.campanha_id == selectedCampaignId }?.nome ?: "Selecione a campanha",
                options = campaigns.map { it.nome },
                onOptionSelected = { selectedOption ->
                    selectedCampaignId = campaigns.find { it.nome == selectedOption }?.campanha_id
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                label = "Data da Adoção",
                value = dataAdocao,
                onValueChange = { dataAdocao = it },
                placeholder = "Digite a data da adoção..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                label = "Data da Devolução",
                value = dataDevolucao,
                onValueChange = { dataDevolucao = it },
                placeholder = "Digite a data da devolução (se houver)..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                label = "Motivo da Devolução",
                value = motivoDevolucao,
                onValueChange = { motivoDevolucao = it },
                placeholder = "Digite o motivo da devolução (se houver)..."
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
                            companha_id = selectedCampaignId ?: 0,
                            data_adocao = dataAdocao.text,
                            data_devolucao = dataDevolucao.text,
                            motivo_devolucao = motivoDevolucao.text,
                            data_cadastro = LocalDate.now().toString()
                        )
                        onSave(adoption)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading &&
                            selectedAnimalId != null &&
                            selectedAdopterId != null &&
                            selectedCampaignId != null &&
                            dataAdocao.text.isNotBlank()
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}