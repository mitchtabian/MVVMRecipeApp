# What is this project?
This course will replace my old java mvvm introduction: [https://codingwithmitch.com/courses/rest-api-mvvm-retrofit2/](https://codingwithmitch.com/courses/rest-api-mvvm-retrofit2/).

**This is a beginner course.**

# Main Features
1. Kotlin
1. MVVM
1. Jetpack Compose
1. MutableState
	- Simpler than LiveData or Flow with respect to composables.
1. Hilt
1. Navigation Components
1. Retrofit2
1. Single Activity Architecture
1. App Themes
1. Custom Fonts
1. Light and Dark theme
	- Toggle between themes


# Compose features
1. Snackbars
2. Dialogs
3. Theming
4. Fonts
5. Colors
6. Animations introduction
	- creating a 'shimmering' loading animation
7. ConstraintLayout
8. Rows
9. Columns
10. Scaffold
11. AppBar
12. Circular Progress Indicator


# Why not Flow or LiveData?
1. LiveData
	- LiveData has always been a love/hate thing for me. It's great for some things but can be very annoying for others. For example you MUST have an observer for livedata to emit anything. Making it pretty useless in a repository or anywhere except viewmodel->view communication.
1. Flow
	1. Flow is great. I use flows for my use cases when building out clean architecture. You don't need an "android observer" because flow lives in the coroutine world and there's even support for operators like RxJava has.
	1. Easily converted from livedata with a single function call.
	1. Tons of other useful properties. Check out the flow [docs](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/).
1. StateFlow
	1. StateFlow is the newest addition to kotlinx.coroutines.flow. Simply put StateFlow is a flow with special properties.
	1. Originally I had planned to use StateFlow in this course but it's really just totally unnecessary. I don't want to show you an unrealistic example of how to use something so I'm stinking with the very simple [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState) in viewmodels.




# References
1. https://github.com/android/compose-samples
1. https://developer.android.com/jetpack/compose
1. https://developer.android.com/jetpack/compose/state
1. Color system
	- https://material.io/design/color/the-color-system.html

