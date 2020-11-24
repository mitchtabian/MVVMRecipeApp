package com.codingwithmitch.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

class RecipeEntity(

    @SerializedName("recipe_id")
    var recipeId: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("image_url")
    var imageUrl: String? = null,

    @SerializedName("social_rank")
    var socialRank: Float = 0f,

    @SerializedName("publisher")
    var publisher: String? = null,

    @SerializedName("publisher_url")
    var publisherUrl: String? = null,

    @SerializedName("source_url")
    var sourceUrl: String? = null,
)

