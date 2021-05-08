package com.antonshilov.storybook.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

abstract class Control<T>(val label: String, initialValue: T) : MutableState<T> by mutableStateOf<T>(initialValue) {
    @Composable
    abstract fun ui()
}
typealias ControlType = Control<*>