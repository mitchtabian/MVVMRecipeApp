package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.ShimmerAnimationDefinitions.AnimationState.END
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.ShimmerAnimationDefinitions.AnimationState.START
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.ShimmerAnimationDefinitions.shimmerDpPropKey
import com.codingwithmitch.mvvmrecipeapp.presentation.components.util.ShimmerAnimationDefinitions.shimmerTranslateAnimation

@Composable
fun LoadingRecipeListShimmer(
        imageHeight: Int,
){

    val shimmerTranslateAnim = transition(
        definition = shimmerTranslateAnimation,
        initState = START,
        toState = END
    )

    val list = listOf(
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .1f),
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .8f),
    )
    val floatValue = shimmerTranslateAnim[shimmerDpPropKey]

    ScrollableColumn() {
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
        ShimmerRecipeCardItem(list, floatValue.value, imageHeight)
    }

}