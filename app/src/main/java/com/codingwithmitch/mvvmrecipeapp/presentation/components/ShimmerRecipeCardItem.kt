package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeCardItem(
    colors: List<Color>,
    floatAnim: Float = 0f,
    imageHeight: Int,
) {
    val brush = linearGradient(colors, start = Offset(-10f, -10f), end = Offset(floatAnim, floatAnim))
    Column(modifier = Modifier.padding(16.dp)) {
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                    modifier = Modifier
                            .fillMaxWidth()
                            .preferredSize(imageHeight.dp)
                            .background(brush = brush)
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
                            .background(brush = brush)
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
                            .background(brush = brush)
            )
        }
    }
}











