package com.example.patasfelizes.ui.screens.animals

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.compose.AsyncImage
import com.example.patasfelizes.R
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.ui.components.CustomDropdown
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.components.ToggleSwitch
import java.time.LocalDate
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalFormScreen(
    navController: NavHostController,
    initialAnimal: Animal? = null,
    onSave: (Animal) -> Unit,
    isEditMode: Boolean = false
) {
    val statusOptions = listOf("Para adoção", "Em tratamento", "Em lar temporário", "Adotado", "Falecido", "Desaparecido")
    val especieOptions = listOf("Gato", "Cachorro", "Outro")
    val idadeOptions = listOf("Dias", "Meses", "Anos")


    var nome by remember { mutableStateOf(TextFieldValue(initialAnimal?.nome ?: "")) }
    var idade by remember { mutableStateOf(TextFieldValue(initialAnimal?.idade?.split(" ")?.get(0) ?: "")) }
    var unidadeIdade by remember { mutableStateOf(initialAnimal?.idade?.split(" ")?.getOrNull(1) ?: "") }
    var sexoIndex by remember { mutableStateOf(if (initialAnimal?.sexo == "Fêmea") 0 else if (initialAnimal?.sexo == "Macho") 1 else 0) }
    var castracaoIndex by remember { mutableStateOf(if (initialAnimal?.castracao == "Sim") 1 else 0) }
    var status by remember { mutableStateOf(initialAnimal?.status ?: "") }
    var especie by remember { mutableStateOf(initialAnimal?.especie ?: "") }
    var descricao by remember { mutableStateOf(TextFieldValue(initialAnimal?.descricao ?: "")) }
    var imageRes by remember { mutableStateOf(initialAnimal?.imageRes ?: R.drawable.default_image) }

    var imageUris by remember {
        mutableStateOf<List<Uri>>(
            initialAnimal?.imageUris?.mapNotNull { uriString ->
                try {
                    Uri.parse(uriString)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (imageUris.size < 10) {
                imageUris = imageUris + it
            }
        }
    }



    @Composable
    fun EditIcon() {
        if (isEditMode) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.8f)
            )
        }
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

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Fotos do pet",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageUris) { uri ->
                        Box(
                            modifier = Modifier.size(120.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Foto do pet",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.default_image)
                                    )
                                }
                            }

                            // Botão de remover
                            IconButton(
                                onClick = {
                                    imageUris = imageUris.filter { it != uri }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(24.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.remover),
                                    contentDescription = "Remover foto",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(top = 4.dp, end = 8.dp)
                                )
                            }
                        }
                    }

                    // Botão de adicionar nova foto (se houver menos de 10 fotos)
                    if (imageUris.size < 10) {
                        item {
                            Card(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clickable { galleryLauncher.launch("image/*") },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = "Adicionar foto",
                                        modifier = Modifier.size(32.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }


                FormField(
                    label = "Nome",
                    placeholder = "Informe o nome do pet...",
                    value = nome,
                    onValueChange = { nome = it },
                    modifier = Modifier.padding(bottom = 16.dp),
                    trailingIcon = { EditIcon() }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = { EditIcon() }
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
                    horizontalPadding = 0.dp,
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
                    modifier = Modifier.padding(bottom = 20.dp),
                    trailingIcon = { EditIcon() }
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
                            text = if (isEditMode) "Cancelar" else "Voltar",
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

                            val animal = if (isEditMode) {
                                initialAnimal!!.copy(
                                    nome = nome.text.trim(),
                                    descricao = descricao.text.trim(),
                                    idade = "${idade.text.trim()} $unidadeIdade",
                                    sexo = if (sexoIndex == 0) "Fêmea" else "Macho",
                                    castracao = if (castracaoIndex == 1) "Sim" else "Não",
                                    status = status,
                                    especie = especie,
                                    imageUris = imageUris.map { it.toString() }
                                )
                            } else {
                                Animal(
                                    id = 0,
                                    nome = nome.text.trim(),
                                    descricao = descricao.text.trim(),
                                    idade = "${idade.text.trim()} $unidadeIdade",
                                    sexo = if (sexoIndex == 0) "Fêmea" else "Macho",
                                    castracao = if (castracaoIndex == 1) "Sim" else "Não",
                                    status = status,
                                    especie = especie,
                                    dataCadastro = LocalDate.now(),
                                    imageUris = imageUris.map { it.toString() }
                                )
                            }
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
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}