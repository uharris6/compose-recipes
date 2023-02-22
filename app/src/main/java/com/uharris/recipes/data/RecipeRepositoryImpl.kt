package com.uharris.recipes.data

import com.uharris.recipes.utils.AppResult
import com.uharris.recipes.utils.Executor
import com.uharris.recipes.data.services.RecipesService
import com.uharris.recipes.utils.Mockable
import javax.inject.Inject

@Mockable
class RecipeRepositoryImpl @Inject internal constructor(
    private val recipeService: RecipesService,
    private val executor: Executor
) : RecipeRepository {

    override suspend fun getRecipes() = executor.launchInNetworkThread {
        try {
            AppResult.Success(recipeService.getRecipes())
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}