package com.uharris.recipes.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uharris.recipes.data.dto.Ingredient
import com.uharris.recipes.data.dto.IngredientType
import com.uharris.recipes.data.dto.Recipe
import com.uharris.recipes.domain.GetRecipesUseCase
import com.uharris.recipes.utils.AppResult
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class RecipesViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var recipe: Recipe

    @Mock
    private lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var recipesViewModel: RecipesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        Dispatchers.setMain(mainThreadSurrogate)
        recipesViewModel = RecipesViewModel(getRecipesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun recipesViewModel_GetRecipes_Loading() {
        runBlocking {
            launch(Dispatchers.Main) {
                whenever(getRecipesUseCase.invoke(null)).thenReturn(AppResult.Success(listOf(recipe)))
                recipesViewModel.getRecipes()

                recipesViewModel.recipesViewModelState.observeForever {
                    assert(it is RecipesViewModel.State.Loading)
                }
            }
        }
    }

    @Test
    fun recipesViewModel_GetRecipes_Success() {
        runBlocking {
            launch(Dispatchers.Main) {
                whenever(getRecipesUseCase.invoke(null)).thenReturn(AppResult.Success(listOf(recipe)))
                recipesViewModel.getRecipes()

                recipesViewModel.recipesViewModelState.observeForever {
                    if (it is RecipesViewModel.State.ShowRecipes) {
                        assert(it.recipes.isNotEmpty())
                    }
                }
            }
        }
    }

    @Test
    fun recipesViewModel_GetRecipes_Error() {
        runBlocking {
            launch(Dispatchers.Main) {
                whenever(getRecipesUseCase.invoke(null)).thenReturn(AppResult.Error(Exception("error")))
                recipesViewModel.getRecipes()

                recipesViewModel.recipesViewModelState.observeForever {
                    if (it is RecipesViewModel.State.Error) {
                        assert(it.error == "error")
                    }
                }
            }
        }
    }

    @Test
    fun recipesViewModel_OnSearchTextChanged_ShowRecipesEmpty() {
        runBlocking {
            launch(Dispatchers.Main) {
                recipesViewModel.recipes = listOf(recipe)
                whenever(recipe.name).thenReturn("test")
                whenever(recipe.ingredients).thenReturn(listOf(Ingredient("1", "test", IngredientType.DAIRY)))

                recipesViewModel.onSearchTextChange("search")

                recipesViewModel.searchText.observeForever {
                    assert(it == "search")
                }
                recipesViewModel.recipesViewModelState.observeForever {
                    assert(it is RecipesViewModel.State.ShowRecipes)
                    assert((it as RecipesViewModel.State.ShowRecipes).recipes.isEmpty())
                }
            }
        }
    }

    @Test
    fun recipesViewModel_OnSearchTextChanged_ShowRecipesNotEmpty() {
        runBlocking {
            launch(Dispatchers.Main) {
                recipesViewModel.recipes = listOf(recipe)
                whenever(recipe.name).thenReturn("search")
                whenever(recipe.ingredients).thenReturn(listOf(Ingredient("1", "test", IngredientType.DAIRY)))

                recipesViewModel.onSearchTextChange("search")

                recipesViewModel.searchText.observeForever {
                    assert(it == "search")
                }
                recipesViewModel.recipesViewModelState.observeForever {
                    assert(it is RecipesViewModel.State.ShowRecipes)
                    assert((it as RecipesViewModel.State.ShowRecipes).recipes.isNotEmpty())
                }
            }
        }
    }

    @Test
    fun recipesViewModel_OnSearchTextChanged_ShowRecipes_SearchTextEmpty() {
        runBlocking {
            launch(Dispatchers.Main) {
                recipesViewModel.recipes = listOf(recipe)
                whenever(recipe.name).thenReturn("search")
                whenever(recipe.ingredients).thenReturn(listOf(Ingredient("1", "test", IngredientType.DAIRY)))

                recipesViewModel.onSearchTextChange("")

                recipesViewModel.searchText.observeForever {
                    assert(it == "")
                }
                recipesViewModel.recipesViewModelState.observeForever {
                    assert(it is RecipesViewModel.State.ShowRecipes)
                    assert((it as RecipesViewModel.State.ShowRecipes).recipes.isNotEmpty())
                }
            }
        }
    }
}