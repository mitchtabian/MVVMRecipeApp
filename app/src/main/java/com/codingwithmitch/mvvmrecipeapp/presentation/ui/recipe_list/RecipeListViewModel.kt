package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.GenericDialogInfo
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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

    val viewState: MutableState<RecipeListViewState> = mutableStateOf(RecipeListViewState())

     init {
        if(!viewState.value.hasExecutedSearch){
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
                viewState.value = viewState.value.copy(loading = false)
            }
        }
    }

    private suspend fun newSearch(){
        viewState.value = viewState.value.copy(loading = true, hasExecutedSearch = true)

        // New search. Reset the state
        resetSearchState()

        // just to show pagination, api is fast
        delay(1000)

        val result = repository.search(token = token, page = viewState.value.page, query = viewState.value.query )
        viewState.value = viewState.value.copy(recipes = result)
    }

    private suspend fun nextPage(){
        viewState.value = viewState.value.copy(loading = true)
        incrementPage()
        Log.d(TAG, "nextPage: triggered: ${viewState.value.page}")

        // just to show pagination, api is fast
        delay(1000)

        if(viewState.value.page > 1){
            val result = repository.search(token = token, page = viewState.value.page, query = viewState.value.query)
            Log.d(TAG, "search: appending")
            appendRecipes(result)
        }
    }

    fun onChangeGenericDialogInfo(dialogInfo: GenericDialogInfo?){
        viewState.value = viewState.value.copy(genericDialogInfo = dialogInfo)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState(){
        viewState.value = viewState.value.copy(recipes = listOf())
        viewState.value = viewState.value.copy(page = 1)
        if(viewState.value.selectedCategory?.value != viewState.value.query) clearSelectedCategory()

    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = viewState.value.recipes
        val new = listOf(current, recipes).flatten()
        viewState.value = viewState.value.copy(recipes = new)
    }

    private fun incrementPage(){
        viewState.value = viewState.value.copy(page = viewState.value.page + 1)
    }

    /**
     * Keep track of what the user has searched
     */
    fun onQueryChanged(query: String){
        viewState.value = viewState.value.copy(query = query)
    }

    private fun clearSelectedCategory(){
        viewState.value = viewState.value.copy(selectedCategory = null)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        viewState.value = viewState.value.copy(selectedCategory = newCategory)
        onQueryChanged(category)
    }


    fun onChangeCategoryScrollPosition(position: Float){
        viewState.value = viewState.value.copy(categoryScrollPosition = position)
    }


}



























