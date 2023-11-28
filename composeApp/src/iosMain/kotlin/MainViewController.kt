import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import platform.setStatusBarColor
import theme.TodoMaterialTheme

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        TodoMaterialTheme {
            val color = TodoMaterialTheme.colors.primary.toArgb()
            val isDark = TodoMaterialTheme.colors.isDark
            SideEffect {
                setStatusBarColor(color = color, isDark = isDark)
            }
            App()
        }
    }
}