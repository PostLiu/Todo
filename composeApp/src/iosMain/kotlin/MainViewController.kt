import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import theme.TodoMaterialTheme

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        TodoMaterialTheme {
            App()
        }
    }
}
