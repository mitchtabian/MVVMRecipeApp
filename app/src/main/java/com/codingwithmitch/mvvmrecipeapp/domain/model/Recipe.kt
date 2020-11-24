package com.codingwithmitch.mvvmrecipeapp.domain.model

/**
 * See Recipe example: https://recipesapi.herokuapp.com/api/get/?rId=47230
 */
class Recipe (
    var recipeId: String? = null,
    var title: String? = null,
    var imageUrl: String? = null,
    var socialRank: String? = "0",
    var publisher: String? = null,
    var publisherUrl: String? = null,
    var sourceUrl: String? = null,
)
