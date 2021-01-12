package com.codingwithmitch.mvvmrecipeapp.network.response

import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

        @SerializedName("count")
        var count: Int,

        @SerializedName("results")
        var recipes: List<RecipeDto>,
)