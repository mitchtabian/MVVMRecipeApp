package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Center a circular indeterminate progress bar with optional vertical bias.
 */
@Composable
fun CircularIndeterminateProgressBar(isDisplayed: Boolean, verticalBias: Float){
    if(isDisplayed){
        ConstraintLayout(
                modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
        ){
            val (progressBar) = createRefs()
            val topBias = createGuidelineFromTop(verticalBias)
            CircularProgressIndicator(
                    modifier = Modifier
                            .constrainAs(progressBar) {
                                top.linkTo(topBias)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            },
                color = MaterialTheme.colors.secondary
            )
        }

    }
}












