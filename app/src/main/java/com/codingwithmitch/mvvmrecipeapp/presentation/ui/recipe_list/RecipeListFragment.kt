package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

                val query = mutableStateOf("Chicken")

                Column {
                    Surface(
                            modifier = Modifier
                                    .fillMaxWidth()
                            ,
                            color = MaterialTheme.colors.secondary,
                            elevation = 8.dp,
                            content = {
                                Row(modifier = Modifier.fillMaxWidth()){
                                    TextField(
                                            modifier = Modifier
                                                    .fillMaxWidth(.9f)
                                                    .padding(8.dp)
                                            ,
                                            value = query.value,
                                            onValueChange = {
                                                query.value = it
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
                            }
                    )
                    LazyColumnForIndexed(
                            items = recipes
                    ) { index, recipe ->
                        RecipeCard(recipe = recipe, onClick = {})
                    }
                }
            }
        }
    }

}





















