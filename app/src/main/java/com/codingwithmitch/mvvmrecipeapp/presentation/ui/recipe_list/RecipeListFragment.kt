package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.codingwithmitch.mvvmrecipeapp.R
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.components.*
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import com.codingwithmitch.openchat.common.framework.presentation.theme.AppTheme
import com.codingwithmitch.openchat.common.framework.presentation.theme.Black5
import com.codingwithmitch.openchat.common.framework.presentation.theme.Grey1
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

                val displayProgressBar by viewModel.loading.collectAsState()

                val selectedCategory by viewModel.selectedCategory.collectAsState()

                val categories = getAllFoodCategories()

                val recipes by viewModel.recipes.collectAsState()

                val query by viewModel.query.collectAsState()

                val page by viewModel.page.collectAsState()

                AppTheme(
                        darkTheme = !application.isLight,
                        progressBarIsDisplayed = displayProgressBar,
                ){
                    Column(
                            modifier = Modifier
                                    .background(color = if(application.isLight) Grey1 else Black5)
                    ) {
                        SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(NewSearchEvent())
                                },
                                categories = categories,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                scrollPosition = viewModel.categoryScrollPosition,
                                onChangeScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = application::toggleLightTheme,
                        )
                        if (displayProgressBar && recipes.isEmpty()) LoadingListShimmer()
                        else RecipeList(
                                recipes = recipes,
                                page = page,
                                onNextPage = {
                                    viewModel.onTriggerEvent(NextPageEvent())
                                },
                                isLoading = displayProgressBar,
                                onSelectRecipe = {
                                    val bundle = Bundle()
                                    bundle.putInt("recipeId", it)
                                    findNavController().navigate(R.id.viewRecipe, bundle)
                                },
                                onError = {
                                    TODO("show error toast somehow")
                                }
                        )
                    }

                }
            }
        }
    }

}

@ExperimentalCoroutinesApi
@Composable
fun RecipeList(
        recipes: List<Recipe>,
        page: Int,
        onNextPage: () -> Unit,
        isLoading: Boolean = false,
        onSelectRecipe: (Int) -> Unit,
        onError: (String) -> Unit,
){
    val state = rememberLazyListState()
    LazyColumnForIndexed(
            items = recipes,
            state = state,
    ) { index, recipe ->
        Log.d(TAG, "RecipeList: index: ${index}")
        if((index + 1) >= (page * PAGE_SIZE) && !isLoading){
            onNextPage()
        }
        RecipeCard(
                recipe = recipe,
                onClick = {
                    recipe.id?.let{
                        onSelectRecipe(it)
                    }?: onError("Error. There's something wrong with that recipe.")
                }
        )
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
    scrollPosition: Float,
    onChangeScrollPosition: (Float) -> Unit,
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
                                onValueChange = {
                                    onQueryChanged(it)
                                },
                                label = {
                                    Text(text = "Search")
                                },
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done,
                                ),
                                leadingIcon = { Icon(Icons.Filled.Search) },
                                onImeActionPerformed = { action, softKeyboardController ->
                                    if (action == ImeAction.Done) {
                                        onExecuteSearch()
                                        softKeyboardController?.hideSoftwareKeyboard()
                                    }
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
                                            .constrainAs(menu) {
                                                end.linkTo(parent.end)
                                                linkTo(top = parent.top, bottom = parent.bottom)
                                            },
                                    onClick = onToggleTheme,
                                    icon = { Icon(Icons.Filled.MoreVert) },
                            )
                        }
                    }
                    val scrollState = rememberScrollState()
                    ScrollableRow(
                            modifier = Modifier
                                    .padding(start = 8.dp, bottom = 8.dp)
                            ,
                            scrollState = scrollState,
                    ) {

                        // restore scroll position after rotation
                        scrollState.scrollTo(scrollPosition)

                        // display FoodChips
                        for (category in categories) {
                            FoodCategoryChip(
                                    category = category.value,
                                    isSelected = selectedCategory == category,
                                    onSelectedCategoryChanged = {
                                        onChangeScrollPosition(scrollState.value)
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

























