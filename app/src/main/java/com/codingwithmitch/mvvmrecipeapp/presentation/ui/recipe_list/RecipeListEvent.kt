package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()
}