// RolesPermissionsScreen.kt
package com.example.patasfelizes.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RolesPermissionsScreen() {
    val roles = listOf(
        "Administrador" to "Acesso total ao sistema",
        "Voluntário" to "Acesso básico às tarefas designadas"
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            roles.forEach { (role, description) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)

                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = role, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(text = description, style = MaterialTheme.typography.bodySmall)
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            PermissionCheckbox("Editar")
                            PermissionCheckbox("Excluir")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionCheckbox(label: String) {
    var checked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(text = label)
    }
}