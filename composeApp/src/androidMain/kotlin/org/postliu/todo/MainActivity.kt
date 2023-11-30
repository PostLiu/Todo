package org.postliu.todo

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import org.postliu.todo.utils.ContextUtils
import theme.TodoMaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextUtils.instance.initWindow(window = window)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        setContent {
            TodoMaterialTheme {
                val statusBarColor = TodoMaterialTheme.colors.primary
                val statusBarIsLight = Color.luminance(statusBarColor.toArgb()) > 0.5f
                SideEffect {
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars = statusBarIsLight
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(statusBarColor)
                        .systemBarsPadding()
                ) {
                    App()
                }
            }
        }
    }
}