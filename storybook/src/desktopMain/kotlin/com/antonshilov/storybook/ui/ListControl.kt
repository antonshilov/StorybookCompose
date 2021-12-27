package com.antonshilov.storybook.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.antonshilov.storybook.model.Control
import com.antonshilov.storybook.model.Story

class ListControl<L, T>(
    label: String,
    private val values: List<L>,
    private val mapper: (id: L) -> T,
) : Control<T>(label, mapper(values.first())) {

    @Composable
    override fun ui() {
        val isVisible = remember { mutableStateOf(false) }
        Text("$label: $value", Modifier.clickable { isVisible.value = isVisible.value.not() })

        DropdownMenu(
            expanded = isVisible.value,
            onDismissRequest = { isVisible.value = false }
        ) {
            values.forEach {
                DropdownMenuItem(
                    onClick = {
                        value = mapper(it)
                        isVisible.value = false
                    }
                ) {
                    Text(it.toString())
                }
            }
        }
    }

    companion object {
        @Composable
        fun Story.rememberListControl(label: String, values: List<String>): ListControl<String, String> =
            remember { addControl(label, ListControl(label, values) { it }) }
    }
}