package com.codingwithmitch.mvvmrecipeapp.network

import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeEntity
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: String
    ): List<RecipeEntity>

    @GET("get")
    suspend fun find(
        @Query("rId") recipe_id: String
    ): RecipeEntity
}