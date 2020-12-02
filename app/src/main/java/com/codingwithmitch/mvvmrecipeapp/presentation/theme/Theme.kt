package com.codingwithmitch.openchat.common.framework.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.codingwithmitch.mvvmrecipeapp.presentation.components.CircularIndeterminateProgressBar


private val LightThemeColors = lightColors(
        primary = Blue600,
        primaryVariant = Blue400,
        onPrimary = Black5,
        secondary = Color.White,
        secondaryVariant = Orange700,
        onSecondary = Color.Black,
        error = RedErrorDark,
        onError = RedErrorLight,
        background = Grey1,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Black5,
)

private val DarkThemeColors = darkColors(
        primary = Blue700,
        primaryVariant = Color.White,
        onPrimary = Color.White,
        secondary = Black3,
        onSecondary = Color.White,
        error = RedErrorLight,
        background = Color.Black,
        onBackground = Color.White,
        surface = Black3,
        onSurface = Color.White,
)

@Composable
val Colors.snackbarAction: Color
        get() = if (isLight) Color.White else Color.White

@Composable
fun AppTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        progressBarIsDisplayed: Boolean = false,
        content: @Composable () -> Unit,
) {
        MaterialTheme(
                colors = if (darkTheme) DarkThemeColors else LightThemeColors,
                typography = QuickSandTypography,
                shapes = AppShapes,
        ){

                Box(
                        modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = if(!darkTheme) Grey1 else Black5)
                ){
                        content()
                        CircularIndeterminateProgressBar(isDisplayed = progressBarIsDisplayed, 0.2f)
                }
        }
}
































