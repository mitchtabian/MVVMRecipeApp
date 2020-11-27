package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list


sealed class RecipeListEvent {

    data class NewSearchEvent(
        val query: String = ""
    ): RecipeListEvent()

    class NextPageEvent: RecipeListEvent()
}