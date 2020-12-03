package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe


sealed class RecipeEvent {

    class NewSearchEvent: RecipeEvent()

    class NextPageEvent: RecipeEvent()

    data class GetRecipeEvent(
            val recipeId: Int,
    ): RecipeEvent()
}