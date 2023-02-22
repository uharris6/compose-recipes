package com.uharris.recipes.data.services

import com.uharris.recipes.data.dto.Recipe
import retrofit2.http.GET

interface RecipesService {

    @GET(RECIPE_URL)
    suspend fun getRecipes(): List<Recipe>

    companion object {
        private const val RECIPE_URL = "recipes"
    }
}