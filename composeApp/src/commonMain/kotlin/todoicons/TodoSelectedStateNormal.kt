package todoicons

import TodoIcons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val TodoIcons.TodoSelectedStateNormal: ImageVector
    get() {
        if (_todoSelectedStateNormal != null) {
            return _todoSelectedStateNormal!!
        }
        _todoSelectedStateNormal = Builder(name = "TodoSelectedStateNormal", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.0f, 840.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(120.0f, 760.0f)
                verticalLineToRelative(-560.0f)
                quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
                reflectiveQuadTo(200.0f, 120.0f)
                horizontalLineToRelative(560.0f)
                quadToRelative(33.0f, 0.0f, 56.5f, 23.5f)
                reflectiveQuadTo(840.0f, 200.0f)
                verticalLineToRelative(560.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(760.0f, 840.0f)
                lineTo(200.0f, 840.0f)
                close()
                moveTo(200.0f, 760.0f)
                horizontalLineToRelative(560.0f)
                verticalLineToRelative(-560.0f)
                lineTo(200.0f, 200.0f)
                verticalLineToRelative(560.0f)
                close()
            }
        }
        .build()
        return _todoSelectedStateNormal!!
    }

private var _todoSelectedStateNormal: ImageVector? = null
