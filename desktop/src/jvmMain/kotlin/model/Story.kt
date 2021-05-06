package model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier

open class Story(
    val group: String,
    val label: String,
    var ui: @Composable Story.(Modifier) -> Unit = {}
) {
    val controlsCache = mutableStateMapOf<String, Control<*>>()
    val controls = derivedStateOf { controlsCache.values.toList() }
    val actions = mutableStateListOf<String>()

    inline fun <reified T : ControlType> addControl(label: String, control: T): T {
        val existingControl = controlsCache[label]
        return if (existingControl != null && existingControl is T) {
            existingControl
        } else {
            controlsCache[label] = control
            control
        }
    }

    fun reportAction(action: String) {
        actions.add(action)
    }
}