package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.BaseApplication
import com.codingwithmitch.mvvmrecipeapp.presentation.components.LoadingRecipeShimmer
import com.codingwithmitch.mvvmrecipeapp.presentation.theme.AppTheme
import com.codingwithmitch.mvvmrecipeapp.util.DEFAULT_RECIPE_IMAGE
import com.codingwithmitch.mvvmrecipeapp.util.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

const val IMAGE_HEIGHT = 260

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val displayProgressBar = viewModel.loading.value

                val recipe = viewModel.recipe.value

                AppTheme(
                    darkTheme = !application.isLight,
                    progressBarIsDisplayed = displayProgressBar,
                ){
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                            scaffoldState = scaffoldState,
                            snackbarHost = {
                                scaffoldState.snackbarHostState
                            }
                    ) {
                        if (displayProgressBar && recipe == null) LoadingRecipeShimmer(IMAGE_HEIGHT)
                        else recipe?.let {
                            RecipeView(
                                    recipe = it,
                            )
                        }
//                    LoadingRecipeShimmer(IMAGE_HEIGHT)
                    }
                }
            }
        }
    }
}


@ExperimentalCoroutinesApi
@Composable
fun RecipeView(
        recipe: Recipe,
){
    ScrollableColumn(
            modifier = Modifier
                    .fillMaxWidth()
    ) {
        recipe.featuredImage?.let { url ->
            val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(IMAGE_HEIGHT.dp)
                        ,
                        contentScale = ContentScale.Crop,
                )
            }
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
            ) {
                recipe.title?.let { title ->
                    Row(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp)
                    ){
                        Text(
                                text = title,
                                modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .wrapContentWidth(Alignment.Start)
                                ,
                                style = MaterialTheme.typography.h3
                        )
                        val rank = recipe.rating.toString()
                        Text(
                                text = rank,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.End)
                                        .align(Alignment.CenterVertically)
                                ,
                                style = MaterialTheme.typography.h5
                        )
                    }
                }
                recipe.publisher?.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                            text = if(updated != null) {
                                "Updated ${updated} by ${publisher}"
                            }
                            else {
                                "By ${publisher}"
                            }
                            ,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ,
                            style = MaterialTheme.typography.caption
                    )
                }
                recipe.description?.let { description ->
                    if(description != "N/A"){
                        Text(
                                text = description,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ,
                                style = MaterialTheme.typography.body1
                        )
                    }
                }
                recipe.ingredients?.let { ingredients ->
                    for(ingredient in ingredients){
                        Text(
                                text = ingredient,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 4.dp)
                                ,
                                style = MaterialTheme.typography.body1
                        )
                    }
                }
                recipe.cookingInstructions?.let { instructions ->
                    Text(
                            text = instructions,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp)
                            ,
                            style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}




















