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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.SwellState.FIRST
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.SwellState.SECOND
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.swellDefinition
import com.codingwithmitch.mvvmrecipeapp.presentation.components.SwellAnimationDefinitions.swellPropKey
import com.codingwithmitch.mvvmrecipeapp.util.TAG


object SwellAnimationDefinitions {

    enum class SwellState {
        FIRST, SECOND,
    }

    val swellPropKey = DpPropKey("swell_key")

    val swellDefinition = transitionDefinition<SwellState> {
        state(FIRST) { this[swellPropKey] = 0.dp }
        state(SECOND) { this[swellPropKey] = 120.dp }

        transition(
            FIRST to SECOND,
        ) {
            swellPropKey using repeatable(
                iterations =  Infinite,
                animation = tween(
                        delayMillis = 80,
                        durationMillis = 700,
                        easing = LinearEasing
                )
            )
        }
    }
}


@Composable
fun HorizontalDottedProgressBar() {
    val color = MaterialTheme.colors.primary
    val start by remember{ mutableStateOf(FIRST)}
    val end by remember{ mutableStateOf(SECOND)}

    val swellAnim = transition(
        definition = swellDefinition,
        initState = start,
        toState = end
    )

    val state = swellAnim[swellPropKey].value

    Log.d(TAG, "HorizontalDottedProgressBar: ${state}")

    DrawCanvas(state = state, color = color)
}


@Composable
fun DrawCanvas(
        state: Float,
        color: Color,
){
    Canvas(
            modifier = Modifier.fillMaxWidth().height(55.dp),
    ){

        val radius = (4.dp).toIntPx().toFloat()
        val padding = (6.dp).toIntPx().toFloat()

        if(state >= 0 && state <= 20){
            for(i in -2..2) {
                drawCircle(
                        radius = radius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
        else if(state > 20 && state <= 40){
            for(i in -2..2) {
                var swellRadius = radius
                if(i == -2){
                    swellRadius = radius * 2
                }
                drawCircle(
                        radius = swellRadius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
        else if(state > 40 && state <= 60){
            for(i in -2..2) {
                var swellRadius = radius
                if(i == -1){
                    swellRadius = radius * 2
                }
                drawCircle(
                        radius = swellRadius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
        else if(state > 60 && state <= 80){
            for(i in -2..2) {
                var swellRadius = radius
                if(i == 0){
                    swellRadius = radius * 2
                }
                drawCircle(
                        radius = swellRadius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
        else if(state > 80 && state <= 100){
            for(i in -2..2) {
                var swellRadius = radius
                if(i == 1){
                    swellRadius = radius * 2
                }
                drawCircle(
                        radius = swellRadius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
        else if(state > 100){
            for(i in -2..2) {
                var swellRadius = radius
                if(i == 2){
                    swellRadius = radius * 2
                }
                drawCircle(
                        radius = swellRadius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * i + padding * i, y = this.center.y)
                )
            }
        }
    }
}















