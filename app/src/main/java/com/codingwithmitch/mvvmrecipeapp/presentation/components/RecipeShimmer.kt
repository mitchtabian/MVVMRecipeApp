package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.AnimationDefinitions

@Composable
fun LoadingRecipeListShimmer(
        imageHeight: Int,
){

    val dpStartState by remember { mutableStateOf(AnimationDefinitions.AnimationState.START) }
    val dpEndState by remember { mutableStateOf(AnimationDefinitions.AnimationState.END) }

    val shimmerTranslateAnim = transition(
            definition = AnimationDefinitions.shimmerTranslateAnimation,
            initState = dpStartState,
            toState = dpEndState
    )

    val shimmerColorAnim = transition(
            definition = AnimationDefinitions.shimmerColorAnimation,
            initState = AnimationDefinitions.AnimationState.START,
            toState = AnimationDefinitions.AnimationState.END,
    )

    val list = listOf(
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey],
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey].copy(alpha = 0.1f)
    )
    val dpValue = shimmerTranslateAnim[AnimationDefinitions.shimmerDpPropKey]

    ScrollableColumn() {
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
        ShimmerRecipeCardItem(list, dpValue.value, imageHeight)
    }

}


@Composable
fun LoadingRecipeShimmer(
        imageHeight: Int,
){

    val dpStartState by remember { mutableStateOf(AnimationDefinitions.AnimationState.START) }
    val dpEndState by remember { mutableStateOf(AnimationDefinitions.AnimationState.END) }

    val shimmerTranslateAnim = transition(
            definition = AnimationDefinitions.shimmerTranslateAnimation,
            initState = dpStartState,
            toState = dpEndState
    )

    val shimmerColorAnim = transition(
            definition = AnimationDefinitions.shimmerColorAnimation,
            initState = AnimationDefinitions.AnimationState.START,
            toState = AnimationDefinitions.AnimationState.END,
    )

    val list = listOf(
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey],
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey].copy(alpha = 0.1f),
    )
    val dpValue = shimmerTranslateAnim[AnimationDefinitions.shimmerDpPropKey]

    ScrollableColumn() {
        ShimmerRecipeItem(list, dpValue.value, imageHeight)
    }
}

@Composable
fun ShimmerRecipeItem(
        lists: List<Color>,
        floatAnim: Float = 0f,
        imageHeight: Int,
){
    val largeBrush = VerticalGradient(lists, 0f, floatAnim)
    val smallBrush = VerticalGradient(lists, 0f, (floatAnim * (20f / imageHeight)))
    Column {
        Spacer(
                modifier = Modifier
                        .fillMaxWidth()
                        .preferredSize(imageHeight.dp)
                        .background(brush = largeBrush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
            ){
                Surface(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start)
                ) {
                    Spacer(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .preferredHeight(20.dp)
                                    .background(brush = smallBrush)
                    )
                }
                Surface(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .wrapContentWidth(Alignment.End)
                ) {
                    Spacer(
                            modifier = Modifier
                                    .width(20.dp)
                                    .preferredHeight(20.dp)
                                    .background(brush = smallBrush)
                    )
                }
            }

            Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                            .padding(vertical = 8.dp)
            ) {
                Spacer(
                        modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .preferredHeight(15.dp)
                                .background(brush = smallBrush)
                )
            }
            Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                            .padding(vertical = 8.dp)
            ) {
                Spacer(
                        modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .preferredHeight(15.dp)
                                .background(brush = smallBrush)
                )
            }
            Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                            .padding(vertical = 8.dp)
            ) {
                Spacer(
                        modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .preferredHeight(15.dp)
                                .background(brush = smallBrush)
                )
            }
            Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                            .padding(vertical = 8.dp)
            ) {
                Spacer(
                        modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .preferredHeight(15.dp)
                                .background(brush = smallBrush)
                )
            }
            Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                            .padding(vertical = 8.dp)
            ) {
                Spacer(
                        modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .preferredHeight(15.dp)
                                .background(brush = smallBrush)
                )
            }
        }
    }
}


@Composable
fun ShimmerRecipeCardItem(
        lists: List<Color>,
        floatAnim: Float = 0f,
        imageHeight: Int,
) {
    val largeBrush = VerticalGradient(lists, 0f, floatAnim)
    val smallBrush = VerticalGradient(lists, 0f, (floatAnim * (20f / imageHeight)))
    Column(modifier = Modifier.padding(16.dp)) {
        Surface(
                shape = MaterialTheme.shapes.small
        ) {
            Spacer(
                    modifier = Modifier
                            .fillMaxWidth()
                            .preferredSize(imageHeight.dp)
                            .background(
                                    brush = largeBrush
                            )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                        .padding(vertical = 8.dp)
        ) {
            Spacer(
                    modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(15.dp)
                            .background(brush = smallBrush)
            )
        }
        Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                        .padding(vertical = 8.dp)
        ) {
            Spacer(
                    modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(15.dp)
                            .background(brush = smallBrush)
            )
        }
    }
}
