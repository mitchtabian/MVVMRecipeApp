package com.codingwithmitch.mvvmrecipeapp.presentation.components.util

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp


/**
 * Inspiration from:
 * https://github.com/Gurupreet/ComposeCookBook/blob/master/app/src/main/java/com/guru/composecookbook/ui/Animations/AnimationDefinitions.kt
 */
object ShimmerAnimationDefinitions {

    enum class AnimationState {
        START, END
    }

    val shimmerDpPropKey = DpPropKey("shimmerDp")

    val shimmerTranslateAnimation = transitionDefinition<AnimationState> {
        state(AnimationState.START) { this[shimmerDpPropKey] = 0.dp }
        state(AnimationState.END) { this[shimmerDpPropKey] = 5000.dp }

        transition(AnimationState.START, AnimationState.END) {
            shimmerDpPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 1800,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart
            )
        }
    }


}
