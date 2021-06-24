package com.antonshilov.storybook.ui.devicepreview

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antonshilov.storybook.model.Decorator
import java.lang.Float.min

class DevicePreviewDecorator(val devices: List<DeviceSpec>) : Decorator() {
    val deviceSpec = mutableStateOf<DeviceSpec>(devices.first())

    @Composable
    override fun decorate(content: @Composable () -> Unit) {
        if (deviceSpec.value != DeviceSpec.None) {
            BoxWithConstraints(
                Modifier.padding(32.dp).fillMaxSize()
            ) {
                val (_, width, height) = deviceSpec.value

                val widthScale = maxWidth / width
                val heightScale = maxHeight / height

                val min = min(widthScale, heightScale)
                val scale = if (min < 1f) {
                    min
                } else {
                    1f
                }

                Box(
                    Modifier.requiredSize(width = width, height = height)
                        .scale(scale)
                        .border(2.dp, Color.Red)
                        .align(Alignment.Center)
                        .animateContentSize()
                ) {
                    content()
                }
            }
        } else content()
    }

    @Composable
    override fun controlUi() {
        Selector(deviceSpec.value, devices) { deviceSpec.value = it }
    }


}

@Composable
private fun Selector(selectedDevice: DeviceSpec, devices: List<DeviceSpec>, onSelect: (DeviceSpec) -> Unit) {
    val isVisible = remember { mutableStateOf(false) }
    Text(selectedDevice.name, Modifier.clickable { isVisible.value = isVisible.value.not() })

    DropdownMenu(
        expanded = isVisible.value,
        onDismissRequest = { isVisible.value = false }
    ) {
        devices.forEach {
            DropdownMenuItem(
                onClick = {
                    onSelect(it)
                    isVisible.value = false
                }
            ) {
                Text(it.name)
            }
        }
    }
}
