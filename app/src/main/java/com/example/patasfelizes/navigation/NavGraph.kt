package com.example.patasfelizes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.ui.screens.adoptions.AdoptionsScreen
import com.example.patasfelizes.ui.screens.animals.AnimalEditScreen
import com.example.patasfelizes.ui.screens.animals.AnimalFormScreen
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
import com.example.patasfelizes.ui.screens.team.DetailsTeamScreen
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

    composable("addAnimal") {
        AnimalFormScreen(
            navController = navController,
            onSave = onSaveAnimal,
            isEditMode = false
        )
    }

    composable(
        route = "editAnimal/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId") ?: return@composable
        val animal = AnimalList.find { it.id == animalId } ?: return@composable

        AnimalFormScreen(
            navController = navController,
            initialAnimal = animal,
            onSave = { updatedAnimal ->
                // Atualizar o animal na lista
                val index = AnimalList.indexOfFirst { it.id == animalId }
                if (index != -1) {
                    AnimalList[index] = updatedAnimal
                }
            },
            isEditMode = true
        )
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
    composable(
        route = "voluntaryDetails/{voluntaryId}",
        arguments = listOf(navArgument("voluntaryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val voluntaryId = backStackEntry.arguments?.getInt("voluntaryId")
        voluntaryId?.let {
            DetailsTeamScreen(navController, voluntaryId = it)
        }
    }
    composable("relatorios") {
        ReportsScreen(navController = navController)
    }
}