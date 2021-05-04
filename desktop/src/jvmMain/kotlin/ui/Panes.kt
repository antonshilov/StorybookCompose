package ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun Panes() {
    val state = rememberSplitPaneState()
    val stories =
        listOf(
            StoryG("Button", "Enabled") {
                val buttonText = remember { addControl("Button Text", "Enabled") }
                Button({
                    reportAction("Click")
                }, it) {
                    Text(buttonText.value.value)
                }
            },
            StoryG("Button", "Disabled") {
                Button({}, it, enabled = false) {
                    Text("Disabled")
                }
            },
            StoryG("Text", "Caption") {
                Text("Caption", it, style = MaterialTheme.typography.caption)
            }

        )
    val groups = stories.groupBy { it.group }

    var selectedId by remember { mutableStateOf("Enabled") }
    HorizontalSplitPane(splitPaneState = state) {
        first(200.dp) {
            LazyColumn {
                groups.forEach { (group, stories) ->
                    item(group) { GroupItem(group) }
                    stories.forEach { story ->
                        item(story.label) {
                            StoryItem(story.label, story.label == selectedId) {
                                selectedId = story.label
                            }
                        }
                    }
                }
            }
        }
        second(50.dp) {
            StoryPreview(stories.first { it.label == selectedId })
        }
    }
}