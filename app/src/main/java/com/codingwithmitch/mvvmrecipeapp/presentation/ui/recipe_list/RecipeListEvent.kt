package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list


sealed class RecipeListEvent {

    class SearchEvent: RecipeListEvent()

    class NextPageEvent: RecipeListEvent()
}