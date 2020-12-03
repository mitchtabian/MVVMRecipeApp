package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.components.LoadingRecipeShimmer
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent
import com.codingwithmitch.mvvmrecipeapp.util.DEFAULT_RECIPE_IMAGE
import com.codingwithmitch.mvvmrecipeapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi


const val IMAGE_HEIGHT = 260

@ExperimentalCoroutinesApi
@Composable
fun RecipeDetailScreen(
        displayProgressBar: Boolean,
        recipe: Recipe?,
        recipeId: Int,
        onTriggerEvent: (RecipeEvent) -> Unit,
){
    if(recipe == null){
        onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
    }
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
            val image by loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).collectAsState()
            image?.let { img ->
                Image(
                        asset = img.asImageAsset(),
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