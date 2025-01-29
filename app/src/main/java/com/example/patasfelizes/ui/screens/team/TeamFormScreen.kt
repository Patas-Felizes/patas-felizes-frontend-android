package com.example.patasfelizes.ui.screens.team

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.patasfelizes.R
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.ui.components.FormField
import com.example.patasfelizes.ui.components.LinearProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamFormScreen(
    navController: NavHostController,
    initialVoluntary: Voluntary? = null,
    onSave: (Voluntary) -> Unit,
    isEditMode: Boolean = false
) {
    var nome by remember { mutableStateOf(TextFieldValue(initialVoluntary?.nome ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(initialVoluntary?.email ?: "")) }
    var telefone by remember { mutableStateOf(TextFieldValue(initialVoluntary?.telefone ?: "")) }
    var imageRes by remember { mutableStateOf(initialVoluntary?.imageRes ?: R.drawable.default_image) }

    var imageUris by remember {
        mutableStateOf<List<Uri>>(
            initialVoluntary?.imageUris?.mapNotNull { uriString ->
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

    var isLoading by remember { mutableStateOf(false) }

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

            LinearProgressBar(isLoading = isLoading)

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Fotos do voluntário",
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
                            // Verifica se os campos estão preenchidos
                            if (nome.text.isBlank() || email.text.isBlank() || telefone.text.isBlank()) {
                                return@Button
                            }

                            isLoading = true



                            // Simular um delay para mostrar o loading
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000) // Simular operação
                                val newVoluntary = Voluntary(
                                    id = initialVoluntary?.id ?: 0,
                                    nome = nome.text.trim(),
                                    email = email.text.trim(),
                                    telefone = telefone.text.trim(),
                                    imageRes = imageRes
                                )

                                onSave(newVoluntary)
                                isLoading = false
                                navController.navigateUp()
                            }
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
