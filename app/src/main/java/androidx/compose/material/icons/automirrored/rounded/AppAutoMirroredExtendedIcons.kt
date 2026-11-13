package androidx.compose.material.icons.automirrored.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.buildRoundedIcon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _arrowForwardIosAm: ImageVector? = null
val Icons.AutoMirrored.Rounded.ArrowForwardIos: ImageVector
    get() {
        _arrowForwardIosAm?.let { return it }
        val vec = buildRoundedIcon("AutoMirrored.Rounded.ArrowForwardIos", autoMirror = true) {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.38f, 21.01f)
                curveToRelative(0.49f, 0.49f, 1.28f, 0.49f, 1.77f, 0.0f)
                lineToRelative(8.31f, -8.31f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(9.15f, 2.98f)
                curveToRelative(-0.49f, -0.49f, -1.28f, -0.49f, -1.77f, 0.0f)
                reflectiveCurveToRelative(-0.49f, 1.28f, 0.0f, 1.77f)
                lineTo(14.62f, 12.0f)
                lineToRelative(-7.25f, 7.25f)
                curveToRelative(-0.48f, 0.48f, -0.48f, 1.28f, 0.01f, 1.76f)
                close()
            }
        }
        _arrowForwardIosAm = vec
        return vec
    }

