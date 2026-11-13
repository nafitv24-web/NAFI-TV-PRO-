package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _gridView: ImageVector? = null
val Icons.Rounded.GridView: ImageVector
    get() {
        _gridView?.let { return it }
        val vec = buildRoundedIcon("Rounded.GridView") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.0f, 11.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 3.0f, 3.0f, 3.9f, 3.0f, 5.0f)
                verticalLineToRelative(4.0f)
                curveTo(3.0f, 10.1f, 3.9f, 11.0f, 5.0f, 11.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.0f, 21.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineTo(5.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(4.0f)
                curveTo(3.0f, 20.1f, 3.9f, 21.0f, 5.0f, 21.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(13.0f, 5.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-4.0f)
                curveTo(13.9f, 3.0f, 13.0f, 3.9f, 13.0f, 5.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.0f, 21.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(4.0f)
                curveTo(13.0f, 20.1f, 13.9f, 21.0f, 15.0f, 21.0f)
                close()
            }
        }
        _gridView = vec
        return vec
    }

private var _group: ImageVector? = null
val Icons.Rounded.Group: ImageVector
    get() {
        _group?.let { return it }
        val vec = buildRoundedIcon("Rounded.Group") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.0f, 11.0f)
                curveToRelative(1.66f, 0.0f, 2.99f, -1.34f, 2.99f, -3.0f)
                reflectiveCurveTo(17.66f, 5.0f, 16.0f, 5.0f)
                reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                close()
                moveTo(8.0f, 11.0f)
                curveToRelative(1.66f, 0.0f, 2.99f, -1.34f, 2.99f, -3.0f)
                reflectiveCurveTo(9.66f, 5.0f, 8.0f, 5.0f)
                reflectiveCurveTo(5.0f, 6.34f, 5.0f, 8.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                close()
                moveTo(8.0f, 13.0f)
                curveToRelative(-2.33f, 0.0f, -7.0f, 1.17f, -7.0f, 3.5f)
                lineTo(1.0f, 18.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-1.5f)
                curveToRelative(0.0f, -2.33f, -4.67f, -3.5f, -7.0f, -3.5f)
                close()
                moveTo(16.0f, 13.0f)
                curveToRelative(-0.29f, 0.0f, -0.62f, 0.02f, -0.97f, 0.05f)
                curveToRelative(0.02f, 0.01f, 0.03f, 0.03f, 0.04f, 0.04f)
                curveToRelative(1.14f, 0.83f, 1.93f, 1.94f, 1.93f, 3.41f)
                lineTo(17.0f, 18.0f)
                curveToRelative(0.0f, 0.35f, -0.07f, 0.69f, -0.18f, 1.0f)
                lineTo(22.0f, 19.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-1.5f)
                curveToRelative(0.0f, -2.33f, -4.67f, -3.5f, -7.0f, -3.5f)
                close()
            }
        }
        _group = vec
        return vec
    }

private var _highQuality: ImageVector? = null
val Icons.Rounded.HighQuality: ImageVector
    get() {
        _highQuality?.let { return it }
        val vec = buildRoundedIcon("Rounded.HighQuality") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.0f, 4.0f)
                lineTo(5.0f, 4.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(21.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(10.25f, 15.0f)
                curveToRelative(-0.41f, 0.0f, -0.75f, -0.34f, -0.75f, -0.75f)
                lineTo(9.5f, 13.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(1.25f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveTo(6.0f, 14.66f, 6.0f, 14.25f)
                verticalLineToRelative(-4.5f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                reflectiveCurveToRelative(0.75f, 0.34f, 0.75f, 0.75f)
                verticalLineToRelative(1.75f)
                horizontalLineToRelative(2.0f)
                lineTo(9.5f, 9.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                reflectiveCurveToRelative(0.75f, 0.34f, 0.75f, 0.75f)
                verticalLineToRelative(4.5f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                close()
                moveTo(18.0f, 14.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-0.75f)
                verticalLineToRelative(0.75f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveToRelative(-0.75f, -0.34f, -0.75f, -0.75f)
                lineTo(14.75f, 15.0f)
                lineTo(14.0f, 15.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(14.5f, 13.5f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(3.0f)
                close()
            }
        }
        _highQuality = vec
        return vec
    }

private var _hourglassTop: ImageVector? = null
val Icons.Rounded.HourglassTop: ImageVector
    get() {
        _hourglassTop?.let { return it }
        val vec = buildRoundedIcon("Rounded.HourglassTop") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(8.0f, 2.0f)
                curveTo(6.9f, 2.0f, 6.0f, 2.9f, 6.0f, 4.0f)
                lineToRelative(0.01f, 3.18f)
                curveToRelative(0.0f, 0.53f, 0.21f, 1.03f, 0.58f, 1.41f)
                lineTo(10.0f, 12.0f)
                lineToRelative(-3.41f, 3.43f)
                curveToRelative(-0.37f, 0.37f, -0.58f, 0.88f, -0.58f, 1.41f)
                lineTo(6.0f, 20.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-3.16f)
                curveToRelative(0.0f, -0.53f, -0.21f, -1.04f, -0.58f, -1.41f)
                lineTo(14.0f, 12.0f)
                lineToRelative(3.41f, -3.4f)
                curveTo(17.79f, 8.22f, 18.0f, 7.71f, 18.0f, 7.18f)
                verticalLineTo(4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineTo(8.0f)
                close()
                moveTo(16.0f, 16.91f)
                verticalLineTo(19.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineTo(9.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-2.09f)
                curveToRelative(0.0f, -0.27f, 0.11f, -0.52f, 0.29f, -0.71f)
                lineTo(12.0f, 12.5f)
                lineToRelative(3.71f, 3.71f)
                curveTo(15.89f, 16.39f, 16.0f, 16.65f, 16.0f, 16.91f)
                close()
            }
        }
        _hourglassTop = vec
        return vec
    }

private var _language: ImageVector? = null
val Icons.Rounded.Language: ImageVector
    get() {
        _language?.let { return it }
        val vec = buildRoundedIcon("Rounded.Language") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.99f, 2.0f)
                curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.47f, 10.0f, 9.99f, 10.0f)
                curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
                reflectiveCurveTo(17.52f, 2.0f, 11.99f, 2.0f)
                close()
                moveTo(18.92f, 8.0f)
                horizontalLineToRelative(-2.95f)
                curveToRelative(-0.32f, -1.25f, -0.78f, -2.45f, -1.38f, -3.56f)
                curveToRelative(1.84f, 0.63f, 3.37f, 1.91f, 4.33f, 3.56f)
                close()
                moveTo(12.0f, 4.04f)
                curveToRelative(0.83f, 1.2f, 1.48f, 2.53f, 1.91f, 3.96f)
                horizontalLineToRelative(-3.82f)
                curveToRelative(0.43f, -1.43f, 1.08f, -2.76f, 1.91f, -3.96f)
                close()
                moveTo(4.26f, 14.0f)
                curveTo(4.1f, 13.36f, 4.0f, 12.69f, 4.0f, 12.0f)
                reflectiveCurveToRelative(0.1f, -1.36f, 0.26f, -2.0f)
                horizontalLineToRelative(3.38f)
                curveToRelative(-0.08f, 0.66f, -0.14f, 1.32f, -0.14f, 2.0f)
                reflectiveCurveToRelative(0.06f, 1.34f, 0.14f, 2.0f)
                lineTo(4.26f, 14.0f)
                close()
                moveTo(5.08f, 16.0f)
                horizontalLineToRelative(2.95f)
                curveToRelative(0.32f, 1.25f, 0.78f, 2.45f, 1.38f, 3.56f)
                curveToRelative(-1.84f, -0.63f, -3.37f, -1.9f, -4.33f, -3.56f)
                close()
                moveTo(8.03f, 8.0f)
                lineTo(5.08f, 8.0f)
                curveToRelative(0.96f, -1.66f, 2.49f, -2.93f, 4.33f, -3.56f)
                curveTo(8.81f, 5.55f, 8.35f, 6.75f, 8.03f, 8.0f)
                close()
                moveTo(12.0f, 19.96f)
                curveToRelative(-0.83f, -1.2f, -1.48f, -2.53f, -1.91f, -3.96f)
                horizontalLineToRelative(3.82f)
                curveToRelative(-0.43f, 1.43f, -1.08f, 2.76f, -1.91f, 3.96f)
                close()
                moveTo(14.34f, 14.0f)
                lineTo(9.66f, 14.0f)
                curveToRelative(-0.09f, -0.66f, -0.16f, -1.32f, -0.16f, -2.0f)
                reflectiveCurveToRelative(0.07f, -1.35f, 0.16f, -2.0f)
                horizontalLineToRelative(4.68f)
                curveToRelative(0.09f, 0.65f, 0.16f, 1.32f, 0.16f, 2.0f)
                reflectiveCurveToRelative(-0.07f, 1.34f, -0.16f, 2.0f)
                close()
                moveTo(14.59f, 19.56f)
                curveToRelative(0.6f, -1.11f, 1.06f, -2.31f, 1.38f, -3.56f)
                horizontalLineToRelative(2.95f)
                curveToRelative(-0.96f, 1.65f, -2.49f, 2.93f, -4.33f, 3.56f)
                close()
                moveTo(16.36f, 14.0f)
                curveToRelative(0.08f, -0.66f, 0.14f, -1.32f, 0.14f, -2.0f)
                reflectiveCurveToRelative(-0.06f, -1.34f, -0.14f, -2.0f)
                horizontalLineToRelative(3.38f)
                curveToRelative(0.16f, 0.64f, 0.26f, 1.31f, 0.26f, 2.0f)
                reflectiveCurveToRelative(-0.1f, 1.36f, -0.26f, 2.0f)
                horizontalLineToRelative(-3.38f)
                close()
            }
        }
        _language = vec
        return vec
    }

private var _layers: ImageVector? = null
val Icons.Rounded.Layers: ImageVector
    get() {
        _layers?.let { return it }
        val vec = buildRoundedIcon("Rounded.Layers") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.6f, 18.06f)
                curveToRelative(-0.36f, 0.28f, -0.87f, 0.28f, -1.23f, 0.0f)
                lineToRelative(-6.15f, -4.78f)
                curveToRelative(-0.36f, -0.28f, -0.86f, -0.28f, -1.22f, 0.0f)
                curveToRelative(-0.51f, 0.4f, -0.51f, 1.17f, 0.0f, 1.57f)
                lineToRelative(6.76f, 5.26f)
                curveToRelative(0.72f, 0.56f, 1.73f, 0.56f, 2.46f, 0.0f)
                lineToRelative(6.76f, -5.26f)
                curveToRelative(0.51f, -0.4f, 0.51f, -1.17f, 0.0f, -1.57f)
                lineToRelative(-0.01f, -0.01f)
                curveToRelative(-0.36f, -0.28f, -0.86f, -0.28f, -1.22f, 0.0f)
                lineToRelative(-6.15f, 4.79f)
                close()
                moveTo(13.23f, 15.04f)
                lineToRelative(6.76f, -5.26f)
                curveToRelative(0.51f, -0.4f, 0.51f, -1.18f, 0.0f, -1.58f)
                lineToRelative(-6.76f, -5.26f)
                curveToRelative(-0.72f, -0.56f, -1.73f, -0.56f, -2.46f, 0.0f)
                lineTo(4.01f, 8.21f)
                curveToRelative(-0.51f, 0.4f, -0.51f, 1.18f, 0.0f, 1.58f)
                lineToRelative(6.76f, 5.26f)
                curveToRelative(0.72f, 0.56f, 1.74f, 0.56f, 2.46f, -0.01f)
                close()
            }
        }
        _layers = vec
        return vec
    }

private var _link: ImageVector? = null
val Icons.Rounded.Link: ImageVector
    get() {
        _link?.let { return it }
        val vec = buildRoundedIcon("Rounded.Link") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 7.0f)
                horizontalLineToRelative(-3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(1.65f, 0.0f, 3.0f, 1.35f, 3.0f, 3.0f)
                reflectiveCurveToRelative(-1.35f, 3.0f, -3.0f, 3.0f)
                horizontalLineToRelative(-3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                reflectiveCurveTo(19.76f, 7.0f, 17.0f, 7.0f)
                close()
                moveTo(8.0f, 12.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(9.0f)
                curveTo(8.45f, 11.0f, 8.0f, 11.45f, 8.0f, 12.0f)
                close()
                moveTo(10.0f, 15.0f)
                horizontalLineTo(7.0f)
                curveToRelative(-1.65f, 0.0f, -3.0f, -1.35f, -3.0f, -3.0f)
                reflectiveCurveToRelative(1.35f, -3.0f, 3.0f, -3.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(7.0f)
                curveToRelative(-2.76f, 0.0f, -5.0f, 2.24f, -5.0f, 5.0f)
                reflectiveCurveToRelative(2.24f, 5.0f, 5.0f, 5.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                curveTo(11.0f, 15.45f, 10.55f, 15.0f, 10.0f, 15.0f)
                close()
            }
        }
        _link = vec
        return vec
    }

private var _liveTv: ImageVector? = null
val Icons.Rounded.LiveTv: ImageVector
    get() {
        _liveTv?.let { return it }
        val vec = buildRoundedIcon("Rounded.LiveTv") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.5f, 17.15f)
                lineToRelative(3.98f, -2.28f)
                curveToRelative(0.67f, -0.38f, 0.67f, -1.35f, 0.0f, -1.74f)
                lineToRelative(-3.98f, -2.28f)
                curveToRelative(-0.67f, -0.38f, -1.5f, 0.11f, -1.5f, 0.87f)
                verticalLineToRelative(4.55f)
                curveToRelative(0.0f, 0.77f, 0.83f, 1.26f, 1.5f, 0.88f)
                close()
                moveTo(21.0f, 6.0f)
                horizontalLineToRelative(-7.59f)
                lineToRelative(2.94f, -2.94f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                reflectiveCurveToRelative(-0.51f, -0.2f, -0.71f, 0.0f)
                lineTo(12.0f, 5.99f)
                lineTo(8.36f, 2.35f)
                curveToRelative(-0.2f, -0.2f, -0.51f, -0.2f, -0.71f, 0.0f)
                reflectiveCurveToRelative(-0.2f, 0.51f, 0.0f, 0.71f)
                lineTo(10.59f, 6.0f)
                lineTo(3.0f, 6.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.89f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(18.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(23.0f, 8.0f)
                curveToRelative(0.0f, -1.11f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(20.0f, 20.0f)
                lineTo(4.0f, 20.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(3.0f, 9.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _liveTv = vec
        return vec
    }

