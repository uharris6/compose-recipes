package com.uharris.recipes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.uharris.recipes.data.dto.Coordinates
import com.uharris.recipes.data.dto.Recipe
import com.uharris.recipes.detail.RecipeDetailScreen
import com.uharris.recipes.detail.RecipeMapScreen
import com.uharris.recipes.recipes.RecipesScreen
import com.uharris.recipes.utils.RecipeType

enum class NavPath(
    val route: String,
) {
    Recipes(route = "recipes"),
    RecipeDetail(route = "recipe_detail"),
    RecipeMap(route = "recipe_map")
}

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = NavPath.Recipes.route) {
        composable(NavPath.Recipes.route) {
            RecipesScreen(
                onRecipeClick = {
                    navHostController.navigate("${NavPath.RecipeDetail.route}/$it")
                }
            )
        }

        composable("${NavPath.RecipeDetail.route}/{recipe}",
            arguments = listOf(navArgument("recipe") { type = RecipeType() })) {
            val recipe = it.arguments?.getParcelable<Recipe>("recipe")
            RecipeDetailScreen(
                recipe,
                onBackClick = {
                    navHostController.navigateUp()
                },
                onMapClick = {
                    navHostController.navigate("${NavPath.RecipeMap.route}/${recipe}")
                }
            )
        }

        composable("${NavPath.RecipeMap.route}/{recipe}",
            arguments = listOf(navArgument("recipe"){ type = RecipeType() })) {
            val recipe = it.arguments?.getParcelable<Recipe>("recipe")
            RecipeMapScreen(
                name = recipe?.name,
                coordinates = recipe?.coordinates,
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}