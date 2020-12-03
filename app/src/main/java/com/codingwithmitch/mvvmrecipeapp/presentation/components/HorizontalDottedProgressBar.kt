package com.codingwithmitch.mvvmrecipeapp.presentation.components

import android.util.Log
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.*
import androidx.compose.animation.core.AnimationConstants.Infinite
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.SwellState.*
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.swellDefinition
import com.codingwithmitch.mvvmrecipeapp.util.TAG


object SwellAnimationDefinitions {

    enum class SwellState {
        FIRST, SECOND, THIRD,
    }

    val swellIntPropKey = IntPropKey("swell_int")

    val swellDefinition = transitionDefinition<SwellState> {
        state(FIRST) { this[swellIntPropKey] = 1 }
        state(SECOND) { this[swellIntPropKey] = 2 }
        state(THIRD) { this[swellIntPropKey] = 3 }

        transition(
            FIRST to SECOND,
            SECOND to THIRD,
            THIRD to FIRST
        ) {
            swellIntPropKey using repeatable(
                iterations =  Infinite,
                animation = tween(
                    durationMillis = 900,
                    easing = LinearOutSlowInEasing
                )
            )
        }

    }

}


@Composable
fun HorizontalDottedProgressBar(isDisplayed: Boolean,) {
    if (isDisplayed) {

        val color = MaterialTheme.colors.primary

        val swellAnim = transition(
            definition = swellDefinition,
            initState = FIRST,
            toState = THIRD
        )

        val state = swellAnim[SwellAnimationDefinitions.swellIntPropKey]

        Log.d(TAG, "HorizontalDottedProgressBar: ${state}")

        Canvas(
            modifier = Modifier.fillMaxWidth().height(55.dp),
        ){

            val radius = (8.dp).toIntPx().toFloat()
            val swellRadius = (12.dp).toIntPx().toFloat()
            val padding = (8.dp).toIntPx().toFloat()

            if(state == 1){
                drawCircle(
                    radius = swellRadius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x + radius*2 + padding, y=this.center.y)
                )
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x, y=this.center.y)
                )
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x - radius*2 - padding, y=this.center.y)
                )
            }
            else if(state == 2){
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x + radius*2 + padding, y=this.center.y)
                )
                drawCircle(
                    radius = swellRadius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x, y=this.center.y)
                )
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x - radius*2 - padding, y=this.center.y)
                )
            }
            else if(state == 3){
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x + radius*2 + padding, y=this.center.y)
                )
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x, y=this.center.y)
                )
                drawCircle(
                    radius = swellRadius,
                    brush = SolidColor(color),
                    center = Offset(x=this.center.x - radius*2 - padding, y=this.center.y)
                )
            }


        }
    }
}
















