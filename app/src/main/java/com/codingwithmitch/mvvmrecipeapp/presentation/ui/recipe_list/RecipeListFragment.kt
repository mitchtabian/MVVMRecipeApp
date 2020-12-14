package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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

                Column(
                        modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(

                    ) {
                        TextField(
                                modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .padding(8.dp),
                                value = "Chicken",
                                onValueChange = {

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
//                                        if (action == ImeAction.Done) {
//                                            onExecuteSearch()
//                                            softKeyboardController?.hideSoftwareKeyboard()
//                                        }
                                },
                                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                backgroundColor = MaterialTheme.colors.surface
                        )
                    }

                    val recipes = viewModel.recipes.value

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





















