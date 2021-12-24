package com.antonshilov.storybook.ui.decorators

import androidx.compose.foundation.clickable
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.antonshilov.storybook.model.Decorator

class DensityDecorator(
    private val densityScales: List<Float> = listOf(0.75f, 1f, 1.25f, 1.5f, 2f),
    private val fontScales: List<Float> = listOf(0.75f, 1f, 1.25f, 1.5f, 2f)
) : Decorator() {
    private val densityScale = mutableStateOf(1f)
    private val fontScale = mutableStateOf(1f)

    @Composable
    override fun decorate(content: @Composable () -> Unit) {
        val density = LocalDensity.current
        val customDensity = remember(densityScale.value, fontScale.value) {
            Density(
                fontScale = density.fontScale * fontScale.value,
                density = density.density * densityScale.value
            )
        }
        CompositionLocalProvider(
            LocalDensity provides customDensity
        ) {
            content()
        }
    }

    @Composable
    override fun controlUi() {
        Selector("Density", densityScale.value, densityScales) { densityScale.value = it }
        Selector("Font", fontScale.value, fontScales) { fontScale.value = it }
    }
}

@Composable
private fun Selector(label: String, selectedScale: Float, scales: List<Float>, onSelect: (Float) -> Unit) {
    val isVisible = remember { mutableStateOf(false) }
    Text("$label: x${"%.2f".format(selectedScale)}", Modifier.clickable { isVisible.value = isVisible.value.not() })

    DropdownMenu(
        expanded = isVisible.value,
        onDismissRequest = { isVisible.value = false }
    ) {
        scales.forEach {
            DropdownMenuItem(
                onClick = {
                    onSelect(it)
                    isVisible.value = false
                }
            ) {
                Text("x${"%.2f".format(it)}")
            }
        }
    }
}

