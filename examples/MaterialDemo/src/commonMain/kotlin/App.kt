package com.antonshilov.storybook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antonshilov.storybook.model.controls.BooleanControl.Companion.rememberBooleanControl
import com.antonshilov.storybook.model.controls.ColorControl.Companion.rememberColorControl
import com.antonshilov.storybook.model.controls.StringControl.Companion.rememberStringControl
import com.antonshilov.storybook.model.storybook
import com.antonshilov.storybook.ui.Storybook

@Composable
fun App() {
    MaterialTheme {
        Storybook(storyBook)

    }
}

private val storyBook = storybook {
    group("Button") {
        story("Playground") {
            val buttonText = rememberStringControl("Button Text", "Enabled")
            val isEnabled = rememberBooleanControl("Enabled", true)
            val color = rememberColorControl("Color", MaterialTheme.colors.primary)
            val content: @Composable RowScope.() -> Unit = { Text(buttonText.value) }
            val onClick = { reportAction("Click") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onClick,
                    modifier = it,
                    enabled = isEnabled.value,
                    colors = ButtonDefaults.buttonColors(backgroundColor = color.value),
                    content = content
                )
                OutlinedButton(
                    onClick = onClick,
                    modifier = it,
                    enabled = isEnabled.value,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = color.value),
                    content = content
                )
                TextButton(
                    onClick = onClick,
                    modifier = it,
                    enabled = isEnabled.value,
                    colors = ButtonDefaults.textButtonColors(contentColor = color.value),
                    content = content
                )
            }
        }
        story("Disabled") {
            Button({ reportAction("Click") }, it, enabled = false) {
                Text("Disabled")
            }
        }
    }

    group("App bar") {
        story("Top App Bar") {
            val title = rememberStringControl("Title", "Top App Bar")
            val backgroundColor = rememberColorControl("Background Color", MaterialTheme.colors.primarySurface)
            val contentColor =
                rememberColorControl("Content color", contentColorFor(MaterialTheme.colors.primarySurface))
            TopAppBar(
                modifier = it,
                title = { Text(title.value) },
                backgroundColor = backgroundColor.value,
                contentColor = contentColor.value,
                navigationIcon = {
                    Icon(Icons.Default.Call, contentDescription = null)
                },
                actions = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )
        }
        story("Bottom App Bar") {
            val content = rememberStringControl("Content", "Bottom App Bar")
            val backgroundColor = rememberColorControl("Background Color", MaterialTheme.colors.primarySurface)
            val contentColor =
                rememberColorControl("Content color", contentColorFor(MaterialTheme.colors.primarySurface))
            BottomAppBar(
                modifier = it,
                backgroundColor = backgroundColor.value,
                contentColor = contentColor.value
            ) {
                Text(content.value)
            }
        }
        story("Bottom Navigation Bar") {
            val label = rememberStringControl("Label", "Label")
            val backgroundColor = rememberColorControl("Background Color", MaterialTheme.colors.primarySurface)
            val contentColor =
                rememberColorControl("Content color", contentColorFor(MaterialTheme.colors.primarySurface))
            val selectedIndex = remember { mutableStateOf(0) }
            BottomNavigation(
                modifier = it,
                backgroundColor = backgroundColor.value,
                contentColor = contentColor.value
            ) {
                repeat(4) { index ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Home, null) },
                        selected = selectedIndex.value == index,
                        onClick = {
                            reportAction("Selected $index")
                            selectedIndex.value = index
                        },
                        label = { Text(label.value) }
                    )
                }
            }
        }
    }
}