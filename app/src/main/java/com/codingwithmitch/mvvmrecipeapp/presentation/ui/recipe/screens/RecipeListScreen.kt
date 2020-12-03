package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.screens

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.codingwithmitch.mvvmrecipeapp.R
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.components.*
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.GenericDialogInfo
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.codingwithmitch.mvvmrecipeapp.presentation.navigation.Navigator
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.*
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.navigation.RecipeDestination
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import com.codingwithmitch.openchat.common.framework.presentation.theme.AppTheme
import com.codingwithmitch.openchat.common.framework.presentation.theme.Black5
import com.codingwithmitch.openchat.common.framework.presentation.theme.Grey1
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFocus
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun RecipeListScreen(
        application: BaseApplication,
        snackbarController: SnackbarController,
        displayProgressBar: Boolean,
        selectedCategory: FoodCategory?,
        onSelectedCategoryChanged: (String) -> Unit,
        categories: List<FoodCategory>,
        categoryScrollPosition: Float,
        onChangeCategoryScrollPosition: (Float) -> Unit,
        recipes: List<Recipe>,
        query: String,
        onQueryChanged: (String) -> Unit,
        page: Int,
        genericDialogInfo: GenericDialogInfo?,
        onTriggerEvent: (RecipeEvent) -> Unit,
        navigator: Navigator<RecipeDestination>,
        ){
    val snackbarActionLabel = stringResource(id = R.string.dismiss)

    val scaffoldState = rememberScaffoldState()

    Scaffold(
            topBar = {
                SearchAppBar(
                        query = query,
                        onQueryChanged = onQueryChanged,
                        onExecuteSearch = {
                            onTriggerEvent(RecipeEvent.NewSearchEvent())
                        },
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = onSelectedCategoryChanged,
                        scrollPosition = categoryScrollPosition,
                        onChangeScrollPosition = onChangeCategoryScrollPosition,
                        onToggleTheme = application::toggleLightTheme,
                        onError = {

                            // Can use a snackbar or dialog here. Your choice.
                            snackbarController.handleSnackbarError(
                                    scaffoldState = scaffoldState,
                                    message = it,
                                    actionLabel = snackbarActionLabel
                            )
                        }
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            },

            ) {
        Column(
                modifier = Modifier
                        .background(color = if(application.isLight) Grey1 else Black5)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (displayProgressBar && recipes.isEmpty()){
                    Column {
                        HorizontalDottedProgressBar()
                        LoadingRecipeListShimmer(200)
                    }
                }
                else RecipeList(
                        recipes = recipes,
                        page = page,
                        onNextPage = {
                            onTriggerEvent(RecipeEvent.NextPageEvent())
                        },
                        isLoading = displayProgressBar,
                        onSelectRecipe = {
                            navigator.navigate(RecipeDestination.RecipeDetail(it))
                        },
                        onError = {
                            snackbarController.handleSnackbarError(
                                    scaffoldState = scaffoldState,
                                    message = it,
                                    actionLabel = snackbarActionLabel
                            )
                        }
                )
                ErrorSnackbar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                        modifier = Modifier.align(Alignment.BottomCenter)
                )
                genericDialogInfo?.let { dialogInfo ->
                    GenericDialog(
                            onDismiss = dialogInfo.onDismiss,
                            title = dialogInfo.title,
                            description = dialogInfo.description,
                            positiveBtnTxt = dialogInfo.positiveBtnTxt,
                            onPositiveAction = dialogInfo.onPositiveAction,
                            negatveBtnTxt = dialogInfo.negatveBtnTxt,
                            onNegativeAction = dialogInfo.onNegativeAction,
                    )
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
        onError: (String) -> Unit,
){
    Surface(
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colors.secondary,
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
                                    onError = onError,
                            )
                        }
                    }
                }
            }
    )
}
































