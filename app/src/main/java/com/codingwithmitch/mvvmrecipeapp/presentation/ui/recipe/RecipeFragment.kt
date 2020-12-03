package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.R
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.codingwithmitch.mvvmrecipeapp.presentation.navigation.Navigator
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.navigation.RecipeDestination
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.screens.RecipeDetailScreen
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.screens.RecipeListScreen
import com.codingwithmitch.openchat.common.framework.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalFocus
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    @Inject
    lateinit var snackbarController: SnackbarController

    private val viewModel: RecipeViewModel by viewModels()

    private val dispatcher: OnBackPressedDispatcher? by lazy {
        activity?.onBackPressedDispatcher
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
                R.layout.compose_view, container, false
        ).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {

                val displayProgressBar = viewModel.loading.value

                val navigator: Navigator<RecipeDestination>? = dispatcher?.let { onBackDispatcher ->
                    rememberSavedInstanceState(
                            saver = Navigator.saver(onBackDispatcher)
                    ) {
                        Navigator(RecipeDestination.RecipeList, onBackDispatcher)
                    }
                }
                AppTheme(
                        darkTheme = !application.isLight,
                        progressBarIsDisplayed = displayProgressBar,
                ) {
                    navigator?.let { fragmentNavigation ->
                        when (val destination = fragmentNavigation.current) {
                            is RecipeDestination.RecipeList -> {
                                viewModel.selectedRecipe.value = null
                                RecipeListScreen(
                                        application = application,
                                        snackbarController = snackbarController,
                                        displayProgressBar = displayProgressBar,
                                        selectedCategory = viewModel.selectedCategory.value,
                                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                        categories = getAllFoodCategories(),
                                        categoryScrollPosition = viewModel.categoryScrollPosition,
                                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                        recipes = viewModel.recipes.value,
                                        query = viewModel.query.value,
                                        onQueryChanged = viewModel::onQueryChanged,
                                        page = viewModel.page.value,
                                        genericDialogInfo = viewModel.genericDialogInfo.value,
                                        onTriggerEvent = viewModel::onTriggerEvent,
                                        navigator = fragmentNavigation,
                                )
                            }
                            is RecipeDestination.RecipeDetail -> {
                                RecipeDetailScreen(
                                        displayProgressBar = displayProgressBar,
                                        recipe = viewModel.selectedRecipe.value,
                                        recipeId = destination.recipeId,
                                        onTriggerEvent = viewModel::onTriggerEvent,
                                )
                            }
                        }
                    }?: NavError()
                }
            }
        }
    }
}

@Composable
fun NavError(){
    Text("Navigation error.")
    Text("Try restarting the app.")
}

























