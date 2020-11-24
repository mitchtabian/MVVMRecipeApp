package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list


sealed class RecipeListEvent {

    data class SearchEvent(
        var query: String,
    ): RecipeListEvent()

    class NextPageEvent: RecipeListEvent()
}