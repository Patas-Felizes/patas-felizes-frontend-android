package com.example.patasfelizes.ui.screens.team

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.ui.components.BoxWithProgressBar
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.viewmodels.team.TeamFormState
import com.example.patasfelizes.ui.viewmodels.team.TeamFormViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamFormScreen(
    navController: NavHostController,
    initialVoluntary: Voluntary? = null,
    isEditMode: Boolean = false,
    onSave: (Voluntary) -> Unit,
    viewModel: TeamFormViewModel = viewModel()
) {
    var nome by remember { mutableStateOf(TextFieldValue(initialVoluntary?.nome ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(initialVoluntary?.email ?: "")) }
    var telefone by remember { mutableStateOf(TextFieldValue(initialVoluntary?.telefone ?: "")) }
    var fotoUri by remember { mutableStateOf<Uri?>(initialVoluntary?.foto?.let { Uri.parse(it) }) }

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fotoUri = it
        }
    }

    val state by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        when (state) {
            is TeamFormState.Success -> {
                isLoading = false
                navController.navigateUp()
            }
            is TeamFormState.Error -> {
                isLoading = false
            }
            is TeamFormState.Loading -> {
                isLoading = true
            }
            else -> {}
        }

        // Ativar a visibilidade após inicializar
        isVisible.value = true
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

    BoxWithProgressBar(isLoading = isLoading) {
        Scaffold { innerPadding ->
            // Envolvendo o conteúdo com Box e AnimatedVisibility
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                    exit = fadeOut()
                ) {
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
                                text = "Foto do pet",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
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
                                        if (fotoUri != null) {
                                            AsyncImage(
                                                model = fotoUri,
                                                contentDescription = "Foto do voluntário",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )

                                            IconButton(
                                                onClick = { fotoUri = null },
                                                modifier = Modifier.align(Alignment.TopEnd)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Remover foto",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        } else {
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

                            FormField(
                                label = "Nome",
                                placeholder = "Informe o nome do voluntário...",
                                value = nome,
                                onValueChange = { nome = it },
                                modifier = Modifier.padding(bottom = 16.dp),
                                trailingIcon = { EditIcon() }
                            )

                            FormField(
                                label = "E-mail",
                                placeholder = "Informe o e-mail...",
                                value = email,
                                onValueChange = { email = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.padding(bottom = 16.dp),
                                trailingIcon = { EditIcon() }
                            )

                            FormField(
                                label = "Telefone",
                                placeholder = "Ex: (xx) x xxxx-xxxx...",
                                value = telefone,
                                onValueChange = { telefone = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.padding(bottom = 16.dp),
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
                                        if (nome.text.isBlank() || email.text.isBlank() || telefone.text.isBlank()) {
                                            return@Button
                                        }

                                        val voluntary = Voluntary(
                                            voluntario_id = initialVoluntary?.voluntario_id ?: 0,
                                            nome = nome.text.trim(),
                                            email = email.text.trim(),
                                            telefone = telefone.text.trim(),
                                            foto = fotoUri?.toString() ?: ""
                                        )

                                        onSave(voluntary)
                                    },
                                    enabled = !isLoading,
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
                        }
                    }
                }
            }
        }
    }
}