package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.util.DEFAULT_RECIPE_IMAGE
import com.codingwithmitch.mvvmrecipeapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeListLoading(){
    ScrollableColumn() {
        for(i in 0..15) LoadingRecipe()
    }
}

@ExperimentalCoroutinesApi
@Composable
fun LoadingRecipe(){
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
        val image by loadPicture(drawableId = DEFAULT_RECIPE_IMAGE).collectAsState()
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
}













