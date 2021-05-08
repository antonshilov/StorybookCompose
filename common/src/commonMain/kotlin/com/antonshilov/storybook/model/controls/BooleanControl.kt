package com.antonshilov.storybook.model.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.antonshilov.storybook.model.Control
import com.antonshilov.storybook.model.Story

class BooleanControl(label: String, initialValue: Boolean) : Control<Boolean>(label, initialValue) {
    @Composable
    override fun ui() {
        Column {
            Text(label)
            Checkbox(
                checked = value,
                onCheckedChange = { value = it }
            )
        }
    }

    companion object {
        @Composable
        fun Story.rememberBooleanControl(label: String, initialValue: Boolean): BooleanControl =
            remember { addControl(label, BooleanControl(label, initialValue)) }
    }
}