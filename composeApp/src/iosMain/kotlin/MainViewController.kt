import androidx.compose.ui.window.ComposeUIViewController
import theme.TodoMaterialTheme

fun MainViewController() = ComposeUIViewController { TodoMaterialTheme { App() } }
