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
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.models.CampaignList
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.models.DonationList
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.models.ExtenseList
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.models.ProcedureList
import com.example.patasfelizes.models.Sponsor
import com.example.patasfelizes.models.SponsorList
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.models.TaskList
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.models.VoluntaryList
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

        val onSaveVoluntary: (Voluntary) -> Unit = { voluntary ->
            val newId = if (VoluntaryList.isNotEmpty()) {
                VoluntaryList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            VoluntaryList.add(voluntary.copy(id = newId))
        }

        val onSaveExtense: (Extense) -> Unit = { extense ->
            val newId = if (ExtenseList.isNotEmpty()) {
                ExtenseList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            ExtenseList.add(extense.copy(id = newId))
        }

        val onSaveDonation: (Donation) -> Unit = { donation ->
            val newId = if (DonationList.isNotEmpty()) {
                DonationList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            DonationList.add(donation.copy(id = newId))
        }

        val onSaveTask: (Task) -> Unit = { task ->
            val newId = if (TaskList.isNotEmpty()) {
                TaskList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            TaskList.add(task.copy(id = newId))
        }

        val onSaveProcedure: (Procedure) -> Unit = { procedure ->
            val newId = if (ProcedureList.isNotEmpty()) {
                ProcedureList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            ProcedureList.add(procedure.copy(id = newId))
        }
        val onSaveCampaign: (Campaign) -> Unit = { campaign ->
            val newId = if (CampaignList.isNotEmpty()) {
                CampaignList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            CampaignList.add(campaign.copy(id = newId))
        }
        val onSaveSupport: (Sponsor) -> Unit = { sponsor ->
            val newId = if (SponsorList.isNotEmpty()) {
                SponsorList.maxByOrNull { it.id }?.id?.plus(1) ?: 1
            } else {
                1
            }
            SponsorList.add(sponsor.copy(id = newId))
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
                        setupNavHost(navController,
                            onSaveAnimal,
                            onSaveVoluntary,
                            onSaveExtense,
                            onSaveDonation,
                            onSaveTask,
                            onSaveProcedure,
                            onSaveCampaign,
                            onSaveSupport)
                    }
                }
            }
        }
    }
}
