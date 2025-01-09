// VolunteerManagementScreen.kt
package com.example.patasfelizes.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.patasfelizes.models.Voluntary

@Composable
fun VolunteerManagementScreen() {
    var showAddVoluntaryDialog by remember { mutableStateOf(false) }
    var showRemoveVoluntaryDialog by remember { mutableStateOf<Voluntary?>(null) }
    var selectedVoluntary by remember { mutableStateOf<Voluntary?>(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = { showAddVoluntaryDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Novo Voluntário")
            }

            // Exibir lista de voluntários aqui

            if (showAddVoluntaryDialog) {
                AddVoluntaryDialog(
                    onDismiss = { showAddVoluntaryDialog = false },
                    onConfirm = { nome, email, telefone ->
                        // Implementar lógica para adicionar voluntário
                        showAddVoluntaryDialog = false
                    }
                )
            }

            showRemoveVoluntaryDialog?.let { voluntary ->
                RemoveVoluntaryDialog(
                    voluntary = voluntary,
                    onDismiss = { showRemoveVoluntaryDialog = null },
                    onConfirm = {
                        // Implementar lógica para remover voluntário
                        showRemoveVoluntaryDialog = null
                    }
                )
            }

            selectedVoluntary?.let { voluntary ->
                EditVoluntaryDialog(
                    voluntary = voluntary,
                    onDismiss = { selectedVoluntary = null },
                    onConfirm = { nome, email, telefone ->
                        // Implementar lógica para editar voluntário
                        selectedVoluntary = null
                    }
                )
            }
        }
    }
}

@Composable
fun AddVoluntaryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Novo Voluntário") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(nome, email, telefone) }) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun RemoveVoluntaryDialog(
    voluntary: Voluntary,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Remover Voluntário") },
        text = { Text("Tem certeza que deseja remover ${voluntary.nome}?") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Remover")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EditVoluntaryDialog(
    voluntary: Voluntary,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var nome by remember { mutableStateOf(voluntary.nome) }
    var email by remember { mutableStateOf(voluntary.email) }
    var telefone by remember { mutableStateOf(voluntary.telefone) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Voluntário") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(nome, email, telefone) }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}