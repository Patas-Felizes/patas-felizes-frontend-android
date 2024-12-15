package com.example.patasfelizes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.screens.animals.DetailsAnimalScreen

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
        //
    }
    composable("lar_temporario") {
        //
    }
    composable("apadrinhamento") {
        //
    }
    composable("procedimentos") {
        //
    }
    composable("campanhas") {
        //
    }
    composable("financas") {
        //
    }
    composable("estoque") {
        //
    }
    composable("tarefas") {
        //
    }
    composable("equipe") {
        //
    }
    composable("relatorios") {
        //
    }
}