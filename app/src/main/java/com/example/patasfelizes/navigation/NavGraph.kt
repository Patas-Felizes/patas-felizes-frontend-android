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
import com.example.patasfelizes.ui.viewmodels.temphomes.TempHomeFormViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.screens.animals.AnimalRegistrationScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignEditScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignRegistrationScreen
import com.example.patasfelizes.ui.screens.finances.donations.DonationEditScreen
import com.example.patasfelizes.ui.screens.finances.donations.DonationRegistrationScreen
import com.example.patasfelizes.ui.screens.finances.extenses.ExtenseEditScreen
import com.example.patasfelizes.ui.screens.finances.extenses.ExtenseRegistrationScreen
import com.example.patasfelizes.ui.screens.procedures.ProcedureEditScreen
import com.example.patasfelizes.ui.screens.procedures.ProcedureRegistrationScreen
import com.example.patasfelizes.ui.screens.tasks.TaskEditScreen
import com.example.patasfelizes.ui.screens.tasks.TaskRegistrationScreen
import com.example.patasfelizes.ui.screens.team.TeamEditScreen
import com.example.patasfelizes.ui.screens.team.TeamRegistrationScreen

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
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "editAnimal/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId") ?: return@composable
        AnimalEditScreen(
            navController = navController,
            animalId = animalId
        )
    }

    // ===  DOAÇÕES  ====================================================================

    composable("adocoes") {
        AdoptionsScreen(navController = navController)
    }

    composable(
        route = "adoptionDetails/{adoptionId}",
        arguments = listOf(navArgument("adoptionId") { type = NavType.IntType })
    ) { backStackEntry ->
        val adoptionId = backStackEntry.arguments?.getInt("adoptionId")
        adoptionId?.let {
            DetailsAdoptionsScreen(navController = navController, adoptionId = it)
        }
    }

    composable("addAdoption") {
        AdoptionRegistrationScreen(
            navController = navController
        )
    }

    composable(
        route = "editAdoption/{adoptionId}",
        arguments = listOf(navArgument("adoptionId") { type = NavType.IntType })
    ) { backStackEntry ->
        val adoptionId = backStackEntry.arguments?.getInt("adoptionId") ?: return@composable
        AdoptionEditScreen(
            navController = navController,
            adoptionId = adoptionId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  LAR TEMPORARIO ====================================================================

    composable("lar_temporario") {
        TemporaryHomesScreen(navController = navController)
    }

    composable(
        route = "temporaryHomeDetails/{tempHomeId}",
        arguments = listOf(navArgument("tempHomeId") { type = NavType.IntType })
    ) { backStackEntry ->
        val tempHomeId = backStackEntry.arguments?.getInt("tempHomeId")
        tempHomeId?.let {
            TempHomesDetailsScreen(navController = navController, tempHomeId = it)
        }
    }

    composable("addTemporaryHome") {
        TempHomeRegistrationScreen(
            navController = navController
        )
    }

    composable(
        route = "editTemporaryHome/{tempHomeId}",
        arguments = listOf(navArgument("tempHomeId") { type = NavType.IntType })
    ) { backStackEntry ->
        val tempHomeId = backStackEntry.arguments?.getInt("tempHomeId") ?: return@composable
        TempHomeEditScreen(
            navController = navController,
            tempHomeId = tempHomeId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }


    // ===  APADRINHAMENTO ====================================================================

    composable("apadrinhamento") {
        SupportScreen(navController = navController)
    }

    composable(
        route = "supportDetails/{supportId}",
        arguments = listOf(navArgument("supportId") { type = NavType.IntType })
    ) { backStackEntry ->
        val supportId = backStackEntry.arguments?.getInt("supportId") ?: return@composable
        SupportDetailsScreen(
            navController = navController,
            supportId = supportId
        )
    }

    composable("addSupport") {
        SupportRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "editSupport/{supportId}",
        arguments = listOf(navArgument("supportId") { type = NavType.IntType })
    ) { backStackEntry ->
        val supportId = backStackEntry.arguments?.getInt("supportId") ?: return@composable

        // Use viewModel para buscar o Support pelo ID em vez de tentar encontrar na lista
        SupportEditScreen(
            navController = navController,
            supportId = supportId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }


    // ===  PROCEDIMENTOS ====================================================================

    composable("procedimentos") {
        ProceduresScreen(navController = navController)
    }

    composable(
        route = "procedureDetails/{procedureId}",
        arguments = listOf(navArgument("procedureId") { type = NavType.IntType })
    ) { backStackEntry ->
        val procedureId = backStackEntry.arguments?.getInt("procedureId")
        procedureId?.let {
            DetailsProcedureScreen(navController, procedureId = it)
        }
    }

    composable("addProcedure") {
        ProcedureRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "editProcedure/{procedureId}",
        arguments = listOf(navArgument("procedureId") { type = NavType.IntType })
    ) { backStackEntry ->
        val procedureId = backStackEntry.arguments?.getInt("procedureId") ?: return@composable

        // Usar ProcedureEditScreen em vez de tentar recuperar de uma lista estática
        ProcedureEditScreen(
            navController = navController,
            procedureId = procedureId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }


    // ===  CAMPANHAS ====================================================================

    composable("campanhas") {
        CampaignsScreen(navController = navController)
    }

    composable(
        route = "campaignDetails/{campaignId}",
        arguments = listOf(navArgument("campaignId") { type = NavType.IntType })
    ) { backStackEntry ->
        val campaignId = backStackEntry.arguments?.getInt("campaignId")
        campaignId?.let {
            DetailsCampaignScreen(navController, campaignId = it)
        }
    }

    composable("addCampaign") {
        CampaignRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "editCampaign/{campaignId}",
        arguments = listOf(navArgument("campaignId") { type = NavType.IntType })
    ) { backStackEntry ->
        val campaignId = backStackEntry.arguments?.getInt("campaignId") ?: return@composable

        CampaignEditScreen(
            navController = navController,
            campaignId = campaignId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  DESPESAS ====================================================================

    composable("financas") {
        FinancesScreen(navController = navController)
    }

    composable("addExtense") {
        ExtenseRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "extenseDetails/{extenseId}",
        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
    ) { backStackEntry ->
        val extenseId = backStackEntry.arguments?.getInt("extenseId")
        extenseId?.let {
            DetailsExtenseScreen(navController, extenseId = it)
        }
    }

    composable(
        route = "editExtense/{extenseId}",
        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
    ) { backStackEntry ->
        val extenseId = backStackEntry.arguments?.getInt("extenseId") ?: return@composable

        // Usar ExtenseEditScreen com ID em vez de objeto Extense
        ExtenseEditScreen(
            navController = navController,
            extenseId = extenseId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  DOAÇAO ====================================================================

    composable("addDonation") {
        DonationRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "donationDetails/{donationId}",
        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val donationId = backStackEntry.arguments?.getInt("donationId")
        donationId?.let {
            DetailsDonationScreen(navController, donationId = it)
        }
    }

    composable(
        route = "editDonation/{donationId}",
        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val donationId = backStackEntry.arguments?.getInt("donationId") ?: return@composable

        // Usar DonationEditScreen com ID em vez de objeto Donation
        DonationEditScreen(
            navController = navController,
            donationId = donationId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  ESTOQUE ====================================================================

    composable("estoque") {
        StockScreen(navController = navController)
    }

    composable(
        route = "stockDetails/{stockId}",
        arguments = listOf(navArgument("stockId") { type = NavType.IntType })
    ) { backStackEntry ->
        val stockId = backStackEntry.arguments?.getInt("stockId")
        stockId?.let {
            StockDetailsScreen(
                navController = navController,
                stockId = it
            )
        }
    }

    composable("addStock") {
        StockRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "editStock/{stockId}",
        arguments = listOf(navArgument("stockId") { type = NavType.IntType })
    ) { backStackEntry ->
        val stockId = backStackEntry.arguments?.getInt("stockId") ?: return@composable

        // Criar uma versão do StockEditScreen que aceita um ID em vez de um objeto Stock
        StockEditScreen(
            navController = navController,
            stockId = stockId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  TAREFAS ====================================================================

    composable("tarefas") {
        TasksScreen(navController = navController)
    }

    composable("addTask") {
        TaskRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "taskDetails/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
    ) { backStackEntry ->
        val taskId = backStackEntry.arguments?.getInt("taskId")
        taskId?.let {
            DetailsTaskScreen(navController, taskId = it)
        }
    }

    composable(
        route = "editTask/{taskId}",
        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
    ) { backStackEntry ->
        val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable

        TaskEditScreen(
            navController = navController,
            taskId = taskId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  EQUIPE ====================================================================

    composable("equipe") {
        TeamScreen(navController = navController)
    }

    composable("addVoluntary") {
        TeamRegistrationScreen(
            navController = navController,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    composable(
        route = "voluntaryDetails/{voluntarioId}",
        arguments = listOf(navArgument("voluntarioId") { type = NavType.IntType })
    ) { backStackEntry ->
        val voluntarioId = backStackEntry.arguments?.getInt("voluntarioId")
        voluntarioId?.let {
            DetailsTeamScreen(navController, voluntarioId = it)
        }
    }

    composable(
        route = "editVoluntary/{voluntarioId}",
        arguments = listOf(navArgument("voluntarioId") { type = NavType.IntType })
    ) { backStackEntry ->
        val voluntarioId = backStackEntry.arguments?.getInt("voluntarioId") ?: return@composable

        // Usar TeamEditScreen com ID em vez de objeto Voluntary
        TeamEditScreen(
            navController = navController,
            voluntarioId = voluntarioId,
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }

    // ===  RELATORIOS  ====================================================================

    composable("relatorios") {
        ReportsScreen(navController = navController)
    }


    // ===  CONFIGURAÇÕES ====================================================================

    composable("configuracoes") {
        SettingsScreen(navController = navController)
    }
    composable("volunteer_management") {
        VolunteerManagementScreen()
    }
    composable("roles_permissions") {
        RolesPermissionsScreen()
    }
    composable("notification_preferences") {
        NotificationPreferencesScreen()
    }
}