package com.uharris.recipes.recipes

import androidx.lifecycle.*
import com.google.android.gms.common.util.VisibleForTesting
import com.uharris.recipes.utils.AppResult
import com.uharris.recipes.data.dto.Recipe
import com.uharris.recipes.domain.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject internal constructor(
    private val getRecipes: GetRecipesUseCase
) : ViewModel() {

    private val _recipesViewModelMutableState = MutableLiveData<State>()
    val recipesViewModelState: LiveData<State> = _recipesViewModelMutableState
    var recipes: List<Recipe>? = null

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    init {
        getRecipes()
    }

    @VisibleForTesting
    fun getRecipes() {
        viewModelScope.launch {
            _recipesViewModelMutableState.value = State.Loading
            if (recipes.isNullOrEmpty()) {
                _recipesViewModelMutableState.value = when (val result = getRecipes(null)) {
                    is AppResult.Success -> {
                        recipes = result.data
                        State.ShowRecipes(result.data)
                    }
                    is AppResult.Error -> State.Error(result.exception.localizedMessage ?: "")
                }
            } else {
                State.ShowRecipes(recipes ?: listOf())
            }
        }
    }

    fun onSearchTextChange(changedSearchText: String) {
        _searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            _recipesViewModelMutableState.value = State.ShowRecipes(recipes ?: listOf())
            return
        }

        _recipesViewModelMutableState.value = State.ShowRecipes(recipes?.filter {
            it.name.contains(changedSearchText, ignoreCase = true) || (it.ingredients.any { ingredient ->
                ingredient.name.contains(changedSearchText, ignoreCase = true)
            })
        } ?: listOf())
    }

    sealed class State {
        object Loading : State()
        class ShowRecipes(val recipes: List<Recipe>) : State()
        class Error(val error: String): State()
    }

    companion object {
        private const val RECIPE_ID_SAVED_STATE_KEY = "plantId"
    }
}