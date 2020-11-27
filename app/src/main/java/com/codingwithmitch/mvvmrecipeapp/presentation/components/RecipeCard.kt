package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.util.DEFAULT_RECIPE_IMAGE
import com.codingwithmitch.mvvmrecipeapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt

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
            recipe.featuredImage?.let { url ->
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
                Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                ) {
                    Text(
                            text = title,
                            modifier = Modifier
                                    .fillMaxWidth(0.85f)
                            ,
                            style = MaterialTheme.typography.h3
                    )
                    ConstraintLayout(
                            modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        val (socialRank) = createRefs()
                        val rank = recipe.rating.toString()
                        Text(
                                text = rank,
                                modifier = Modifier
                                        .constrainAs(socialRank){
                                            linkTo(top = parent.top, bottom = parent.bottom,)
                                            end.linkTo(parent.end)
                                        }
                                ,
                                style = MaterialTheme.typography.h5
                        )
                    }
                }

            }
        }





    }
}












