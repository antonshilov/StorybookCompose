import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import com.antonshilov.storybook.App
import com.antonshilov.storybook.ui.devicepreview.DevicePreviewDecorator
import com.antonshilov.storybook.ui.devicepreview.DeviceSpec


fun main() = Window("Storybook") {
    DesktopMaterialTheme {
        App(
            listOf(
                DevicePreviewDecorator(DeviceSpec.All)
            )
        )
    }
}
