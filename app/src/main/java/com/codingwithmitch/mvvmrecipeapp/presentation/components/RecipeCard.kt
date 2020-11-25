package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.util.DEFAULT_RECIPE_IMAGE
import com.codingwithmitch.mvvmrecipeapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeCard(
    recipe: Recipe,
){
    Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                    .padding(
                            bottom = 4.dp,
                            top = 4.dp,
                            start = 1.dp,
                            end = 1.dp
                    )
                    .fillMaxWidth()
            ,
            elevation = 8.dp
    ) {

        Column() {
            recipe.imageUrl?.let { url ->
                val image by loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).collectAsState()
                image?.let { img ->
                    Image(
                            asset = img.asImageAsset(),
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .preferredHeight(225.dp)
                            ,
                            contentScale = ContentScale.Crop,
                    )
                }
            }
            recipe.title?.let { title ->
                Text(
                        text = title,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        style = MaterialTheme.typography.h4
                )
            }
        }





    }
}












