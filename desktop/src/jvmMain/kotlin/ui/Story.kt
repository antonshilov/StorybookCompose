package ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

open class Story(
    val group: String,
    val label: String,
    var ui: @Composable Story.(Modifier) -> Unit = {}
) {
    val ctr = mutableStateMapOf<String, Control>()
    val controls = derivedStateOf { ctr.values.toList() }
    val actions = mutableStateListOf<String>()

    fun addControl(label: String, initialValue: String): Control =
        ctr.getOrPut(label) { Control(label, initialValue) }

    fun reportAction(action: String) {
        actions.add(action)
    }
}

class Control(val label: String, default: String) {
    val value = mutableStateOf<String>(default)
}

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