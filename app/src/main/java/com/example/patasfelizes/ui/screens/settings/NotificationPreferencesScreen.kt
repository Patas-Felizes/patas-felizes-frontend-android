// NotificationPreferencesScreen.kt
package com.example.patasfelizes.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NotificationPreferencesScreen() {
    var emailNotifications by remember { mutableStateOf(true) }
    var smsNotifications by remember { mutableStateOf(false) }
    var pushNotifications by remember { mutableStateOf(true) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            NotificationOption(
                title = "Notificações por Email",
                description = "Receber alertas de tarefas por email",
                checked = emailNotifications,
                onCheckedChange = { emailNotifications = it }
            )

            NotificationOption(
                title = "Notificações por SMS",
                description = "Receber alertas de tarefas por SMS",
                checked = smsNotifications,
                onCheckedChange = { smsNotifications = it }
            )

            NotificationOption(
                title = "Notificações Push",
                description = "Receber alertas de tarefas como notificações push",
                checked = pushNotifications,
                onCheckedChange = { pushNotifications = it }
            )
        }
    }
}

@Composable
fun NotificationOption(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}




