package com.codingwithmitch.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.lang.Exception

@Composable
fun GenericDialog(
        onDismiss: () -> Unit,
        title: String,
        description: String? = null,
        positiveBtnTxt: String? = null,
        onPositiveAction: () -> Unit,
        negatveBtnTxt: String? = null,
        onNegativeAction: () -> Unit,
        modifier: Modifier? = null,

        ){
    if(positiveBtnTxt == null && negatveBtnTxt == null){
        throw Exception("There must be at least one button on a dialog.")
    }
    AlertDialog(
            modifier = modifier?:Modifier.padding(8.dp)
            ,
            onDismissRequest = onDismiss,
            title = {
                Text(
                        text = title,
                )
            },
            text = {
                if(description != null){
                    Text(
                            text = description,
                    )
                }
            },
            buttons = {
                Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        horizontalArrangement = Arrangement.End,

                ){
                    if(positiveBtnTxt != null){
                        Button(
                                modifier = if(negatveBtnTxt != null) Modifier.padding(end=8.dp) else Modifier,
                                onClick = onPositiveAction,
                        ) {
                            Text(text = positiveBtnTxt)
                        }
                    }
                    if(negatveBtnTxt != null){
                        Button(
                                onClick = onNegativeAction
                        ) {
                            Text(text = negatveBtnTxt)
                        }
                    }
                }

            }
    )
}
