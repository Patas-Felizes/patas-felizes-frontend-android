package com.example.patasfelizes.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            )
            SettingsMenuItem(
                title = "Gerenciamento de Voluntários",
                description = "Adicionar, editar e remover voluntários",
                onClick = { navController.navigate("volunteer_management") }
            )

            SettingsMenuItem(
                title = "Papéis e Permissões",
                description = "Configurar níveis de acesso e permissões",
                onClick = { navController.navigate("roles_permissions") }
            )

            SettingsMenuItem(
                title = "Preferências de Notificação",
                description = "Configurar notificações por email, SMS e push",
                onClick = { navController.navigate("notification_preferences") }
            )
        }
    }
}

@Composable
fun SettingsMenuItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}