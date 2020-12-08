package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository

class RecipeListViewModel
@ViewModelInject
constructor(
        private val repository: RecipeRepository
): ViewModel(){

    fun getRepo() = repository

}
















