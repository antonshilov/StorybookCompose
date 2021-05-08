package com.antonshilov.storybook.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class StoryGroup(val label: String) {
    val stories: MutableList<Story> = mutableListOf()

    fun story(storyLabel: String, ui: @Composable Story.(Modifier) -> Unit) {
        stories.add(Story(group = this.label, label = storyLabel, ui))
    }
}