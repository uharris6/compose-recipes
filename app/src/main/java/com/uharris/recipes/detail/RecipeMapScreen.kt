package com.uharris.recipes.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.uharris.recipes.data.dto.Coordinates
import com.uharris.recipes.recipes.RecipesTopBar

@Composable
fun RecipeMapScreen(
    name: String?,
    coordinates: Coordinates?,
    onBackClick: () -> Unit = {}
) {
    Scaffold(topBar = { RecipesTopBar(name = name, showBackButton = true, onBackClick = onBackClick) }) { contentPadding ->
        val positionRecipe = LatLng(coordinates?.lon ?: 0.0, coordinates?.lat ?: 0.0)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(positionRecipe, 16f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = positionRecipe),
                title = name,
                snippet = "Marker of recipe $name"
            )
        }
    }
}