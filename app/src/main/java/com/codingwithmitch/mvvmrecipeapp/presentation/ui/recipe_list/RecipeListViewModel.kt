package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
): ViewModel(){

    private val _recipes: MutableStateFlow<List<Recipe>> = MutableStateFlow(ArrayList())

    val recipes: StateFlow<List<Recipe>> get() = _recipes

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loading: StateFlow<Boolean> get() = _loading

    private var _page: String = "1" // Pagination starts at '1' (-1 = exhausted?)

    private var _query: String = ""

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is SearchEvent -> {
                        search(event.query)
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
        delay(1000) // for testing
        _query = query
        val result = repository.search(_query, _page)
        _recipes.value = result
    }

    private suspend fun nextPage(){
        _loading.value = true
        incrementPage()
        search(_query)
    }

    private fun incrementPage(){
        _page = (_page.toInt() + 1).toString()
    }

}



























