package com.example.patasfelizes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.models.AdopterList
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.ui.screens.adoptions.AdoptionsScreen
import com.example.patasfelizes.ui.screens.adoptions.DetailsAdoptionsScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionRegistrationScreen
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.screens.animals.DetailsAnimalScreen
import com.example.patasfelizes.ui.screens.animals.AnimalRegistrationScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignsScreen
import com.example.patasfelizes.ui.screens.finances.FinancesScreen
import com.example.patasfelizes.ui.screens.procedures.ProceduresScreen
import com.example.patasfelizes.ui.screens.stock.StockScreen
import com.example.patasfelizes.ui.screens.tasks.TasksScreen
import com.example.patasfelizes.ui.screens.team.TeamScreen
import com.example.patasfelizes.ui.screens.reports.ReportsScreen
import com.example.patasfelizes.ui.screens.support.SupportScreen

import com.example.patasfelizes.ui.screens.temporaryhomes.TemporaryHomesScreen

fun NavGraphBuilder.setupNavHost(navController: NavHostController, onSaveAnimal: (Animal) -> Unit) {

    //Navegação da Tela Pets
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

    composable(
        route = "addAnimal"
    ) {
        AnimalRegistrationScreen(navController = navController, onSave = onSaveAnimal)
    }

    composable("adocoes") {
        AdoptionsScreen(navController = navController)
    }
    composable(
        route = "adoptionDetails/{adopterId}",
        arguments = listOf(navArgument("adopterId") { type = NavType.IntType })
    ) { backStackEntry ->
        val adopterId = backStackEntry.arguments?.getInt("adopterId")
        adopterId?.let {
            DetailsAdoptionsScreen(navController = navController, adopterId = it, animalId = it)
        }
    }
    composable("addAdoption") {
        AdoptionRegistrationScreen(
            navController = navController,
            onSave = { pet, name, contact, state, city, address, neighborhood, number, cep ->
                // Salve os dados do adotante aqui ou execute uma lógica
            }
        )
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