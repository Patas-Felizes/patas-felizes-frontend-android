package com.example.patasfelizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.navigation.setupNavHost
import com.example.patasfelizes.ui.components.DrawerContent
import com.example.patasfelizes.ui.components.TopBar
import com.example.patasfelizes.ui.theme.PatasFelizesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            // Variável para controlar o tema atual
            var isDarkTheme by remember { mutableStateOf(false) }

            PatasFelizesTheme(isDarkTheme = isDarkTheme) {
                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        isDarkTheme: Boolean,
        onToggleTheme: () -> Unit
    ) {
        val navController = rememberNavController()
        var title by remember { mutableStateOf("Pets") }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        // Função para salvar animal
        val onSaveAnimal: (Animal) -> Unit = { animal ->
            val newId = if (AnimalList.isNotEmpty()) {
                AnimalList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }

            // Adicionar à lista de animais com novo ID
            AnimalList.add(animal.copy(id = newId))
        }

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
                        },
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme // Passamos a função de alternância do tema
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
                        }
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "pets"
                    ) {
                        setupNavHost(navController, onSaveAnimal)
                    }
                }
            }
        }
    }
}
