package com.example.patasfelizes.navigation

import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.ui.screens.adoptions.AdoptionsScreen
import com.example.patasfelizes.ui.screens.animals.AnimalFormScreen
import com.example.patasfelizes.ui.screens.adoptions.DetailsAdoptionsScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionRegistrationScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionEditScreen
import com.example.patasfelizes.ui.screens.animals.AnimalEditScreen
import com.example.patasfelizes.ui.screens.animals.AnimalRegistrationScreen
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.screens.animals.DetailsAnimalScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignFormScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignsScreen
import com.example.patasfelizes.ui.screens.campaigns.DetailsCampaignScreen
import com.example.patasfelizes.ui.screens.finances.donations.DetailsDonationScreen
import com.example.patasfelizes.ui.screens.finances.extenses.DetailsExtenseScreen
import com.example.patasfelizes.ui.screens.finances.donations.DonationFormScreen
import com.example.patasfelizes.ui.screens.finances.extenses.ExtenseFormScreen
import com.example.patasfelizes.ui.screens.finances.FinancesScreen
import com.example.patasfelizes.ui.screens.procedures.DetailsProcedureScreen
import com.example.patasfelizes.ui.screens.procedures.ProcedureFormScreen
import com.example.patasfelizes.ui.screens.procedures.ProceduresScreen
import com.example.patasfelizes.ui.screens.stock.StockScreen
import com.example.patasfelizes.ui.screens.tasks.TasksScreen
import com.example.patasfelizes.ui.screens.team.TeamScreen
import com.example.patasfelizes.ui.screens.reports.ReportsScreen
import com.example.patasfelizes.ui.screens.settings.NotificationPreferencesScreen
import com.example.patasfelizes.ui.screens.settings.RolesPermissionsScreen
import com.example.patasfelizes.ui.screens.settings.SettingsScreen
import com.example.patasfelizes.ui.screens.settings.VolunteerManagementScreen
import com.example.patasfelizes.ui.screens.stock.StockDetailsScreen
import com.example.patasfelizes.ui.screens.stock.StockEditScreen
import com.example.patasfelizes.ui.screens.stock.StockFormScreen
import com.example.patasfelizes.ui.screens.stock.StockRegistrationScreen
import com.example.patasfelizes.ui.screens.support.SupportDetailsScreen
import com.example.patasfelizes.ui.screens.support.SupportEditScreen
import com.example.patasfelizes.ui.screens.support.SupportRegistrationScreen
import com.example.patasfelizes.ui.screens.support.SupportScreen
import com.example.patasfelizes.ui.screens.tasks.DetailsTaskScreen
import com.example.patasfelizes.ui.screens.tasks.TaskFormScreen
import com.example.patasfelizes.ui.screens.team.DetailsTeamScreen
import com.example.patasfelizes.ui.screens.team.TeamFormScreen
import com.example.patasfelizes.ui.screens.temporaryhomes.TempHomeRegistrationScreen
import com.example.patasfelizes.ui.screens.temporaryhomes.TempHomesDetailsScreen
import com.example.patasfelizes.ui.screens.temporaryhomes.TemporaryHomesScreen
import com.example.patasfelizes.ui.screens.temporaryhomes.TempHomeEditScreen

fun NavGraphBuilder.setupNavHost(
    navController: NavHostController,
    onSaveAnimal: (Animal) -> Unit,
    onSaveAdoption: (Adoption) -> Unit,
    onSaveStock: (Stock) -> Unit,
    onSaveVoluntary: (Voluntary) -> Unit,
    onSaveExtense: (Extense) -> Unit,
    onSaveDonation: (Donation) -> Unit,
    onSaveTask: (Task) -> Unit,
    onSaveProcedure: (Procedure) -> Unit,
    onSaveCampaign: (Campaign) -> Unit,
    onSaveSupport: (Support) -> Unit,
    onSaveTemporaryHome: (TempHome) -> Unit

) {
    // ===  ANIMAL  ====================================================================
    composable("pets") {
        AnimalScreen(navController = navController)
    }

    composable(
        route = "animalDetails/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId")
        animalId?.let {
            DetailsAnimalScreen(navController, animalId = it)
        }
    }

    composable("addAnimal") {
        AnimalRegistrationScreen(
            navController = navController
        )
    }

    composable(
        route = "editAnimal/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId") ?: return@composable
        val animal = AnimalList.find { it.id == animalId } ?: return@composable
        AnimalEditScreen(
            navController = navController,
            animalId = animalId,
        )
    }
//
//    // ===  DOAÇÕES  ====================================================================
//
//    composable("adocoes") {
//        AdoptionsScreen(navController = navController)
//    }
//
//    composable(
//        route = "adoptionDetails/{adopterId}",
//        arguments = listOf(navArgument("adopterId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val adopterId = backStackEntry.arguments?.getInt("adopterId")
//        adopterId?.let {
//            DetailsAdoptionsScreen(navController = navController, adopterId = it, animalId = it)
//        }
//    }
//
//    composable("addAdoption") {
//        AdoptionRegistrationScreen(
//            navController = navController,
//            onSave = onSaveAdoption
//        )
//    }
//
//    composable(
//        route = "editAdoption/{adoptionId}",
//        arguments = listOf(navArgument("adoptionId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val adoptionId = backStackEntry.arguments?.getInt("adoptionId") ?: return@composable
//        val adopter = AdopterList.find { it.id == adoptionId } ?: return@composable
//
//        AdoptionEditScreen(
//            navController = navController,
//            adopter = adopter,
//            onSave = { updatedAdopter ->
//                val index = AdopterList.indexOfFirst { it.id == adoptionId }
//                if (index != -1) {
//                    AdopterList[index] = updatedAdopter
//                }
//            }
//        )
//    }
//
//    // ===  LAR TEMPORARIO ====================================================================
//
//    composable("lar_temporario") {
//        TemporaryHomesScreen(navController = navController)
//    }
//    composable(
//        route = "temporaryHomeDetails/{guardianId}",
//        arguments = listOf(navArgument("guardianId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val guardianId = backStackEntry.arguments?.getInt("guardianId")
//        guardianId?.let {
//            TempHomesDetailsScreen(navController = navController, guardianId = it)
//        }
//    }
//    composable("addTemporaryHome") {
//        TempHomeRegistrationScreen(
//            navController = navController,
//            onSave = onSaveTemporaryHome
//        )
//    }
//    composable(
//        route = "editTemporaryHome/{guardianId}",
//        arguments = listOf(navArgument("guardianId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val guardianId = backStackEntry.arguments?.getInt("guardianId") ?: return@composable
//        val guardian = GuardianTempList.find { it.id == guardianId } ?: return@composable
//        TempHomeEditScreen(
//            navController = navController,
//            guardian = guardian,
//            onSave = { updateGuardian ->
//                val index = GuardianTempList.indexOfFirst { it.id == guardianId }
//                if (index != 1) {
//                    GuardianTempList[index] = updateGuardian
//                }
//            }
//        )
//    }
//
//    composable(
//        route = "editSupport/{sponsorId}",
//        arguments = listOf(navArgument("sponsorId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val sponsorId = backStackEntry.arguments?.getInt("sponsorId") ?: return@composable
//        val sponsor = SponsorList.find { it.id == sponsorId } ?: return@composable
//        SupportEditScreen(
//            navController = navController,
//            sponsor = sponsor,
//            onSave = { updatedSponsor ->
//                val index = SponsorList.indexOfFirst { it.id == sponsorId }
//                if (index != -1) SponsorList[index] = updatedSponsor
//            }
//        )
//    }
//
//
//    // ===  APADRINHAMENTO ====================================================================
//
//    composable("apadrinhamento") {
//        SupportScreen(navController = navController)
//    }
//    composable(
//        route = "supportDetails/{sponsorId}",
//        arguments = listOf(navArgument("sponsorId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val sponsorId = backStackEntry.arguments?.getInt("sponsorId") ?: return@composable
//        SupportDetailsScreen(
//            navController = navController,
//            sponsorId = sponsorId,
//            onDelete = { id -> SponsorList.removeIf { it.id == id } }
//        )
//    }
//
//    composable("addSupport") {
//        SupportRegistrationScreen(
//            navController = navController,
//            onSave = { SponsorList.add(it.copy(id = SponsorList.size + 1)) }
//        )
//    }
//
//    composable(
//        route = "editSupport/{sponsorId}",
//        arguments = listOf(navArgument("sponsorId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val sponsorId = backStackEntry.arguments?.getInt("sponsorId") ?: return@composable
//        val sponsor = SponsorList.find { it.id == sponsorId } ?: return@composable
//        SupportEditScreen(
//            navController = navController,
//            sponsor = sponsor,
//            onSave = { updatedSponsor ->
//                val index = SponsorList.indexOfFirst { it.id == sponsorId }
//                if (index != -1) SponsorList[index] = updatedSponsor
//            }
//        )
//    }
//
//
//    // ===  PROCEDIMENTOS ====================================================================
//
//    composable("procedimentos") {
//        ProceduresScreen(navController = navController)
//    }
//
//    composable(
//        route = "procedureDetails/{procedureId}",
//        arguments = listOf(navArgument("procedureId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val procedureId = backStackEntry.arguments?.getInt("procedureId")
//        procedureId?.let {
//            DetailsProcedureScreen(navController, procedureId = it)
//        }
//    }
//
//    composable("addProcedure") {
//        ProcedureFormScreen(
//            navController = navController,
//            onSave = onSaveProcedure,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "editProcedure/{procedureId}",
//        arguments = listOf(navArgument("procedureId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val procedureId = backStackEntry.arguments?.getInt("procedureId") ?: return@composable
//        val procedure = ProcedureList.find { it.id == procedureId } ?: return@composable
//
//        ProcedureFormScreen(
//            navController = navController,
//            initialProcedure = procedure,
//            onSave = { updatedProcedure ->
//                // Atualizar o animal na lista
//                val index = ProcedureList.indexOfFirst { it.id == procedureId }
//                if (index != -1) {
//                    ProcedureList[index] = updatedProcedure
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//
//    // ===  CAMPANHAS ====================================================================
//
//    composable("campanhas") {
//        CampaignsScreen(navController = navController)
//    }
//    composable(
//        route = "campaignDetails/{campaignId}",
//        arguments = listOf(navArgument("campaignId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val campaignId = backStackEntry.arguments?.getInt("campaignId")
//        campaignId?.let {
//            DetailsCampaignScreen(navController, campaignId = it)
//        }
//    }
//
//    composable("addCampaign") {
//        CampaignFormScreen(
//            navController = navController,
//            onSave = onSaveCampaign,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "editCampaign/{campaignId}",
//        arguments = listOf(navArgument("campaignId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val campaignId = backStackEntry.arguments?.getInt("campaignId") ?: return@composable
//        val campaign = CampaignList.find { it.id == campaignId } ?: return@composable
//
//        CampaignFormScreen(
//            navController = navController,
//            initialCampaign = campaign,
//            onSave = { updatedCampaign ->
//                // Atualizar o animal na lista
//                val index = CampaignList.indexOfFirst { it.id == campaignId }
//                if (index != -1) {
//                    CampaignList[index] = updatedCampaign
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//    // ===  DESPESAS ====================================================================
//
//    composable("financas") {
//        FinancesScreen(navController = navController)
//    }
//
//    composable("addExtense") {
//        ExtenseFormScreen(
//            navController = navController,
//            onSave = onSaveExtense,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "extenseDetails/{extenseId}",
//        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val extenseId = backStackEntry.arguments?.getInt("extenseId")
//        extenseId?.let {
//            DetailsExtenseScreen(navController, extenseId = it)
//        }
//    }
//
//    composable(
//        route = "editExtense/{extenseId}",
//        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val extenseId = backStackEntry.arguments?.getInt("extenseId") ?: return@composable
//        val extense = ExtenseList.find { it.id == extenseId } ?: return@composable
//
//        ExtenseFormScreen(
//            navController = navController,
//            initialExtense = extense,
//            onSave = { updatedExtense ->
//                // Atualizar o animal na lista
//                val index = ExtenseList.indexOfFirst { it.id == extenseId }
//                if (index != -1) {
//                    ExtenseList[index] = updatedExtense
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//    // ===  DOAÇAO ====================================================================
//
//    composable("addDonation") {
//        DonationFormScreen(
//            navController = navController,
//            onSave = onSaveDonation,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "donationDetails/{donationId}",
//        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val donationId = backStackEntry.arguments?.getInt("donationId")
//        donationId?.let {
//            DetailsDonationScreen(navController, donationId = it)
//        }
//    }
//
//    composable(
//        route = "editDonation/{donationId}",
//        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val donationId = backStackEntry.arguments?.getInt("donationId") ?: return@composable
//        val donation = DonationList.find { it.id == donationId } ?: return@composable
//
//        DonationFormScreen(
//            navController = navController,
//            initialDonation = donation,
//            onSave = { updatedDonation ->
//                // Atualizar o animal na lista
//                val index = DonationList.indexOfFirst { it.id == donationId }
//                if (index != -1) {
//                    DonationList[index] = updatedDonation
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//    // ===  ESTOQUE ====================================================================
//
//    composable("estoque") {
//        StockScreen(navController = navController)
//    }
//    composable(
//        route = "stockDetails/{stockId}",
//        arguments = listOf(navArgument("stockId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val stockId = backStackEntry.arguments?.getInt("stockId")
//        stockId?.let {
//            StockDetailsScreen(
//                navController = navController,
//                stockId = it,
//                onDelete = { id ->
//                    StockList.removeIf { stock -> stock.id == id }
//                }
//            )
//        }
//    }
//    composable("addStock") {
//        StockRegistrationScreen(
//            navController = navController,
//            onSave = onSaveStock
//        )
//    }
//
//    composable(
//        route = "editStock/{stockId}",
//        arguments = listOf(navArgument("stockId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val stockId = backStackEntry.arguments?.getInt("stockId") ?: return@composable
//        val stock = StockList.find { it.id == stockId } ?: return@composable
//
//        StockEditScreen(
//            navController = navController,
//            stock = stock,
//            onSave = { updateStock ->
//                val index = StockList.indexOfFirst { it.id == stockId }
//                if (index != 1) {
//                    StockList[index] = updateStock
//                }
//            }
//        )
//    }
//
//    // ===  TAREFAS ====================================================================
//
//    composable("tarefas") {
//        TasksScreen(navController = navController)
//    }
//
//    composable("addTask") {
//        TaskFormScreen(
//            navController = navController,
//            onSave = onSaveTask,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "taskDetails/{taskId}",
//        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val taskId = backStackEntry.arguments?.getInt("taskId")
//        taskId?.let {
//            DetailsTaskScreen(navController, taskId = it)
//        }
//    }
//
//    composable(
//        route = "editTask/{taskId}",
//        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
//        val task = TaskList.find { it.id == taskId } ?: return@composable
//
//        TaskFormScreen(
//            navController = navController,
//            initialTask = task,
//            onSave = { updatedTask ->
//                // Atualizar o animal na lista
//                val index = TaskList.indexOfFirst { it.id == taskId }
//                if (index != -1) {
//                    TaskList[index] = updatedTask
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//    // ===  EQUIPE ====================================================================
//
//    composable("equipe") {
//        TeamScreen(navController = navController)
//    }
//
//    composable("addVoluntary") {
//        TeamFormScreen(
//            navController = navController,
//            onSave = onSaveVoluntary,
//            isEditMode = false
//        )
//    }
//
//    composable(
//        route = "voluntaryDetails/{voluntaryId}",
//        arguments = listOf(navArgument("voluntaryId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val voluntaryId = backStackEntry.arguments?.getInt("voluntaryId")
//        voluntaryId?.let {
//            DetailsTeamScreen(navController, voluntaryId = it)
//        }
//    }
//
//    composable(
//        route = "editVoluntary/{voluntaryId}",
//        arguments = listOf(navArgument("voluntaryId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val voluntaryId = backStackEntry.arguments?.getInt("voluntaryId") ?: return@composable
//        val voluntary = VoluntaryList.find { it.id == voluntaryId } ?: return@composable
//
//        TeamFormScreen(
//            navController = navController,
//            initialVoluntary = voluntary,
//            onSave = { updatedVoluntary ->
//                // Atualizar o animal na lista
//                val index = VoluntaryList.indexOfFirst { it.id == voluntaryId }
//                if (index != -1) {
//                    VoluntaryList[index] = updatedVoluntary
//                }
//            },
//            isEditMode = true
//        )
//    }
//
//    // ===  RELATORIOS  ====================================================================
//
//    composable("relatorios") {
//        ReportsScreen(navController = navController)
//    }
//
//
//    // ===  CONFIGURAÇÕES ====================================================================
//
//    composable("configuracoes") {
//        SettingsScreen(navController = navController)
//    }
//    composable("volunteer_management") {
//        VolunteerManagementScreen()
//    }
//    composable("roles_permissions") {
//        RolesPermissionsScreen()
//    }
//    composable("notification_preferences") {
//        NotificationPreferencesScreen()
//    }
}