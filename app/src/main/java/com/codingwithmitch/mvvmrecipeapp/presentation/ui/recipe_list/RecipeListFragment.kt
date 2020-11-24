package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.R
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.codingwithmitch.openchat.common.framework.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.compose_view, container, false
        ).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {

                val progressBarState by viewModel.loading.collectAsState()

                AppTheme(
                    darkTheme = false,
                    progressBarIsDisplayed = progressBarState
                ){

                    val recipes by viewModel.recipes.collectAsState()

                    RecipeList(recipes = recipes)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onTriggerEvent(SearchEvent("chicken"))
    }
}

@Composable
fun RecipeList(
    recipes: List<Recipe>,
){
    Column() {
        for(recipe in recipes){
            recipe.title?.let { title ->
                Text(title)
            }
        }
    }

}




















