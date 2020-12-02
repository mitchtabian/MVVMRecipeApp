package com.codingwithmitch.mvvmrecipeapp.presentation.components.util

data class GenericDialogInfo(
        val onDismiss: () -> Unit,
        val title: String,
        val description: String? = null,
        val positiveBtnTxt: String? = null,
        val onPositiveAction: () -> Unit,
        val negatveBtnTxt: String? = null,
        val onNegativeAction: () -> Unit,
)