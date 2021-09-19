package com.codingwithmitch.mvvmrecipeapp.repository

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.network.RecipeService
import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeDtoMapper

class RecipeRepository_Impl (
        private val recipeService: RecipeService,
        private val mapper: RecipeDtoMapper,
): RecipeRepository {

    override suspend fun search(page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(recipeService.search(page = page, query = query).recipes)
    }

    override suspend fun get(id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(id))
    }

}