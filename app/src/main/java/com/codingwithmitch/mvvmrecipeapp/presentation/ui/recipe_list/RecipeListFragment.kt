package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search

import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.R
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.codingwithmitch.openchat.common.framework.presentation.theme.AppTheme
import com.codingwithmitch.openchat.common.framework.presentation.theme.Grey4
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalFocus
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

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

                val query by viewModel.query.collectAsState()

                val selectedCategory by viewModel.selectedCategory.collectAsState()

                val categories = getAllFoodCategories()

                AppTheme(
                    darkTheme = !application.isLight,
                    progressBarIsDisplayed = progressBarState
                ){
                    Column {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = {
                                viewModel.onTriggerEvent(SearchEvent())
                            },
                            categories = categories,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            categoryScrollPosition = viewModel._categoryScrollPosition,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            onToggleTheme = application::toggleLightTheme,
                        )

                        val recipes by viewModel.recipes.collectAsState()
                        RecipeList(recipes = recipes)
                    }

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

@ExperimentalFocus
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categories: List<FoodCategory>,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    categoryScrollPosition: Float,
    onChangeCategoryScrollPosition: (Float) -> Unit,
    onToggleTheme: () -> Unit,
){
    Surface(
        modifier = Modifier.padding(bottom = 8.dp),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(8.dp),
                        value = query,
                        onValueChange = { onQueryChanged(it) },
                        label = {
                            Text(text = "Search")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done,
                        ),
                        leadingIcon = { Icon(Icons.Filled.Search) },
                        onImeActionPerformed = { action, softKeyboardController ->
                            if (action == ImeAction.Next || action == ImeAction.Done) {
                                softKeyboardController?.hideSoftwareKeyboard()
                            }
                            onExecuteSearch()
                        },
                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                        backgroundColor = MaterialTheme.colors.surface
                    )
                    ConstraintLayout(
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        val (menu) = createRefs()
                        IconButton(
                            modifier = Modifier
                                .constrainAs(menu){
                                    end.linkTo(parent.end)
                                    linkTo(top=parent.top, bottom=parent.bottom)
                                },
                            onClick = onToggleTheme,
                            icon = { Icon(Icons.Filled.MoreVert) },
                        )
                    }

                }
                val scrollState = rememberScrollState()
                ScrollableRow(
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    scrollState = scrollState
                ) {
                    // restore scroll position after rotation
                    scrollState.scrollTo(categoryScrollPosition)
                    // display FoodChips
                    for (category in categories) {
                        FoodChip(
                            category = category.value,
                            isSelected = selectedCategory == category,
                            onSelectedCategoryChanged = {
                                onChangeCategoryScrollPosition(scrollState.value)
                                onSelectedCategoryChanged(it)
                            },
                            onExecuteSearch = onExecuteSearch,
                        )
                    }
                }
            }

        }
    )
}


@Composable
fun FoodChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category)
                    onExecuteSearch()
                }
            )
        ) {
            Surface(
                color = if(isSelected) Grey4 else MaterialTheme.colors.primary,
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
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




















