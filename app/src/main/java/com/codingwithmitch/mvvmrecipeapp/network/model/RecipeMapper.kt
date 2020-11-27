package com.codingwithmitch.mvvmrecipeapp.network.model

import android.util.Log
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.util.EntityMapper
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import org.json.JSONObject
import java.lang.Exception


class RecipeMapper : EntityMapper<RecipeEntity, Recipe>{

    override fun mapFromEntity(entity: RecipeEntity): Recipe {
//        Log.d(TAG, "mapFromEntity: ${entity.ingredients}")
        return Recipe(
                id = entity.pk,
                title = entity.title,
                featuredImage = entity.featuredImage,
                rating = entity.rating,
                publisher = entity.publisher,
                sourceUrl = entity.sourceUrl,
                description = entity.description,
                cookingInstructions = entity.cookingInstructions,
                ingredients = mapIngredientFromEntity(entity.ingredients),
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
                ingredients = null, // don't care we do not publish any recipes
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

    /**
     * Example Json data
     * {
     *  'Butter': '1 Cup',
     *  'Carrot': '6',
     *  '250 ml Milk': null
     * }
     *
     * Will map to List<String>:
     * [
     *  'Butter: 1 Cup', 'Carrot: 6', '250 ml Milk',
     * ]
     *
     *
     */
    fun mapIngredientFromEntity(jsonObject: JsonObject?): List<String>{
        val list: ArrayList<String> = ArrayList()
        if(jsonObject != null){
            for(key in jsonObject.keySet()){
                var ingredient = key
                // Most ingredients are not formatted properly from the initial data set
                if(jsonObject[key].toString() != "null"){
                    ingredient = key + ": " +jsonObject[key].toString()
                }
                list.add(ingredient)
            }
        }
        return list
    }

}


















