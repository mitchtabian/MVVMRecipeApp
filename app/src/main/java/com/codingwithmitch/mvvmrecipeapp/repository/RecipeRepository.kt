package com.codingwithmitch.mvvmrecipeapp.repository

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe


interface RecipeRepository {

    suspend fun search(query: String, page: String): List<Recipe>

    suspend fun find(recipeId: String): Recipe

}















