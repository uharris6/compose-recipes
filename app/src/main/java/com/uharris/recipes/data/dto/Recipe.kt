package com.uharris.recipes.data.dto

import android.net.Uri
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.uharris.recipes.utils.Mockable
import kotlinx.parcelize.Parcelize

@Mockable
@Parcelize
data class Recipe(
    val id: String,
    val name: String,
    @SerializedName("imageURL")
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val timers: List<Int>,
    val coordinates: Coordinates
): Parcelable {
    override fun toString(): String {
        return Uri.encode(Gson().toJson(this))
    }
}

@Mockable
@Parcelize
data class Ingredient(
    val quantity: String,
    val name: String,
    val type: IngredientType
): Parcelable

@Mockable
@Parcelize
data class Coordinates(
    val lon: Double,
    val lat: Double
): Parcelable {
    override fun toString(): String {
        return Uri.encode(Gson().toJson(this))
    }
}

@Mockable
enum class IngredientType(val type: String) {
    @SerializedName("Meat")
    MEAT("Meat"),
    @SerializedName("Baking")
    BAKING("Baking"),
    @SerializedName("Condiments")
    CONDIMENTS("Condiments"),
    @SerializedName("Drinks")
    DRINKS("Drinks"),
    @SerializedName("Produce")
    PRODUCE("Produce"),
    @SerializedName("Misc")
    MISC("Misc"),
    @SerializedName("Dairy")
    DAIRY("Dairy"),
}