package com.codingwithmitch.mvvmrecipeapp.network.model

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.util.EntityMapper


class RecipeMapper : EntityMapper<RecipeEntity, Recipe>{

    override fun mapFromEntity(entity: RecipeEntity): Recipe {
        return Recipe(
            recipeId = entity.recipeId,
            title = entity.title,
            imageUrl = entity.imageUrl,
            socialRank = entity.socialRank.toString(),
            publisher = entity.publisher,
            publisherUrl = entity.publisherUrl,
            sourceUrl = entity.sourceUrl,
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeEntity {
        return RecipeEntity(
            recipeId = domainModel.recipeId,
            title = domainModel.title,
            imageUrl = domainModel.imageUrl,
            socialRank = domainModel.socialRank?.toFloat()?: 0f,
            publisher = domainModel.publisher,
            publisherUrl = domainModel.publisherUrl,
            sourceUrl = domainModel.sourceUrl,
        )
    }

    fun fromEntityList(initial: List<RecipeEntity>): List<Recipe>{
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeEntity>{
        return initial.map { mapToEntity(it) }
    }
}


















