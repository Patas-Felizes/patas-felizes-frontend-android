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
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.navigation.setupNavHost
import com.example.patasfelizes.ui.components.DrawerContent
import com.example.patasfelizes.ui.components.TopBar
import com.example.patasfelizes.ui.theme.PatasFelizesTheme
import com.example.patasfelizes.utils.checkNotificationPermission
import com.example.patasfelizes.utils.requestNotificationPermission
import com.example.patasfelizes.utils.scheduleTaskNotification
import kotlinx.coroutines.launch
import android.content.Context
import java.util.Calendar
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.patasfelizes.utils.scheduleTaskNotification
import com.example.patasfelizes.utils.showInstantNotification
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.repository.AnimalsRepository
import androidx.compose.runtime.mutableStateListOf
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.repository.AdoptionsRepository
import com.example.patasfelizes.repository.CampaignsRepository
import com.example.patasfelizes.repository.DonationRepository
import com.example.patasfelizes.repository.ExtenseRepository
import com.example.patasfelizes.repository.ProcedureRepository
import com.example.patasfelizes.repository.StockRepository
import com.example.patasfelizes.repository.SupportRepository
import com.example.patasfelizes.repository.TaskRepository
import com.example.patasfelizes.repository.TempHomeRepository
import com.example.patasfelizes.repository.VoluntaryRepository


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val adoptionsRepository = AdoptionsRepository()
    private val animalsRepository = AnimalsRepository()
    private val campaignRepository = CampaignsRepository()
    private val extenseRepository = ExtenseRepository()
    private val donationRepository = DonationRepository()
    private val procedureRepository = ProcedureRepository()
    private val stockRepository = StockRepository()
    private val supportRepository = SupportRepository()
    private val taskRepository = TaskRepository()
    private val voluntaryRepository = VoluntaryRepository()
    private val tempHomeRepository = TempHomeRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        println("Join onCreate")
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestNotificationPermission(this)

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            PatasFelizesTheme(isDarkTheme = isDarkTheme) {
                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }

    fun testNotification() {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.SECOND, 30)
        }

        scheduleTaskNotification(
            context = this,
            taskId = 999,
            taskType = "Teste",
            taskDescription = "Notificação de teste - ${calendar.time}",
            taskDate = "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
        )
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


        val onSaveAnimal: (Animal) -> Unit = { animal ->
            animalsRepository.createAnimal(
                animal,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar animal: $error")
                }
            )
        }

        val onSaveAdoption: (Adoption) -> Unit = { adoption ->
            adoptionsRepository.createAdoption(
                adoption,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar adoção: $error")
                }
            )
        }

        val onSaveStock: (Stock) -> Unit = { stock ->
            stockRepository.createStock(
                stock,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar item do estoque: $error")
                }
            )
        }

        val onSaveTask: (Task) -> Unit = { task ->
            taskRepository.createTask(
                task,
                onSuccess = {
                    // Mantendo a funcionalidade de notificação que já existia
                    showInstantNotification(
                        context = this@MainActivity,
                        taskId = task.tarefa_id,
                        taskType = task.tipo,
                        taskDescription = task.descricao
                    )
                    scheduleTaskNotification(
                        context = this@MainActivity,
                        taskId = task.tarefa_id,
                        taskType = task.tipo,
                        taskDescription = task.descricao,
                        taskDate = task.data_tarefa
                    )
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar tarefa: $error")
                }
            )
        }

        val onSaveProcedure: (Procedure) -> Unit = { procedure ->
            procedureRepository.createProcedure(
                procedure,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar procedimento: $error")
                }
            )
        }

        val onSaveCampaign: (Campaign) -> Unit = { campaign ->
            campaignRepository.createCampaign(
                campaign,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar campanha: $error")
                }
            )
        }

        val onSaveExtense: (Extense) -> Unit = { extense ->
            extenseRepository.createExtense(
                extense,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar despesa: $error")
                }
            )
        }

        val onSaveDonation: (Donation) -> Unit = { donation ->
            donationRepository.createDonation(
                donation,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar doação: $error")
                }
            )
        }

        val onSaveSupport: (Support) -> Unit = { support ->
            supportRepository.createSupport(
                support,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar apadrinhamento: $error")
                }
            )
        }

        val onSaveTeam: (Voluntary) -> Unit = { voluntary ->
            voluntaryRepository.createVoluntario(
                voluntary,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar voluntário: $error")
                }
            )
        }

        val onSaveTempHome: (TempHome) -> Unit = { tempHome ->
            tempHomeRepository.createTempHome(
                tempHome,
                onSuccess = {
                    navController.navigateUp()
                },
                onError = { error ->
                    Log.e("MainActivity", "Erro ao salvar lar temporário: $error")
                }
            )
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
                            onSaveAdoption,
                            onSaveStock,
                            onSaveTeam,
                            onSaveExtense,
                            onSaveDonation,
                            onSaveTask,
                            onSaveProcedure,
                            onSaveCampaign,
                            onSaveSupport,
                            onSaveTempHome)
                    }
                }
            }
        }
    }
}
