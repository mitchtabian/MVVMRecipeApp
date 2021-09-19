package com.codingwithmitch.mvvmrecipeapp.network

import com.codingwithmitch.mvvmrecipeapp.network.model.RecipeDto
import com.codingwithmitch.mvvmrecipeapp.network.response.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RecipeService {

    @GET("search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun get(
        @Query("id") id: Int
    ): RecipeDto
}











