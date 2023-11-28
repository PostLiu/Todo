package org.postliu.todo

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        setContent {
            TodoMaterialTheme {
                val color = TodoMaterialTheme.colors.primary.toArgb()
                SideEffect {
                    window.statusBarColor = color
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars = Color.luminance(color) > 0.5f
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    App()
                }
            }
        }
    }
}