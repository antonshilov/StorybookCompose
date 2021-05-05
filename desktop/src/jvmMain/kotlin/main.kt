import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ui.Storybook
import ui.storybook

fun main() = Window {
    DesktopMaterialTheme {
        Panes()
    }
}

@Composable
fun Panes() {
    val storyBook = storybook {
        group("Button") {
            story("Enabled") {
                val buttonText = remember { addControl("Button Text", "Enabled") }
                val isEnabled = remember { addControl("Enabled", false) }
                Button(
                    onClick = { reportAction("Click") },
                    modifier = it,
                    enabled = isEnabled.value.value
                ) {
                    Text(buttonText.value.value)
                }
            }
            story("Disabled") {
                Button({ reportAction("Click") }, it, enabled = false) {
                    Text("Disabled")
                }
            }
        }

        group("Text") {
            story("Caption") {
                Text("Caption", it, style = MaterialTheme.typography.caption)
            }
        }
    }
    Storybook(storyBook)
}
