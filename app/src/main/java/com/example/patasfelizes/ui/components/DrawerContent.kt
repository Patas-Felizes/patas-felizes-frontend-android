package com.example.patasfelizes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.theme.primaryColor
import com.example.patasfelizes.ui.theme.whiteColor

@Composable
fun DrawerContent(
    navController: NavHostController,
    onItemSelected: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onCloseDrawer,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Menu",
                    tint = whiteColor
                )
            }
            IconButton(
                onClick = onCloseDrawer,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Drawer",
                    tint = whiteColor
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        listOf(
            "Pets" to "pets",
            "Adoções" to "adocoes",
            "Lar Temporário" to "lar_temporario",
            "Apadrinhamento" to "apadrinhamento",
            "Procedimentos" to "procedimentos",
            "Campanhas" to "campanhas",
            "Finanças" to "financas",
            "Estoque" to "estoque",
            "Tarefas" to "tarefas",
            "Equipe" to "equipe",
            "Relatórios" to "relatorios"
        ).forEach { (label, route) ->
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = whiteColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemSelected(label)
                        navController.navigate(route)
                    }
                    .padding(vertical = 10.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}

