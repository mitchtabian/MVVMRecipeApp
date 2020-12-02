package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.GenericDialogInfo

data class RecipeListViewState (
        val recipes: List<Recipe> = emptyList(),
        val loading: Boolean = false,
        val page: Int = 1,
        val query: String = "",
        val genericDialogInfo: GenericDialogInfo? = null,
        val selectedCategory: FoodCategory? = null,
        val categoryScrollPosition: Float = 0f,
        val hasExecutedSearch: Boolean = false,
)



