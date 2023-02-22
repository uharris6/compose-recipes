package com.uharris.recipes.domain

import com.uharris.recipes.data.RecipeRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetRecipesUseCaseTest {

    @Mock
    private lateinit var recipesRepositoryImpl: RecipeRepositoryImpl

    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        getRecipesUseCase = GetRecipesUseCase(recipesRepositoryImpl)
    }

    @Test
    fun shouldDeleteOffer() {
        runBlocking {
            getRecipesUseCase.invoke(null)
            verify(recipesRepositoryImpl).getRecipes()
        }
    }
}