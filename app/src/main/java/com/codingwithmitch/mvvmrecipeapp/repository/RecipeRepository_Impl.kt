package com.codingwithmitch.mvvmrecipeapp.repository

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.network.RecipeService
import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeMapper

class RecipeRepository_Impl (
    private val recipeService: RecipeService,
    private val mapper: RecipeMapper,
): RecipeRepository {

    override suspend fun search(query: String, page: String): List<Recipe> {
        return mapper.fromEntityList(recipeService.search(query = query, page = page).recipes)
    }

    override suspend fun find(recipeId: String): Recipe {
        return mapper.mapFromEntity(recipeService.find(recipeId).recipe)
    }

}