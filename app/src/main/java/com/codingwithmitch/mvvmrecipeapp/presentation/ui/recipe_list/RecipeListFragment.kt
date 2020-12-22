package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.presentation.components.FoodCategoryChip
import com.codingwithmitch.mvvmrecipeapp.presentation.components.RecipeCard
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
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                Column {
                    Surface(
                            modifier = Modifier
                                    .fillMaxWidth()
                            ,
                            color = Color.White,
                            elevation = 8.dp,
                    ){
                        Column{
                            Row(modifier = Modifier.fillMaxWidth()){
                                TextField(
                                        modifier = Modifier
                                                .fillMaxWidth(.9f)
                                                .padding(8.dp)
                                        ,
                                        value = query,
                                        onValueChange = {
                                            viewModel.onQueryChanged(it)
                                        },
                                        label = {
                                            Text(text = "Search")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Done,
                                        ),
                                        leadingIcon = {
                                            Icon(Icons.Filled.Search)
                                        },
                                        onImeActionPerformed = { action, softKeyboardController ->
                                            if (action == ImeAction.Done) {
                                                viewModel.newSearch()
                                                softKeyboardController?.hideSoftwareKeyboard()
                                            }
                                        },
                                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                        backgroundColor = MaterialTheme.colors.surface
                                )
                            }
                            ScrollableRow(
                                    modifier = Modifier
                                            .padding(start = 8.dp, bottom = 8.dp)
                                    ,
                            ) {
                                for(category in getAllFoodCategories()){
                                    FoodCategoryChip(
                                            category = category.value,
                                            isSelected = category == getFoodCategory(query),
                                            onSelectedCategoryChanged = {
                                                viewModel.onSelectedCategoryChanged(it)
                                            },
                                            onExecuteSearch = viewModel::newSearch,
                                    )
                                }
                            }
                        }
                    }

                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ){index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }

}





















