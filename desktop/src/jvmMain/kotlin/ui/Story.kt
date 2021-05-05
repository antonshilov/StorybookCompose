package ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

open class Story(
    val group: String,
    val label: String,
    var ui: @Composable Story.(Modifier) -> Unit = {}
) {
    val ctr = mutableStateMapOf<String, Control<*>>()
    val controls = derivedStateOf { ctr.values.toList() }
    val actions = mutableStateListOf<String>()

    fun addControl(label: String, initialValue: String): StringControl {
        val existingControl = ctr[label]
        return if (existingControl != null && existingControl is StringControl) {
            existingControl
        } else {
            val key = StringControl(label, initialValue)
            ctr[label] = key
            key
        }
    }

    fun addControl(label: String, initialValue: Boolean): BooleanControl {
        val existingControl = ctr[label]
        return if (existingControl != null && existingControl is BooleanControl) {
            existingControl
        } else {
            val key = BooleanControl(label, initialValue)
            ctr[label] = key
            key
        }
    }

    fun reportAction(action: String) {
        actions.add(action)
    }
}

abstract class Control<T>(val label: String, initialValue: T) {
    val value = mutableStateOf<T>(initialValue)
}
typealias ControlType = Control<*>

class StringControl(label: String, initialValue: String) : Control<String>(label, initialValue)
class BooleanControl(label: String, initialValue: Boolean) : Control<Boolean>(label, initialValue)

fun storybook(block: Storybook.() -> Unit): Storybook {
    val storybook = Storybook()
    storybook.block()
    return storybook
}

class Storybook {
    val groups: MutableList<StoryGroup> = mutableListOf()
    fun group(label: String, block: StoryGroup.() -> Unit) {
        val group = StoryGroup(label)
        group.block()
        groups.add(group)
    }
}

class StoryGroup(val label: String) {
    val stories: MutableList<Story> = mutableListOf()

    fun story(storyLabel: String, ui: @Composable Story.(Modifier) -> Unit) {
        stories.add(Story(group = this.label, label = storyLabel, ui))
    }

}