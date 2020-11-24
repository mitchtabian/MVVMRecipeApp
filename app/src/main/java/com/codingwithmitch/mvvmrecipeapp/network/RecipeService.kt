package com.codingwithmitch.mvvmrecipeapp.network

import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeEntity
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeService {

    // SEARCH
    @GET("search")
    suspend fun searchRecipe(
        @Query("q") query: String?,
        @Query("page") page: String?
    ): List<RecipeEntity>

//    // GET RECIPE REQUEST
//    @GET("get")
//    suspend fun getRecipe(
//        @Query("rId") recipe_id: String?
//    ): RecipeEntity
}