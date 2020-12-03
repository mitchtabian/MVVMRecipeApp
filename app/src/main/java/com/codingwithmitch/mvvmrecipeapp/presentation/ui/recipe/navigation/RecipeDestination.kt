package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.navigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class RecipeDestination: Parcelable {

    @Parcelize
    object RecipeList: RecipeDestination()

    @Parcelize
    data class RecipeDetail(val recipeId: Int) : RecipeDestination()
}