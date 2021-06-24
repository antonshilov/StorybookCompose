package com.antonshilov.storybook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antonshilov.storybook.model.Decorator
import com.antonshilov.storybook.model.controls.BooleanControl.Companion.rememberBooleanControl
import com.antonshilov.storybook.model.controls.ColorControl.Companion.rememberColorControl
import com.antonshilov.storybook.model.controls.StringControl.Companion.rememberStringControl
import com.antonshilov.storybook.model.storybook
import com.antonshilov.storybook.ui.Storybook

@Composable
fun App(decorators: List<Decorator> = emptyList()) {
    MaterialTheme {
        Storybook(storyBook.apply {
            this.decorators = decorators
        })

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
        story("SC") {
            val title = rememberStringControl("Title", "Top App Bar")
            val backgroundColor = rememberColorControl("Background Color", MaterialTheme.colors.primarySurface)
            val contentColor =
                rememberColorControl("Content color", contentColorFor(MaterialTheme.colors.primarySurface))

            // Consider negative values to mean 'cut corner' and positive values to mean 'round corner'
            val fabShape = RoundedCornerShape(50)

            // Scaffold is a pre-defined composable that implements the basic material design visual
            // layout structure. It takes in child composables for all the common elements that you see
            // in an app using material design - app bar, bottom app bar, floating action button, etc. It
            // also takes care of laying out these child composables in the correct positions - eg bottom
            // app bar is automatically placed at the bottom of the screen even though I didn't specify
            // that explicitly.
            Scaffold(
                topBar = { TopAppBar(title = { Text("Scaffold Examples") }) },
                bottomBar = {
                    // We specify the shape of the FAB bu passing a shape composable (fabShape) as a
                    // parameter to cutoutShape property of the BottomAppBar. It automatically creates a
                    // cutout in the BottomAppBar based on the shape of the Floating Action Button.
                    BottomAppBar(cutoutShape = fabShape) {}
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        // We specify the same shape that we passed as the cutoutShape above.
                        shape = fabShape,
                        // We use the secondary color from the current theme. It uses the defaults when
                        // you don't specify a theme (this example doesn't specify a theme either hence
                        // it will just use defaults. Look at DarkModeActivity if you want to see an
                        // example of using themes.
                        backgroundColor = MaterialTheme.colors.secondary
                    ) {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Favorite, "")
                        }
                    }
                },
                isFloatingActionButtonDocked = true,
                content = { padding ->
                    LazyColumn(contentPadding = padding) {
                        items(100) {
                            // Card composable is a predefined composable that is meant to represent
                            // the card surface as specified by the Material Design specification. We
                            // also configure it to have rounded corners and apply a modifier.
                            Card(
                                backgroundColor = colors[it % colors.size],
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Spacer(modifier = Modifier.fillMaxWidth().height(200.dp))
                            }
                        }
                    }
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
val colors = listOf(
    Color(0xFFffd7d7.toInt()),
    Color(0xFFffe9d6.toInt()),
    Color(0xFFfffbd0.toInt()),
    Color(0xFFe3ffd9.toInt()),
    Color(0xFFd0fff8.toInt())
)