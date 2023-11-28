package org.postliu.todo

import App
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
import platform.setStatusBarColor
import theme.TodoMaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextUtils.instance.initWindow(window = window)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TodoMaterialTheme {
                val color = TodoMaterialTheme.colors.primary.toArgb()
                val isDark = TodoMaterialTheme.colors.isDark
                SideEffect {
                    setStatusBarColor(color = color, isDark = isDark)
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