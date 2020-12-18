package com.codingwithmitch.mvvmrecipeapp.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.codingwithmitch.mvvmrecipeapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

const val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate

@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // show default image while image loads
    Glide.with(AmbientContext.current)
            .asBitmap()
            .load(defaultImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) { }
                override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                ) {
                    bitmapState.value = resource
                }
            })

    // get network image
    Glide.with(AmbientContext.current)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) { }
                override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                ) {
                    bitmapState.value = resource
                }
            })

    return bitmapState
}





@ExperimentalCoroutinesApi
@Composable
fun loadPicture(drawableId: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // get network image
    Glide.with(ContextAmbient.current)
            .asBitmap()
            .load(drawableId)
            .into(object : CustomTarget<Bitmap>() {

                override fun onLoadCleared(placeholder: Drawable?) { }

                override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                ) {
                    bitmapState.value = resource
                }
            })

    return bitmapState
}








