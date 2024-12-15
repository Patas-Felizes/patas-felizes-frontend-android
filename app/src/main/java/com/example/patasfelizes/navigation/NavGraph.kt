package com.example.patasfelizes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.ui.screens.adoptions.AdoptionsScreen
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.screens.animals.DetailsAnimalScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignsScreen
import com.example.patasfelizes.ui.screens.finances.FinancesScreen
import com.example.patasfelizes.ui.screens.procedures.ProceduresScreen
import com.example.patasfelizes.ui.screens.stock.StockScreen
import com.example.patasfelizes.ui.screens.tasks.TasksScreen
import com.example.patasfelizes.ui.screens.team.TeamScreen
import com.example.patasfelizes.ui.screens.reports.ReportsScreen
import com.example.patasfelizes.ui.screens.support.SupportScreen

import com.example.patasfelizes.ui.screens.temporaryhomes.TemporaryHomesScreen

fun NavGraphBuilder.setupNavHost(navController: NavHostController) {
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
    composable("adocoes") {
        AdoptionsScreen(navController = navController)
    }
    composable("lar_temporario") {
        TemporaryHomesScreen(navController = navController)
    }
    composable("apadrinhamento") {
        SupportScreen(navController = navController)
    }
    composable("procedimentos") {
        ProceduresScreen(navController = navController)
    }
    composable("campanhas") {
        CampaignsScreen(navController = navController)
    }
    composable("financas") {
        FinancesScreen(navController = navController)
    }
    composable("estoque") {
        StockScreen(navController = navController)
    }
    composable("tarefas") {
        TasksScreen(navController = navController)
    }
    composable("equipe") {
        TeamScreen(navController = navController)
    }
    composable("relatorios") {
        ReportsScreen(navController = navController)
    }
}