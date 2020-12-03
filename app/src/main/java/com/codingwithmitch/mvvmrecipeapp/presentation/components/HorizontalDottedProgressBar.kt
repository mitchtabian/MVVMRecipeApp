package com.codingwithmitch.mvvmrecipeapp.presentation.components

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


object SwellAnimationDefinitions {

    enum class SwellState {
        FIRST, SECOND,
    }

    val swellPropKey = FloatPropKey("swell_key")

    val swellDefinition = transitionDefinition<SwellState> {
        state(FIRST) { this[swellPropKey] = 0f }
        state(SECOND) { this[swellPropKey] = 6f }

        transition(
            FIRST to SECOND,
        ) {
            swellPropKey using repeatable(
                iterations =  Infinite,
                animation = tween(
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

    val state = swellAnim[swellPropKey]

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

        for(i in 1..5) {
            if(i-1 == state.toInt()){
                drawCircle(
                        radius = radius*2,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * (i-3)  + padding * (i-3), y = this.center.y)
                )
            }
            else{
                drawCircle(
                        radius = radius,
                        brush = SolidColor(color),
                        center = Offset(x = this.center.x + radius * 2 * (i-3) + padding * (i-3), y = this.center.y)
                )
            }
        }
    }
}















