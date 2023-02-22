package com.uharris.recipes.data

import com.uharris.recipes.utils.AppResult
import com.uharris.recipes.data.dto.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): AppResult<List<Recipe>>
}