import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import model.controls.BooleanControl.Companion.rememberBooleanControl
import model.controls.ColorControl.Companion.rememberColorControl
import model.controls.StringControl.Companion.rememberStringControl
import model.storybook
import ui.Storybook


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
                val buttonText = rememberStringControl("Button Text", "Enabled")
                val isEnabled = rememberBooleanControl("Enabled", false)
                val backgroundColor = rememberColorControl("Background color", MaterialTheme.colors.primary)
                Button(
                    onClick = { reportAction("Click") },
                    modifier = it,
                    enabled = isEnabled.value,
                    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor.value)
                ) {
                    Text(buttonText.value)
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
