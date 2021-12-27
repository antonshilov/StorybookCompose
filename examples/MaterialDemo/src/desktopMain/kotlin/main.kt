import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.window.singleWindowApplication
import com.antonshilov.storybook.App
import com.antonshilov.storybook.ui.ListControl.Companion.rememberListControl
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
        ) {
            group("Desktop") {
                story("kek") {
                    val list = rememberListControl("Bar text", listOf("Yo", "Sup", "Huh"))
                    TopAppBar(
                        title = { Text(list.value) }
                    )
                }
            }
        }
    }
}
