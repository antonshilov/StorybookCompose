import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.singleWindowApplication
import com.antonshilov.storybook.App
import com.antonshilov.storybook.ui.devicepreview.DevicePreviewDecorator
import com.antonshilov.storybook.ui.devicepreview.DeviceSpec


fun main() = singleWindowApplication {
    MaterialTheme {
        App(
            listOf(
                DevicePreviewDecorator(DeviceSpec.All)
            )
        )
    }
}
