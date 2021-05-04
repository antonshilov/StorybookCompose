package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun StoryPreview(storyG: StoryG) {
    val state = rememberSplitPaneState(initialPositionPercentage = 1f)

    VerticalSplitPane(splitPaneState = state) {
        first(300.dp) {
            Box(Modifier.fillMaxSize()) {
                storyG.ui(storyG, Modifier.align(Alignment.Center))
            }
        }
        second(150.dp) {
            ControlsAndActions(storyG)
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

@Composable
fun ControlsAndActions(storyG: StoryG) {
    val selectedTab = remember { mutableStateOf(0) }

    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTab.value) {
            Tab(selectedTab.value == 0, { selectedTab.value = 0 }) {
                Text("Actions")
            }
            Tab(selectedTab.value == 1, { selectedTab.value = 1 }) {
                Text("Controls")
            }
        }
        when (selectedTab.value) {
            0 -> {
                Column {
                    storyG.controls.value.forEach {
                        ControlItem(it)
                    }
                }
            }
            1 -> {
                ActionList(storyG)
            }
        }
    }
}