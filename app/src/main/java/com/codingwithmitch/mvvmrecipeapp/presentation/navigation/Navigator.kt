package com.codingwithmitch.mvvmrecipeapp.presentation.navigation

import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.savedinstancestate.listSaver
import androidx.compose.runtime.toMutableStateList

/**
 * A simple navigator which maintains a back stack.
 *
 * https://github.com/android/compose-samples/blob/2cff65334e/Jetsnack/app/src/main/java/com/example/jetsnack/ui/utils/Navigation.kt
 */
class Navigator<T> private constructor(
        initialBackStack: List<T>,
        backDispatcher: OnBackPressedDispatcher
) {
    constructor(
            initial: T,
            backDispatcher: OnBackPressedDispatcher
    ) : this(listOf(initial), backDispatcher)

    private val backStack = initialBackStack.toMutableStateList()
    private val backCallback = object : OnBackPressedCallback(canGoBack()) {
        override fun handleOnBackPressed() {
            back()
        }
    }.also { callback ->
        backDispatcher.addCallback(callback)
    }
    val current: T get() = backStack.last()

    fun back() {
        backStack.removeAt(backStack.lastIndex)
        backCallback.isEnabled = canGoBack()
    }

    fun navigate(destination: T) {
        backStack += destination
        backCallback.isEnabled = canGoBack()
    }

    private fun canGoBack(): Boolean = backStack.size > 1

    companion object {
        /**
         * Serialize the back stack to save to instance state.
         */
        fun <T : Parcelable> saver(backDispatcher: OnBackPressedDispatcher) =
                listSaver<Navigator<T>, T>(
                        save = { navigator -> navigator.backStack.toList() },
                        restore = { backstack -> Navigator(backstack, backDispatcher) }
                )
    }
}
















