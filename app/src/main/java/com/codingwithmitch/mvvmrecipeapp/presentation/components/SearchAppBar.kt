package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe_list.FoodCategory

@Composable
fun SearchAppBar(
        query: String,
        onQueryChanged: (String) -> Unit,
        onExecuteSearch: () -> Unit,
        categories: List<FoodCategory>,
        selectedCategory: FoodCategory?,
        onSelectedCategoryChanged: (String) -> Unit,
        scrollPosition: Float,
        onChangeScrollPosition: (Float) -> Unit,
        onToggleTheme: () -> Unit,
){
    Surface(
            modifier = Modifier
                    .fillMaxWidth()
            ,
            color = MaterialTheme.colors.secondary,
            elevation = 8.dp,
    ){
        Column{
            Row(modifier = Modifier.fillMaxWidth()){
                TextField(
                        modifier = Modifier
                                .fillMaxWidth(.9f)
                                .padding(8.dp)
                        ,
                        value = query,
                        onValueChange = {
                            onQueryChanged(it)
                        },
                        label = {
                            Text(text = "Search")
                        },
                        keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                        ),
                        leadingIcon = {
                            Icon(Icons.Filled.Search)
                        },
                        onImeActionPerformed = { action, softKeyboardController ->
                            if (action == ImeAction.Done) {
                                onExecuteSearch()
                                softKeyboardController?.hideSoftwareKeyboard()
                            }
                        },
                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                        backgroundColor = MaterialTheme.colors.surface
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val (menu) = createRefs()
                    IconButton(
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                linkTo(top = parent.top, bottom = parent.bottom)
                            },
                        onClick = onToggleTheme
                        ,
                    ){
                        Icon(Icons.Filled.MoreVert)
                    }
                }
            }
            val scrollState = rememberScrollState()
            ScrollableRow(
                    modifier = Modifier
                            .padding(start = 8.dp, bottom = 8.dp)
                    ,
                    scrollState = scrollState,
            ) {

                // restore scroll position after rotation
                scrollState.scrollTo(scrollPosition)

                for(category in categories){
                    FoodCategoryChip(
                            category = category.value,
                            isSelected = selectedCategory == category,
                            onSelectedCategoryChanged = {
                                onChangeScrollPosition(scrollState.value)
                                onSelectedCategoryChanged(it)
                            },
                            onExecuteSearch = {
                                onExecuteSearch()
                            },
                    )
                }
            }
        }
    }
}