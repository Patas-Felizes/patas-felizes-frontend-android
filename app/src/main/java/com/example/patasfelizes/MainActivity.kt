package com.example.patasfelizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.patasfelizes.ui.components.DrawerContent
import com.example.patasfelizes.ui.components.TopBar
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.theme.PatasFelizesTheme
import com.example.patasfelizes.ui.theme.primaryColor
import com.example.patasfelizes.ui.theme.whiteColor
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            PatasFelizesTheme {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var title by remember { mutableStateOf("Pets") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController,
                    onItemSelected = { selectedItem ->
                        title = selectedItem
                        coroutineScope.launch { drawerState.close() }
                    },
                    onCloseDrawer = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                TopBar(
                    title = title,
                    onOpenDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    onProfileClick = {
                        // Ação ao clicar no perfil do usuário
                        // Adicionar como navegar para uma tela de perfil
                    }
                )

                NavHost(navController = navController, startDestination = "pets") {
                    composable("pets") {
                        AnimalScreen(navController = navController)
                    }
                    composable("adocoes") {
                        ScreenTemplate(title = "Tela Adoções")
                    }
                    composable("lar_temporario") {
                        ScreenTemplate(title = "Tela Lar Temporário")
                    }
                    composable("apadrinhamento") {
                        ScreenTemplate(title = "Tela Apadrinhamento")
                    }
                    composable("procedimentos") {
                        ScreenTemplate(title = "Tela Procedimentos")
                    }
                    composable("campanhas") {
                        ScreenTemplate(title = "Tela Campanhas")
                    }
                    composable("financas") {
                        ScreenTemplate(title = "Tela Finanças")
                    }
                    composable("estoque") {
                        ScreenTemplate(title = "Tela Estoque")
                    }
                    composable("tarefas") {
                        ScreenTemplate(title = "Tela Tarefas")
                    }
                    composable("equipe") {
                        ScreenTemplate(title = "Tela Equipe")
                    }
                    composable("relatorios") {
                        ScreenTemplate(title = "Tela Relatórios")
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenTemplate(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = primaryColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


