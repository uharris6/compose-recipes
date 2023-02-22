package com.uharris.recipes.domain

import com.uharris.recipes.utils.AppResult
import com.uharris.recipes.data.RecipeRepository
import com.uharris.recipes.data.dto.Recipe
import com.uharris.recipes.utils.Mockable
import javax.inject.Inject

@Mockable
class GetRecipesUseCase @Inject internal constructor(
    private val recipeRepository: RecipeRepository
): UseCase<AppResult<List<Recipe>>, Nothing?>(){
    override suspend fun run(params: Nothing?) = recipeRepository.getRecipes()
}