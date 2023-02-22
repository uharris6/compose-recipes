package com.uharris.recipes.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.uharris.recipes.R
import com.uharris.recipes.data.dto.Coordinates
import com.uharris.recipes.data.dto.Ingredient
import com.uharris.recipes.data.dto.Recipe

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe?,
    onBackClick: () -> Unit = {},
    onMapClick: () -> Unit = {}
) {

    Scaffold(
        topBar = { RecipesDetailTopBar(onBackClick, onMapClick) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                val name = recipe?.name ?: ""
                Text(text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                GlideImage(
                    model = recipe?.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(text = stringResource(id = R.string.ingredients_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                recipe?.ingredients?.forEach { 
                    IngredientItem(ingredient = it)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
                Text(text = stringResource(id = R.string.steps_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                recipe?.steps?.forEach {
                    StepItem(step = it)
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    )
}

@Composable
fun RecipesDetailTopBar(onBackClick: () -> Unit, onMapClick: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "back")
            }
        },
        title = { },
        actions = {
            IconButton(onClick = { onMapClick() }) {
                Icon(Icons.Filled.Place, contentDescription = "place")
            }
        }
    )
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = ingredient.name,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(2f)
                .padding(vertical = 8.dp))
        Text(text = ingredient.quantity,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun StepItem(step: String) {
    Card(elevation = 4.dp, modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()) {
        Text(text = step, modifier = Modifier.padding(8.dp))
    }
}