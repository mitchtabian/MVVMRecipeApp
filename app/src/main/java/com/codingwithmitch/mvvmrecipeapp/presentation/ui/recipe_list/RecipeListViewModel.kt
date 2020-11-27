package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.FoodCategory.*
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Named

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

    private var _page: String = "1" // Pagination starts at '1' (-1 = exhausted?)

    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    val query: StateFlow<String> get() = _query

    private var hasExecutedSearch = false // has the user done a search?

    private val _selectedCategory: MutableStateFlow<FoodCategory?> = MutableStateFlow(null)

    val selectedCategory: StateFlow<FoodCategory?> get() = _selectedCategory

    var _categoryScrollPosition: Float = 0f

    init {
        if(!hasExecutedSearch){
            onTriggerEvent(SearchEvent())
        }
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is SearchEvent -> {
                        search(_query.value)
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
                _loading.value = false
            }
        }
    }

    private suspend fun search(query: String){
        _loading.value = true
        _recipes.value = ArrayList()
        if(_selectedCategory.value?.value != query) clearSelectedCategory()
        delay(1000) // for testing
        hasExecutedSearch = true
        onQueryChanged(query)
        val result = repository.search(token = token, page = _page, query = _query.value )
        _recipes.value = result
    }

    private suspend fun nextPage(){
        _loading.value = true
        incrementPage()
        search(_query.value)
    }

    private fun incrementPage(){
        _page = (_page.toInt() + 1).toString()
    }

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



























