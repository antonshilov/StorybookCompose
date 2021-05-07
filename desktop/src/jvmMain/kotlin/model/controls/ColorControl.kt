package model.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Control
import model.Story

class ColorControl(label: String, initialValue: Color) : Control<Color>(label, initialValue) {
    @Composable
    override fun ui() {
        val colorText = remember { mutableStateOf(value.toHexString()) }
        Column {
            Text(label)
            Row {
                Box(Modifier.size(24.dp).background(value, CircleShape))
                TextField(
                    colorText.value,
                    onValueChange = {
                        colorText.value = it
                        it.toColor()?.let {
                            value = it
                        }
                    }
                )
            }
        }
    }

    private fun String.toColor(): Color? {
        val colorString = this
        return if (colorString.isNotBlank() && colorString[0] == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            var color = colorString.substring(1).toLongOrNull(16) ?: return null
            if (colorString.length == 7) {
                // Set the alpha value
                color = color or -0x1000000
            } else if (colorString.length != 9) {
                return null
            }
            Color(color)
        } else null
    }

    private fun Color.toHexString(): String {
        return "#${alpha.toHex()}${red.toHex()}${green.toHex()}${blue.toHex()}"
    }

    private fun Float.toHex(): String {
        val int = (this * 255f).toInt()
        return if (int > 9) {
            int.toString(16)
        } else "0${int.toString(16)}"
    }

    companion object {
        @Composable
        fun Story.rememberColorControl(label: String, initialValue: Color): ColorControl =
            remember { addControl(label, ColorControl(label, initialValue)) }
    }
}