package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingwithmitch.openchat.common.framework.presentation.theme.Grey4

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onError: (String) -> Unit,
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    // for testing, force an error
                    if(category == "Milk"){
                        onError("Invalid category")
                    }
                    else{
                        onSelectedCategoryChanged(category)
                        onExecuteSearch()
                    }
                }
            )
        ) {
            Surface(
                color = if(isSelected) Grey4 else MaterialTheme.colors.primary,
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}