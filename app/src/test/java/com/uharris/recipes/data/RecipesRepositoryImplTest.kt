package com.uharris.recipes.data

import com.uharris.recipes.utils.AppResult
import com.uharris.recipes.data.dto.Recipe
import com.uharris.recipes.data.services.RecipesService
import com.uharris.recipes.utils.TestExecutor
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.jvm.Throws

class RecipesRepositoryImplTest {

    @Mock
    private lateinit var recipe: Recipe

    @Mock
    private lateinit var service: RecipesService

    private var testExecutor = TestExecutor()

    private lateinit var recipesRepository: RecipeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        recipesRepository = RecipeRepositoryImpl(service, testExecutor)
    }

    @Test
    fun shouldReturnListOffers() {
        runBlocking {
            val list = listOf(recipe)
            whenever(service.getRecipes()).thenReturn(list)
            val recipes = recipesRepository.getRecipes()
            verify(service).getRecipes()
            assert(recipes is AppResult.Success)
            assert((recipes as AppResult.Success).data.size == list.size)
        }
    }

    @Test
    @Throws(RuntimeException::class)
    fun shouldThrowException() {
        runBlocking {
            whenever(service.getRecipes()).thenThrow(RuntimeException("Exception"))
            val recipes = recipesRepository.getRecipes()
            verify(service).getRecipes()
            assert(recipes is AppResult.Error)
        }
    }
}