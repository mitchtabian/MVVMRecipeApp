package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.components.*
import com.codingwithmitch.mvvmrecipeapp.presentation.theme.AppTheme
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(
                    darkTheme = application.isDark.value
                ) {

                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val categoryScrollPosition = viewModel.categoryScrollPosition

                    val loading = viewModel.loading.value

                    val scaffoldState = rememberScaffoldState()

                    val scope = rememberCoroutineScope()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk"){
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Invalid category: MILK",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    }
                                    else{
                                        viewModel.newSearch()
                                    }
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                scrollPosition = categoryScrollPosition,
                                onChangeScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },

                    ) {
                        Box(modifier = Modifier
                            .background(color = MaterialTheme.colors.surface)
                        ) {
                            if (loading){
                                LoadingRecipeListShimmer(imageHeight = 250.dp,)
                            }else{
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading, verticalBias = 0.3f)
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}



























