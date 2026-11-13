package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _smartDisplay: ImageVector? = null
val Icons.Rounded.SmartDisplay: ImageVector
    get() {
        _smartDisplay?.let { return it }
        val vec = buildRoundedIcon("Rounded.SmartDisplay") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 4.0f)
                horizontalLineTo(4.0f)
                curveTo(2.9f, 4.0f, 2.0f, 4.9f, 2.0f, 6.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(6.0f)
                curveTo(22.0f, 4.9f, 21.1f, 4.0f, 20.0f, 4.0f)
                close()
                moveTo(9.5f, 14.67f)
                verticalLineTo(9.33f)
                curveToRelative(0.0f, -0.79f, 0.88f, -1.27f, 1.54f, -0.84f)
                lineToRelative(4.15f, 2.67f)
                curveToRelative(0.61f, 0.39f, 0.61f, 1.29f, 0.0f, 1.68f)
                lineToRelative(-4.15f, 2.67f)
                curveTo(10.38f, 15.94f, 9.5f, 15.46f, 9.5f, 14.67f)
                close()
            }
        }
        _smartDisplay = vec
        return vec
    }

private var _smartphone: ImageVector? = null
val Icons.Rounded.Smartphone: ImageVector
    get() {
        _smartphone?.let { return it }
        val vec = buildRoundedIcon("Rounded.Smartphone") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 1.01f)
                lineTo(7.0f, 1.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(3.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -1.99f, -2.0f, -1.99f)
                close()
                moveTo(17.0f, 19.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(5.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(14.0f)
                close()
            }
        }
        _smartphone = vec
        return vec
    }

private var _speed: ImageVector? = null
val Icons.Rounded.Speed: ImageVector
    get() {
        _speed?.let { return it }
        val vec = buildRoundedIcon("Rounded.Speed") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.46f, 10.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -0.07f, 1.0f)
                arcToRelative(7.55f, 7.55f, 0.0f, false, true, 0.52f, 1.81f)
                arcToRelative(8.0f, 8.0f, 0.0f, false, true, -0.69f, 4.73f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.89f, 0.53f)
                horizontalLineTo(5.68f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.89f, -0.54f)
                arcTo(8.0f, 8.0f, 0.0f, false, true, 13.0f, 6.06f)
                arcToRelative(7.69f, 7.69f, 0.0f, false, true, 2.11f, 0.56f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 1.0f, -0.07f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -0.17f, -1.76f)
                arcTo(10.0f, 10.0f, 0.0f, false, false, 3.35f, 19.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 1.72f, 1.0f)
                horizontalLineToRelative(13.85f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 1.74f, -1.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, 0.55f, -8.89f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.75f, -0.11f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.59f, 12.59f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 2.83f, 2.83f)
                lineToRelative(5.66f, -8.49f)
                close()
            }
        }
        _speed = vec
        return vec
    }

private var _sportsSoccer: ImageVector? = null
val Icons.Rounded.SportsSoccer: ImageVector
    get() {
        _sportsSoccer?.let { return it }
        val vec = buildRoundedIcon("Rounded.SportsSoccer") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                curveToRelative(0.0f, 5.52f, 4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                curveTo(22.0f, 6.48f, 17.52f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(13.0f, 5.3f)
                lineToRelative(1.35f, -0.95f)
                curveToRelative(1.82f, 0.56f, 3.37f, 1.76f, 4.38f, 3.34f)
                lineToRelative(-0.39f, 1.34f)
                lineToRelative(-1.35f, 0.46f)
                lineTo(13.0f, 6.7f)
                verticalLineTo(5.3f)
                close()
                moveTo(9.65f, 4.35f)
                lineTo(11.0f, 5.3f)
                verticalLineToRelative(1.4f)
                lineTo(7.01f, 9.49f)
                lineTo(5.66f, 9.03f)
                lineTo(5.27f, 7.69f)
                curveTo(6.28f, 6.12f, 7.83f, 4.92f, 9.65f, 4.35f)
                close()
                moveTo(7.08f, 17.11f)
                lineToRelative(-1.14f, 0.1f)
                curveTo(4.73f, 15.81f, 4.0f, 13.99f, 4.0f, 12.0f)
                curveToRelative(0.0f, -0.12f, 0.01f, -0.23f, 0.02f, -0.35f)
                lineToRelative(1.0f, -0.73f)
                lineTo(6.4f, 11.4f)
                lineToRelative(1.46f, 4.34f)
                lineTo(7.08f, 17.11f)
                close()
                moveTo(14.5f, 19.59f)
                curveTo(13.71f, 19.85f, 12.87f, 20.0f, 12.0f, 20.0f)
                reflectiveCurveToRelative(-1.71f, -0.15f, -2.5f, -0.41f)
                lineToRelative(-0.69f, -1.49f)
                lineTo(9.45f, 17.0f)
                horizontalLineToRelative(5.11f)
                lineToRelative(0.64f, 1.11f)
                lineTo(14.5f, 19.59f)
                close()
                moveTo(14.27f, 15.0f)
                horizontalLineTo(9.73f)
                lineToRelative(-1.35f, -4.02f)
                lineTo(12.0f, 8.44f)
                lineToRelative(3.63f, 2.54f)
                lineTo(14.27f, 15.0f)
                close()
                moveTo(18.06f, 17.21f)
                lineToRelative(-1.14f, -0.1f)
                lineToRelative(-0.79f, -1.37f)
                lineToRelative(1.46f, -4.34f)
                lineToRelative(1.39f, -0.47f)
                lineToRelative(1.0f, 0.73f)
                curveTo(19.99f, 11.77f, 20.0f, 11.88f, 20.0f, 12.0f)
                curveTo(20.0f, 13.99f, 19.27f, 15.81f, 18.06f, 17.21f)
                close()
            }
        }
        _sportsSoccer = vec
        return vec
    }

private var _starBorder: ImageVector? = null
val Icons.Rounded.StarBorder: ImageVector
    get() {
        _starBorder?.let { return it }
        val vec = buildRoundedIcon("Rounded.StarBorder") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.65f, 9.04f)
                lineToRelative(-4.84f, -0.42f)
                lineToRelative(-1.89f, -4.45f)
                curveToRelative(-0.34f, -0.81f, -1.5f, -0.81f, -1.84f, 0.0f)
                lineTo(9.19f, 8.63f)
                lineToRelative(-4.83f, 0.41f)
                curveToRelative(-0.88f, 0.07f, -1.24f, 1.17f, -0.57f, 1.75f)
                lineToRelative(3.67f, 3.18f)
                lineToRelative(-1.1f, 4.72f)
                curveToRelative(-0.2f, 0.86f, 0.73f, 1.54f, 1.49f, 1.08f)
                lineToRelative(4.15f, -2.5f)
                lineToRelative(4.15f, 2.51f)
                curveToRelative(0.76f, 0.46f, 1.69f, -0.22f, 1.49f, -1.08f)
                lineToRelative(-1.1f, -4.73f)
                lineToRelative(3.67f, -3.18f)
                curveToRelative(0.67f, -0.58f, 0.32f, -1.68f, -0.56f, -1.75f)
                close()
                moveTo(12.0f, 15.4f)
                lineToRelative(-3.76f, 2.27f)
                lineToRelative(1.0f, -4.28f)
                lineToRelative(-3.32f, -2.88f)
                lineToRelative(4.38f, -0.38f)
                lineTo(12.0f, 6.1f)
                lineToRelative(1.71f, 4.04f)
                lineToRelative(4.38f, 0.38f)
                lineToRelative(-3.32f, 2.88f)
                lineToRelative(1.0f, 4.28f)
                lineTo(12.0f, 15.4f)
                close()
            }
        }
        _starBorder = vec
        return vec
    }

private var _subtitles: ImageVector? = null
val Icons.Rounded.Subtitles: ImageVector
    get() {
        _subtitles?.let { return it }
        val vec = buildRoundedIcon("Rounded.Subtitles") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 4.0f)
                lineTo(4.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(5.0f, 12.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(5.0f, 14.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(13.0f, 18.0f)
                lineTo(5.0f, 18.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                close()
                moveTo(19.0f, 18.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                close()
                moveTo(19.0f, 14.0f)
                horizontalLineToRelative(-8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _subtitles = vec
        return vec
    }

private var _swapHoriz: ImageVector? = null
val Icons.Rounded.SwapHoriz: ImageVector
    get() {
        _swapHoriz?.let { return it }
        val vec = buildRoundedIcon("Rounded.SwapHoriz") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.14f, 11.86f)
                lineToRelative(-2.78f, 2.79f)
                curveToRelative(-0.19f, 0.2f, -0.19f, 0.51f, 0.0f, 0.71f)
                lineToRelative(2.78f, 2.79f)
                curveToRelative(0.31f, 0.32f, 0.85f, 0.09f, 0.85f, -0.35f)
                lineTo(6.99f, 16.0f)
                lineTo(13.0f, 16.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(6.99f, 14.0f)
                verticalLineToRelative(-1.79f)
                curveToRelative(0.0f, -0.45f, -0.54f, -0.67f, -0.85f, -0.35f)
                close()
                moveTo(20.65f, 8.65f)
                lineToRelative(-2.78f, -2.79f)
                curveToRelative(-0.31f, -0.32f, -0.85f, -0.09f, -0.85f, 0.35f)
                lineTo(17.02f, 8.0f)
                lineTo(11.0f, 8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(6.01f)
                verticalLineToRelative(1.79f)
                curveToRelative(0.0f, 0.45f, 0.54f, 0.67f, 0.85f, 0.35f)
                lineToRelative(2.78f, -2.79f)
                curveToRelative(0.2f, -0.19f, 0.2f, -0.51f, 0.01f, -0.7f)
                close()
            }
        }
        _swapHoriz = vec
        return vec
    }

private var _sync: ImageVector? = null
val Icons.Rounded.Sync: ImageVector
    get() {
        _sync?.let { return it }
        val vec = buildRoundedIcon("Rounded.Sync") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 4.0f)
                lineTo(12.0f, 2.21f)
                curveToRelative(0.0f, -0.45f, -0.54f, -0.67f, -0.85f, -0.35f)
                lineToRelative(-2.8f, 2.79f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(2.79f, 2.79f)
                curveToRelative(0.32f, 0.31f, 0.86f, 0.09f, 0.86f, -0.36f)
                lineTo(12.0f, 6.0f)
                curveToRelative(3.31f, 0.0f, 6.0f, 2.69f, 6.0f, 6.0f)
                curveToRelative(0.0f, 0.79f, -0.15f, 1.56f, -0.44f, 2.25f)
                curveToRelative(-0.15f, 0.36f, -0.04f, 0.77f, 0.23f, 1.04f)
                curveToRelative(0.51f, 0.51f, 1.37f, 0.33f, 1.64f, -0.34f)
                curveToRelative(0.37f, -0.91f, 0.57f, -1.91f, 0.57f, -2.95f)
                curveToRelative(0.0f, -4.42f, -3.58f, -8.0f, -8.0f, -8.0f)
                close()
                moveTo(12.0f, 18.0f)
                curveToRelative(-3.31f, 0.0f, -6.0f, -2.69f, -6.0f, -6.0f)
                curveToRelative(0.0f, -0.79f, 0.15f, -1.56f, 0.44f, -2.25f)
                curveToRelative(0.15f, -0.36f, 0.04f, -0.77f, -0.23f, -1.04f)
                curveToRelative(-0.51f, -0.51f, -1.37f, -0.33f, -1.64f, 0.34f)
                curveTo(4.2f, 9.96f, 4.0f, 10.96f, 4.0f, 12.0f)
                curveToRelative(0.0f, 4.42f, 3.58f, 8.0f, 8.0f, 8.0f)
                verticalLineToRelative(1.79f)
                curveToRelative(0.0f, 0.45f, 0.54f, 0.67f, 0.85f, 0.35f)
                lineToRelative(2.79f, -2.79f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineToRelative(-2.79f, -2.79f)
                curveToRelative(-0.31f, -0.31f, -0.85f, -0.09f, -0.85f, 0.36f)
                lineTo(12.0f, 18.0f)
                close()
            }
        }
        _sync = vec
        return vec
    }

private var _systemUpdate: ImageVector? = null
val Icons.Rounded.SystemUpdate: ImageVector
    get() {
        _systemUpdate?.let { return it }
        val vec = buildRoundedIcon("Rounded.SystemUpdate") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 1.01f)
                lineTo(7.0f, 1.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(19.0f, 3.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -1.99f, -2.0f, -1.99f)
                close()
                moveTo(17.0f, 19.0f)
                lineTo(7.0f, 19.0f)
                lineTo(7.0f, 5.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(14.0f)
                close()
                moveTo(14.79f, 13.0f)
                lineTo(13.0f, 13.0f)
                lineTo(13.0f, 9.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                lineTo(9.21f, 13.0f)
                curveToRelative(-0.45f, 0.0f, -0.67f, 0.54f, -0.35f, 0.85f)
                lineToRelative(2.79f, 2.79f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineToRelative(2.79f, -2.79f)
                curveToRelative(0.31f, -0.31f, 0.09f, -0.85f, -0.36f, -0.85f)
                close()
            }
        }
        _systemUpdate = vec
        return vec
    }

