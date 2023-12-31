import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import theme.TodoMaterialTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "待办",
    ) {
        TodoMaterialTheme {
            App()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}