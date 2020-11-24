package com.codingwithmitch.mvvmrecipeapp.network

import com.codingwithmitch.mvvmrecipeapp.network.response.RecipeFindResponse
import com.codingwithmitch.mvvmrecipeapp.network.response.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun find(
        @Query("rId") recipe_id: String
    ): RecipeFindResponse
}