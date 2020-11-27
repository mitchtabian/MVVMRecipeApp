package com.codingwithmitch.mvvmrecipeapp.network.response

import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeEntity
import com.google.gson.annotations.SerializedName

class RecipeGetResponse (

    @SerializedName("recipe")
    var recipe: RecipeEntity
)