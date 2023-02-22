package com.uharris.recipes.recipes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.uharris.recipes.R
import com.uharris.recipes.data.dto.Recipe

@Composable
fun RecipesScreen(
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    onRecipeClick: (Recipe) -> Unit = {}
) {
    val state = recipesViewModel.recipesViewModelState.observeAsState()
    var showProgressIndicator by remember { mutableStateOf(true) }
    val searchTextState = recipesViewModel.searchText.observeAsState()
    LaunchedEffect(state.value) {
        showProgressIndicator = when (state.value) {
            is RecipesViewModel.State.Loading -> true
            is RecipesViewModel.State.ShowRecipes ->
                false
            is RecipesViewModel.State.Error -> false
            else -> true
        }
    }
    Scaffold(topBar = { RecipesTopBar(title = R.string.recipes_screen_title) }) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = showProgressIndicator) {
                CircularProgressIndicator()
            }
            AnimatedVisibility(visible = !showProgressIndicator) {
                RecipesList(
                    recipes = (state.value as RecipesViewModel.State.ShowRecipes).recipes,
                    searchTextState = searchTextState,
                    onRecipeClick = onRecipeClick,
                    onSearchTextChange = { recipesViewModel.onSearchTextChange(it) }
                )
            }
        }
    }
}

@Composable
fun RecipesTopBar(title: Int? = null, name: String? = null, showBackButton: Boolean = false, onBackClick: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }
            }
        },
        title = { Text(name ?: stringResource(id = title ?: 0)) })
}

@Composable
fun RecipesList(
    recipes: List<Recipe>,
    searchTextState: State<String?>,
    onSearchTextChange: (String) -> Unit,
    onRecipeClick: (Recipe) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchView(searchText = searchTextState.value ?: "", onSearchTextChange = onSearchTextChange)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recipes, key = {
                it.id
            }) {
                RecipeItem(recipe = it, onRecipeClick = onRecipeClick)
            }
        }   
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun RecipeItem(recipe: Recipe, onRecipeClick: (Recipe) -> Unit = {}) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        onClick = { onRecipeClick(recipe) }
    ) {
        Row(modifier = Modifier.height(50.dp)) {
            GlideImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(text = recipe.name, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SearchView(searchText: String, onSearchTextChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = { value ->
            onSearchTextChange(value)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (searchText != "") {
                IconButton(
                    onClick = {
                        onSearchTextChange("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
    )
}
