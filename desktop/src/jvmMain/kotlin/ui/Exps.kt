package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StoryPreview(storyG: StoryG) {
    Box(Modifier.fillMaxSize()) {
        storyG.ui(storyG, Modifier.align(Alignment.Center))
        Row(Modifier.align(Alignment.BottomCenter)) {
            Column {
                storyG.controls.value.forEach {
                    ControlItem(it)
                }
            }
            ActionList(storyG)
        }
    }
}

@Composable
fun ControlItem(control: Control) {
    Column {
        Text(control.label)
        TextField(
            value = control.value.value,
            onValueChange = {
                control.value.value = it
            }
        )
    }
}

@Composable
fun ActionList(storyG: StoryG) {
    LazyColumn(Modifier.height(200.dp)) {
        items(storyG.actions) { item ->
            Text(item)
        }
    }

}

open class StoryG(
    val group: String,
    val label: String,
    var ui: @Composable StoryG.(Modifier) -> Unit = {}
) {
    val ctr = mutableStateMapOf<String, Control>()
    val controls = derivedStateOf { ctr.values }
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