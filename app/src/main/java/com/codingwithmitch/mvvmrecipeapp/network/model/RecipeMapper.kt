package com.codingwithmitch.mvvmrecipeapp.network.model

import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.util.EntityMapper


class RecipeMapper : EntityMapper<RecipeEntity, Recipe>{

    override fun mapFromEntity(entity: RecipeEntity): Recipe {
        return Recipe(
                id = entity.pk,
                title = entity.title,
                featuredImage = entity.featuredImage,
                rating = entity.rating,
                publisher = entity.publisher,
                sourceUrl = entity.sourceUrl,
                description = entity.description,
                cookingInstructions = entity.cookingInstructions,
                ingredients = entity.ingredients,
                dateAdded = entity.dateAdded,
                dateUpdated = entity.dateUpdated,
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeEntity {
        return RecipeEntity(
                pk = domainModel.id,
                title = domainModel.title,
                featuredImage = domainModel.featuredImage,
                rating = domainModel.rating,
                publisher = domainModel.publisher,
                sourceUrl = domainModel.sourceUrl,
                description = domainModel.description,
                cookingInstructions = domainModel.cookingInstructions,
                ingredients = domainModel.ingredients,
                dateAdded = domainModel.dateAdded,
                dateUpdated = domainModel.dateUpdated,
        )
    }

    fun fromEntityList(initial: List<RecipeEntity>): List<Recipe>{
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeEntity>{
        return initial.map { mapToEntity(it) }
    }


}


















