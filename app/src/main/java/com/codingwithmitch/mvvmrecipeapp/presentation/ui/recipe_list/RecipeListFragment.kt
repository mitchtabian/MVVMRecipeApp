package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.presentation.components.*
import com.codingwithmitch.mvvmrecipeapp.presentation.components.HeartButtonState.*
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.tan

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

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

                val categoryScrollPosition = viewModel.categoryScrollPosition

                val loading = viewModel.loading.value

                Column {

                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        categories = getAllFoodCategories(),
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        scrollPosition = categoryScrollPosition,
                        onChangeScrollPosition = viewModel::onChangeCategoryScrollPosition,
                    )

//                    GradientDemo()

//                    LoadingRecipeListShimmer(imageHeight = 250.dp,)


                    Box(modifier = Modifier.fillMaxSize()) {
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
                    }

                }
            }
        }
    }

}

@Composable
fun GradientDemo(
    heightPercentage: Float = 0.2f,

){
    WithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = with(AmbientDensity.current) { maxWidth.value.dp.toPx() }
        val height = with(AmbientDensity.current) { maxHeight.value.dp.toPx() }
        Log.d(TAG, "GradientDemo:  ${width}")
        Log.d(TAG, "GradientDemo: ${height}")

        val h = height * heightPercentage
        val gradientWidth = (h * tan(3.14 / 4)).toFloat()
        Log.d(TAG, "GradientDemo: gradientWidth: ${gradientWidth}")

        val colors = listOf(
            Color.Blue,
            Color.Red,
            Color.Blue,
        )
        val brush = linearGradient(
            colors,
            start = Offset(0f, 0f),
            end = Offset(gradientWidth, gradientWidth)
        )
        Column(
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = brush)
                )
            }
        }
    }
}























