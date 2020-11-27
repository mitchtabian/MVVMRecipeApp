package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

@ExperimentalCoroutinesApi
class RecipeListViewModel
@ViewModelInject
constructor(
        private val repository: RecipeRepository,
        private @Named("auth_token") val token: String,
): ViewModel(){

    private val _recipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(ArrayList())

    val recipes: StateFlow<List<Recipe>> get() = _recipes

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loading: StateFlow<Boolean> get() = _loading

    // Pagination starts at '1' (-1 = exhausted?)
    private val _page: MutableStateFlow<Int> = MutableStateFlow(1)

    val page: StateFlow<Int> get() = _page

    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    val query: StateFlow<String> get() = _query

    private var hasExecutedSearch = false // has the user done a search?

    private val _selectedCategory: MutableStateFlow<FoodCategory?> = MutableStateFlow(null)

    val selectedCategory: StateFlow<FoodCategory?> get() = _selectedCategory

    var _categoryScrollPosition: Float = 0f

     init {
        if(!hasExecutedSearch){
            onTriggerEvent(NewSearchEvent())
        }
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
                _loading.value = false
            }
        }
    }

    private suspend fun newSearch(){
        _loading.value = true
        hasExecutedSearch = true

        // New search. Reset the state
        resetSearchState()

        // just to show pagination, api is fast
        delay(1000)

        val result = repository.search(token = token, page = _page.value, query = _query.value )
        _recipes.value = result
    }

    private suspend fun nextPage(){
        _loading.value = true
        incrementPage()
        Log.d(TAG, "nextPage: triggered: ${_page.value}")

        // just to show pagination, api is fast
        delay(1000)

        if(_page.value > 1){
            val result = repository.search(token = token, page = _page.value, query = _query.value )
            Log.d(TAG, "search: appending")
            appendRecipes(result)
        }
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState(){
        _recipes.value = listOf()
        _page.value = 1
        if(_selectedCategory.value?.value != _query.value) clearSelectedCategory()

    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = _recipes.value
        val new = listOf(current, recipes).flatten()
        _recipes.value = new
    }

    private fun incrementPage(){
        _page.value += 1
    }

    /**
     * Keep track of what the user has searched
     */
    fun onQueryChanged(query: String){
        _query.value = query
    }

    private fun clearSelectedCategory(){
        _selectedCategory.value = null
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        _selectedCategory.value = newCategory
        onQueryChanged(category)
    }


    fun onChangeCategoryScrollPosition(position: Float){
        _categoryScrollPosition = position
    }


}



























