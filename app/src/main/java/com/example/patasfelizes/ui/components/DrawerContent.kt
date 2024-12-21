package com.example.patasfelizes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.R

@Composable
fun DrawerContent(
    navController: NavHostController,
    onItemSelected: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(320.dp) // largura do drawer aqui
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier.size(25.dp)
                ) {
                    if (isDarkTheme) {
                        Icon(
                            painter = painterResource(R.drawable.ic_light_mode),
                            contentDescription = "Ativar tema claro",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_dark_mode),
                            contentDescription = "Ativar tema escuro",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))


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
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemSelected(label)
                            navController.navigate(route)
                        }
                        .padding(vertical = 9.dp)
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Configurações",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemSelected("Configurações")
                        navController.navigate("configuracoes")
                    }
                    .padding(vertical = 9.dp)
                    .padding(start = 8.dp)
            )

            Text(
                text = "Ajuda",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemSelected("Ajuda")
                        navController.navigate("ajuda")
                    }
                    .padding(vertical = 9.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}
