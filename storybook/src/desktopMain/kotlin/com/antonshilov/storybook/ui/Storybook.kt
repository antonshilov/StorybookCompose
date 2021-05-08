package com.antonshilov.storybook.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.antonshilov.storybook.model.Story
import com.antonshilov.storybook.model.Storybook
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
actual fun Storybook(storybook: Storybook) {
    val state = rememberSplitPaneState()
    val selected = remember(storybook) { mutableStateOf<Story>(storybook.groups.first().stories.first()) }
    HorizontalSplitPane(splitPaneState = state) {
        first(200.dp) {
            GroupList(storybook, selected)
        }
        second(50.dp) {
            StoryPreview(selected.value)
        }
    }
}