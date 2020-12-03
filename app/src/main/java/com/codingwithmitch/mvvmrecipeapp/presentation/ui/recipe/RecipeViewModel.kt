package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.GenericDialogInfo
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent.*
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

@ExperimentalCoroutinesApi
class RecipeViewModel
@ViewModelInject
constructor(
        private val repository: RecipeRepository,
        private @Named("auth_token") val token: String,
): ViewModel(){

    // list of recipes
    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    // The selected recipe
    val selectedRecipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    // Pagination starts at '1' (-1 = exhausted?)
    val page = mutableStateOf(1)

    val query = mutableStateOf("")

    /**
     * Display a dialog for the user to see.
     * If GenericDialogInfo == null, do not a show dialog.
     */
    val genericDialogInfo: MutableState<GenericDialogInfo?> = mutableStateOf(null)

    private var hasExecutedSearch = false // has the user done a search?

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f

     init {
        if(!hasExecutedSearch){
            onTriggerEvent(NewSearchEvent())
        }
    }

    fun onTriggerEvent(event: RecipeEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is GetRecipeEvent -> {
                        getRecipe(event.recipeId)
                    }
                }

            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
                loading.value = false
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        loading.value = true

        // simulate a delay to show loading
        delay(1000)

        val recipe = repository.get(token=token, id=id)
        this.selectedRecipe.value = recipe
    }

    private suspend fun newSearch(){
        loading.value = true
        hasExecutedSearch = true

        // New search. Reset the state
        resetSearchState()

        // just to show pagination, api is fast
        delay(1000)

        val result = repository.search(token = token, page = page.value, query = query.value )
        recipes.value = result
    }

    private suspend fun nextPage(){
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
    }

    fun onChangeGenericDialogInfo(dialogInfo: GenericDialogInfo?){
        genericDialogInfo.value = dialogInfo
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()

    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = this.recipes.value
        val new = listOf(current, recipes).flatten()
        this.recipes.value = new
    }

    private fun incrementPage(){
        page.value += 1
    }

    /**
     * Keep track of what the user has searched
     */
    fun onQueryChanged(query: String){
        this.query.value = query
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }


    fun onChangeCategoryScrollPosition(position: Float){
        categoryScrollPosition = position
    }


}



























