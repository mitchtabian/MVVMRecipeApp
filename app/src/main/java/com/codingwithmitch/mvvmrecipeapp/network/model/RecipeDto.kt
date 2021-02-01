package com.codingwithmitch.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDto(

        @SerializedName("pk")
        var pk: Int,

        @SerializedName("title")
        var title: String,

        @SerializedName("publisher")
        var publisher: String,

        @SerializedName("featured_image")
        var featuredImage: String,

        @SerializedName("rating")
        var rating: Int = 0,

        @SerializedName("source_url")
        var sourceUrl: String,

        @SerializedName("ingredients")
        var ingredients: List<String> = emptyList(),

        @SerializedName("date_added")
        var dateAdded: String,

        @SerializedName("date_updated")
        var dateUpdated: String,
)