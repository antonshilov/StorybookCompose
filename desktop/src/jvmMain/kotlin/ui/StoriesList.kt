package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Components(groups: List<Group>, selectedLabel: String, onStoryClicked: (Story) -> Unit) {
    LazyColumn {
        groups.forEach { group ->
            item(group.label) { GroupItem(group.label) }
            group.items.forEach { story ->
                item(story) { StoryItem(story.label, story.label == selectedLabel) { onStoryClicked(story) } }
            }
        }
    }
}

@Composable
fun GroupItem(label: String) {
    val style = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )

    Text(
        text = label,
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .hoverBackground()
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp
            )
    )
}

@Composable
fun StoryItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val style = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )

    Text(
        text = label,
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color.Blue.copy(alpha = 0.2f) else Color.Transparent)
            .hoverBackground()
            .padding(
                vertical = 2.dp
            )
            .padding(start = 12.dp, end = 4.dp)
    )
}

fun Modifier.hoverBackground(): Modifier =
    composed {
        var isHovered by remember { mutableStateOf(false) }
        val color by derivedStateOf {
            if (isHovered)
                Color.Black.copy(alpha = 0.1f)
            else
                Color.Transparent
        }

        this.background(color)
            .pointerMoveFilter(
                onEnter = {
                    isHovered = true
                    false
                },
                onExit = {
                    isHovered = false
                    false
                }
            )
    }

data class Group(val label: String, val items: List<Story>)
data class Story(val label: String)