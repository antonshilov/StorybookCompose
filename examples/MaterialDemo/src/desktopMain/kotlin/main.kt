import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import com.antonshilov.storybook.App


fun main() = Window("Storybook") {
    DesktopMaterialTheme {
        App()
    }
}
