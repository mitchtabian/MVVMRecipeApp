package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

private val idleIconSize = 50.dp
private val expandedIconSize = 60.dp

@Composable
fun AnimatedHeartButton() {
    var needLike by remember { mutableStateOf(true) }
    val transition = updateTransition(targetState = needLike, label = "LikeTransition")

    val animatedSizeDp by transition.animateDp(label = "sizeTransition",
        transitionSpec = {
            keyframes {
                durationMillis = 100
                expandedIconSize at 50
                idleIconSize at 100
            }
        }) { needLike ->
        //in both like and not like cases, heart will have same size, we just need the animation here.
        if (needLike) {
            idleIconSize
        } else {
            idleIconSize
        }
    }

    val heartColor by transition.animateColor(
        transitionSpec = { tween(100) }, label = "colorTransition"
    ) { needLike ->
        if (needLike) {//if heart is not liked, then it will have..
            Color.Gray
        } else {//if heart is already liked, then it will have..
            Color.Red
        }
    }

    Image(
        modifier = Modifier
            .size(animatedSizeDp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = { needLike = !needLike }
            ),
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
        colorFilter = ColorFilter.tint(heartColor)
    )
}














