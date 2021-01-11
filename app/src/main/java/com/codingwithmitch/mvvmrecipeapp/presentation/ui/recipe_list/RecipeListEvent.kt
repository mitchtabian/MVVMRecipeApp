package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {

    class NewSearchEvent : RecipeListEvent()

    class NextPageEvent : RecipeListEvent()
}