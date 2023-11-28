package platform

import androidx.core.view.WindowCompat
import org.postliu.todo.utils.ContextUtils

actual fun setStatusBarColor(color: Int, isDark: Boolean) {
    val window = ContextUtils.instance.window
    window.statusBarColor = color
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = !isDark
    }
}