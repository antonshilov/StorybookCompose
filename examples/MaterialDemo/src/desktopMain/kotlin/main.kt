import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.singleWindowApplication
import com.antonshilov.storybook.App
import com.antonshilov.storybook.ui.decorators.DensityDecorator
import com.antonshilov.storybook.ui.decorators.devicepreview.DevicePreviewDecorator
import com.antonshilov.storybook.ui.decorators.devicepreview.DeviceSpec


fun main() = singleWindowApplication {
    MaterialTheme {
        App(
            listOf(
                DevicePreviewDecorator(DeviceSpec.All),
                DensityDecorator()
            )
        )
    }
}
