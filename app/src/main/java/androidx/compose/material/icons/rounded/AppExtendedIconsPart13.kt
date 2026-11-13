package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _volumeDown: ImageVector? = null
val Icons.Rounded.VolumeDown: ImageVector
    get() {
        _volumeDown?.let { return it }
        val vec = buildRoundedIcon("Rounded.VolumeDown") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.5f, 12.0f)
                curveToRelative(0.0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
                verticalLineToRelative(8.05f)
                curveToRelative(1.48f, -0.73f, 2.5f, -2.25f, 2.5f, -4.02f)
                close()
                moveTo(5.0f, 10.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                lineToRelative(3.29f, 3.29f)
                curveToRelative(0.63f, 0.63f, 1.71f, 0.18f, 1.71f, -0.71f)
                verticalLineTo(6.41f)
                curveToRelative(0.0f, -0.89f, -1.08f, -1.34f, -1.71f, -0.71f)
                lineTo(9.0f, 9.0f)
                horizontalLineTo(6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _volumeDown = vec
        return vec
    }

private var _volumeMute: ImageVector? = null
val Icons.Rounded.VolumeMute: ImageVector
    get() {
        _volumeMute?.let { return it }
        val vec = buildRoundedIcon("Rounded.VolumeMute") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.0f, 10.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                lineToRelative(3.29f, 3.29f)
                curveToRelative(0.63f, 0.63f, 1.71f, 0.18f, 1.71f, -0.71f)
                verticalLineTo(6.41f)
                curveToRelative(0.0f, -0.89f, -1.08f, -1.34f, -1.71f, -0.71f)
                lineTo(11.0f, 9.0f)
                horizontalLineTo(8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _volumeMute = vec
        return vec
    }

private var _volumeOff: ImageVector? = null
val Icons.Rounded.VolumeOff: ImageVector
    get() {
        _volumeOff?.let { return it }
        val vec = buildRoundedIcon("Rounded.VolumeOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.63f, 3.63f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineTo(7.29f, 8.7f)
                lineTo(7.0f, 9.0f)
                lineTo(4.0f, 9.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                lineToRelative(3.29f, 3.29f)
                curveToRelative(0.63f, 0.63f, 1.71f, 0.18f, 1.71f, -0.71f)
                verticalLineToRelative(-4.17f)
                lineToRelative(4.18f, 4.18f)
                curveToRelative(-0.49f, 0.37f, -1.02f, 0.68f, -1.6f, 0.91f)
                curveToRelative(-0.36f, 0.15f, -0.58f, 0.53f, -0.58f, 0.92f)
                curveToRelative(0.0f, 0.72f, 0.73f, 1.18f, 1.39f, 0.91f)
                curveToRelative(0.8f, -0.33f, 1.55f, -0.77f, 2.22f, -1.31f)
                lineToRelative(1.34f, 1.34f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(5.05f, 3.63f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.42f, 0.0f)
                close()
                moveTo(19.0f, 12.0f)
                curveToRelative(0.0f, 0.82f, -0.15f, 1.61f, -0.41f, 2.34f)
                lineToRelative(1.53f, 1.53f)
                curveToRelative(0.56f, -1.17f, 0.88f, -2.48f, 0.88f, -3.87f)
                curveToRelative(0.0f, -3.83f, -2.4f, -7.11f, -5.78f, -8.4f)
                curveToRelative(-0.59f, -0.23f, -1.22f, 0.23f, -1.22f, 0.86f)
                verticalLineToRelative(0.19f)
                curveToRelative(0.0f, 0.38f, 0.25f, 0.71f, 0.61f, 0.85f)
                curveTo(17.18f, 6.54f, 19.0f, 9.06f, 19.0f, 12.0f)
                close()
                moveTo(10.29f, 5.71f)
                lineToRelative(-0.17f, 0.17f)
                lineTo(12.0f, 7.76f)
                lineTo(12.0f, 6.41f)
                curveToRelative(0.0f, -0.89f, -1.08f, -1.33f, -1.71f, -0.7f)
                close()
                moveTo(16.5f, 12.0f)
                curveToRelative(0.0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
                verticalLineToRelative(1.79f)
                lineToRelative(2.48f, 2.48f)
                curveToRelative(0.01f, -0.08f, 0.02f, -0.16f, 0.02f, -0.24f)
                close()
            }
        }
        _volumeOff = vec
        return vec
    }

private var _volumeUp: ImageVector? = null
val Icons.Rounded.VolumeUp: ImageVector
    get() {
        _volumeUp?.let { return it }
        val vec = buildRoundedIcon("Rounded.VolumeUp") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 10.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                lineToRelative(3.29f, 3.29f)
                curveToRelative(0.63f, 0.63f, 1.71f, 0.18f, 1.71f, -0.71f)
                lineTo(12.0f, 6.41f)
                curveToRelative(0.0f, -0.89f, -1.08f, -1.34f, -1.71f, -0.71f)
                lineTo(7.0f, 9.0f)
                lineTo(4.0f, 9.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
                moveTo(16.5f, 12.0f)
                curveToRelative(0.0f, -1.77f, -1.02f, -3.29f, -2.5f, -4.03f)
                verticalLineToRelative(8.05f)
                curveToRelative(1.48f, -0.73f, 2.5f, -2.25f, 2.5f, -4.02f)
                close()
                moveTo(14.0f, 4.45f)
                verticalLineToRelative(0.2f)
                curveToRelative(0.0f, 0.38f, 0.25f, 0.71f, 0.6f, 0.85f)
                curveTo(17.18f, 6.53f, 19.0f, 9.06f, 19.0f, 12.0f)
                reflectiveCurveToRelative(-1.82f, 5.47f, -4.4f, 6.5f)
                curveToRelative(-0.36f, 0.14f, -0.6f, 0.47f, -0.6f, 0.85f)
                verticalLineToRelative(0.2f)
                curveToRelative(0.0f, 0.63f, 0.63f, 1.07f, 1.21f, 0.85f)
                curveTo(18.6f, 19.11f, 21.0f, 15.84f, 21.0f, 12.0f)
                reflectiveCurveToRelative(-2.4f, -7.11f, -5.79f, -8.4f)
                curveToRelative(-0.58f, -0.23f, -1.21f, 0.22f, -1.21f, 0.85f)
                close()
            }
        }
        _volumeUp = vec
        return vec
    }

private var _warningAmber: ImageVector? = null
val Icons.Rounded.WarningAmber: ImageVector
    get() {
        _warningAmber?.let { return it }
        val vec = buildRoundedIcon("Rounded.WarningAmber") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 5.99f)
                lineTo(19.53f, 19.0f)
                lineTo(4.47f, 19.0f)
                lineTo(12.0f, 5.99f)
                moveTo(2.74f, 18.0f)
                curveToRelative(-0.77f, 1.33f, 0.19f, 3.0f, 1.73f, 3.0f)
                horizontalLineToRelative(15.06f)
                curveToRelative(1.54f, 0.0f, 2.5f, -1.67f, 1.73f, -3.0f)
                lineTo(13.73f, 4.99f)
                curveToRelative(-0.77f, -1.33f, -2.69f, -1.33f, -3.46f, 0.0f)
                lineTo(2.74f, 18.0f)
                close()
                moveTo(11.0f, 11.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                close()
                moveTo(11.0f, 16.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }
        _warningAmber = vec
        return vec
    }

private var _web: ImageVector? = null
val Icons.Rounded.Web: ImageVector
    get() {
        _web?.let { return it }
        val vec = buildRoundedIcon("Rounded.Web") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 4.0f)
                lineTo(4.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(4.0f, 9.0f)
                horizontalLineToRelative(10.5f)
                verticalLineToRelative(3.5f)
                lineTo(4.0f, 12.5f)
                lineTo(4.0f, 9.0f)
                close()
                moveTo(4.0f, 14.5f)
                horizontalLineToRelative(10.5f)
                lineTo(14.5f, 18.0f)
                lineTo(5.0f, 18.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-2.5f)
                close()
                moveTo(19.0f, 18.0f)
                horizontalLineToRelative(-2.5f)
                lineTo(16.5f, 9.0f)
                lineTo(20.0f, 9.0f)
                verticalLineToRelative(8.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _web = vec
        return vec
    }

private var _wifi: ImageVector? = null
val Icons.Rounded.Wifi: ImageVector
    get() {
        _wifi?.let { return it }
        val vec = buildRoundedIcon("Rounded.Wifi") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(2.06f, 10.06f)
                curveToRelative(0.51f, 0.51f, 1.32f, 0.56f, 1.87f, 0.1f)
                curveToRelative(4.67f, -3.84f, 11.45f, -3.84f, 16.13f, -0.01f)
                curveToRelative(0.56f, 0.46f, 1.38f, 0.42f, 1.89f, -0.09f)
                curveToRelative(0.59f, -0.59f, 0.55f, -1.57f, -0.1f, -2.1f)
                curveToRelative(-5.71f, -4.67f, -13.97f, -4.67f, -19.69f, 0.0f)
                curveToRelative(-0.65f, 0.52f, -0.7f, 1.5f, -0.1f, 2.1f)
                close()
                moveTo(9.82f, 17.82f)
                lineToRelative(1.47f, 1.47f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(1.47f, -1.47f)
                curveToRelative(0.47f, -0.47f, 0.37f, -1.28f, -0.23f, -1.59f)
                curveToRelative(-1.22f, -0.63f, -2.68f, -0.63f, -3.91f, 0.0f)
                curveToRelative(-0.57f, 0.31f, -0.68f, 1.12f, -0.21f, 1.59f)
                close()
                moveTo(6.09f, 14.09f)
                curveToRelative(0.49f, 0.49f, 1.26f, 0.54f, 1.83f, 0.13f)
                curveToRelative(2.44f, -1.73f, 5.72f, -1.73f, 8.16f, 0.0f)
                curveToRelative(0.57f, 0.4f, 1.34f, 0.36f, 1.83f, -0.13f)
                lineToRelative(0.01f, -0.01f)
                curveToRelative(0.6f, -0.6f, 0.56f, -1.62f, -0.13f, -2.11f)
                curveToRelative(-3.44f, -2.49f, -8.13f, -2.49f, -11.58f, 0.0f)
                curveToRelative(-0.69f, 0.5f, -0.73f, 1.51f, -0.12f, 2.12f)
                close()
            }
        }
        _wifi = vec
        return vec
    }

