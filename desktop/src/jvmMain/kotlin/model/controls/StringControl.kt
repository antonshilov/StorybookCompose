package model.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import model.Control
import model.Story

class StringControl(label: String, initialValue: String) : Control<String>(label, initialValue) {
    @Composable
    override fun ui() {
        Column {
            Text(label)
            TextField(
                value = value,
                onValueChange = { value = it }
            )
        }
    }

    companion object {
        @Composable
        fun Story.rememberStringControl(label: String, initialValue: String): StringControl =
            remember { addControl(label, StringControl(label, initialValue)) }
    }
}