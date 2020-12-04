package com.codingwithmitch.mvvmrecipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Adb
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            ListItem(
                    modifier = Modifier
                            .height(100.dp)
                            .border(border = BorderStroke(1.dp, Color.Black))
                    ,
                    icon = {
                        Icon(
                                Icons.Filled.Adb,
                                modifier = Modifier
                                        .border(border = BorderStroke(1.dp, Color.Black))
                                )
                    },
                    trailing = {
                        IconButton(
                                onClick = {},
                                modifier = Modifier
                                        .border(border = BorderStroke(1.dp, Color.Black))
                        ) {
                            Icon(Icons.Filled.AccessTime)
                        }

                    },
                    overlineText = {
                        Text(
                                text = "Overline text",
                                modifier = Modifier
                                        .border(border = BorderStroke(1.dp, Color.Black))
                        )
                    },
                    secondaryText = {
                        Text(
                                text = "Secondary text",
                                modifier = Modifier
                                        .border(border = BorderStroke(1.dp, Color.Black))
                        )
                    }
            ) {
                Text(
                        "Big Title Text",
                        modifier = Modifier
                                .border(border = BorderStroke(1.dp, Color.Black))
                )
            }
        }
    }
}






















