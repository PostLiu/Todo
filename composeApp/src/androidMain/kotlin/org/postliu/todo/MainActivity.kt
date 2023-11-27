package org.postliu.todo

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import org.postliu.todo.utils.ContextUtils
import theme.TodoMaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextUtils.instance.initWindow(window = window)
        setContent {
            TodoMaterialTheme {
                val color = MaterialTheme.colors.onPrimary.toArgb()
                SideEffect {
                    window.statusBarColor = color
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars = Color.luminance(color) > 0.5f
                    }
                }
                Surface(
                    modifier = Modifier
                        .systemBarsPadding()
                        .navigationBarsPadding()
                ) {
                    App()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    TodoMaterialTheme {
        App()
    }
}