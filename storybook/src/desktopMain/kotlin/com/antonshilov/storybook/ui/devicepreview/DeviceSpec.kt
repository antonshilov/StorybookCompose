package com.antonshilov.storybook.ui.devicepreview

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DeviceSpec(val name: String, val width: Dp, val height: Dp) {
    companion object {
        val Pixel4 = DeviceSpec("Pixel 4", 412.dp, 869.dp)
        val GalaxyS9 = DeviceSpec("S9", 360.dp, 740.dp)
        val Pixel = DeviceSpec("Pixel", 412.dp, 732.dp)
        val PixelC = DeviceSpec("Pixel C", 1280.dp, 900.dp)
        val Iphone5 = DeviceSpec("Iphone 5", 320.dp, 568.dp)
        val None = DeviceSpec("-", 0.dp, 0.dp)
        val All = listOf(None, Pixel, Pixel4, GalaxyS9, PixelC, Iphone5)
    }
}
