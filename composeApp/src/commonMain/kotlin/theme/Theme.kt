package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val todoLightColors = TodoColors(
    isDark = false
)
private val todoNightColors = TodoColors(
    primary = SecondaryColor,
    onPrimary = OnSecondaryColor,
    secondary = SecondaryColor,
    onSecondary = Color.White,
    background = Color(0XFF3F3F3F),
    onBackground = Color.White,
    textPrimary = Color.White,
    textSecondary = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    isDark = true
)

/**
 * 自定义主题色
 *
 * @constructor
 *
 * @param primary 主色调
 * @param onPrimary 显示在主色调上的色调
 * @param secondary 次色调
 * @param onSecondary 显示在次色调上的色调
 * @param textPrimary 文本主色调
 * @param textSecondary 文本次色调
 * @param background 整体背景色调
 * @param onBackground 显示在整体背景上的色调
 * @param surface 表面色调
 * @param onSurface 在表面色调上的颜色
 * @param isDark 是否是暗黑模式
 */
@Stable
class TodoColors(
    primary: Color = PrimaryColor,
    onPrimary: Color = OnPrimaryColor,
    secondary: Color = SecondaryColor,
    onSecondary: Color = OnSecondaryColor,
    textPrimary: Color = TextPrimaryColor,
    textSecondary: Color = TextSecondaryColor,
    background: Color = BackgroundColor,
    onBackground: Color = OnBackgroundColor,
    surface: Color = SurfaceColor,
    onSurface: Color = OnSurfaceColor,
    isDark: Boolean = false
) {

    var primary by mutableStateOf(primary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var background by mutableStateOf(background)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: TodoColors) {
        primary = other.primary
        onPrimary = other.onPrimary
        secondary = other.secondary
        onSecondary = other.onSecondary
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        background = other.background
        onBackground = other.onBackground
        surface = other.surface
        onSurface = other.onSurface
        isDark = other.isDark
    }

    fun copy(): TodoColors = TodoColors(
        primary = primary,
        onPrimary = onPrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        isDark = isDark
    )
}

@Composable
fun ProvideTodoColors(colors: TodoColors, content: @Composable () -> Unit) {
    val colorPalette = remember { colors.copy() }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalTodoColors provides colorPalette, content = content)
}

private val LocalTodoColors =
    staticCompositionLocalOf<TodoColors> { error("No TodoColors provided") }

object TodoMaterialTheme {
    val colors: TodoColors
        @Composable @ReadOnlyComposable get() = LocalTodoColors.current
}

@Composable
fun TodoMaterialTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDark) {
        todoNightColors
    } else {
        todoLightColors
    }
    ProvideTodoColors(colors) {
        MaterialTheme(
            colors = defaultColors(isDark, colors),
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

fun defaultColors(isDark: Boolean, todoColors: TodoColors) = if (isDark) darkColors(
    primary = todoColors.primary,
    onPrimary = todoColors.onPrimary,
    secondary = todoColors.secondary,
    onSecondary = todoColors.onSecondary,
    background = todoColors.background,
    onBackground = todoColors.onBackground,
    surface = todoColors.surface,
    onSurface = todoColors.onSurface,
) else lightColors(
    primary = todoColors.primary,
    onPrimary = todoColors.onPrimary,
    secondary = todoColors.secondary,
    onSecondary = todoColors.onSecondary,
    background = todoColors.background,
    onBackground = todoColors.onBackground,
    surface = todoColors.surface,
    onSurface = todoColors.onSurface,
)