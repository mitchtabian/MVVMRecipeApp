package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
) : ViewModel() {


    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f

    val loading = mutableStateOf(false)

    // Pagination starts at '1' (-1 = exhausted)
    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0

    init {
        onTriggerEvent(NewSearchEvent())
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(2000)

        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result

        loading.value = false
    }

    private suspend fun nextPage(){
        // prevent duplicate event due to recompose happening to quickly
        if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE) ){
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            // just to show pagination, api is fast
            delay(1000)

            if(page.value > 1){
                val result = repository.search(token = token, page = page.value, query = query.value )
                Log.d(TAG, "search: appending")
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }
}
















