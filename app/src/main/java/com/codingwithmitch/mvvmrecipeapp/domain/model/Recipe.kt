package com.codingwithmitch.mvvmrecipeapp.domain.model

/**
 * See Recipe example: https://food2fork.ca/
 */
data class Recipe (
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int = 0,
    val sourceUrl: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: String,
    val dateUpdated: String,
)