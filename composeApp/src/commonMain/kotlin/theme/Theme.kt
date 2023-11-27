package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

private val lightColors = lightColors(
    primary = Color(0XFF389133),
    onPrimary = Color.White,
    background = Color(0xFFF2F2F2),
    onBackground = Color.White,
    secondary = Color(0XFF389133),
    onSecondary = Color.White,
    primaryVariant = Color(0XFF389133),
    secondaryVariant = Color(0XFF389133),
    onSurface = Color.Black,
)
private val darkColors = darkColors(
    primary = Color(0XFF389133),
    onPrimary = Color.White,
    background = Color(0xFFF2F2F2),
    onBackground = Color.White,
    secondary = Color(0XFF389133),
    onSecondary = Color.White,
    primaryVariant = Color(0XFF389133),
    secondaryVariant = Color(0XFF389133),
    onSurface = Color.Black,
)

object TodoMaterialTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) darkColors else lightColors
}

@Composable
fun TodoMaterialTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDark) {
        darkColors
    } else {
        lightColors
    }
    MaterialTheme(
        colors = colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}