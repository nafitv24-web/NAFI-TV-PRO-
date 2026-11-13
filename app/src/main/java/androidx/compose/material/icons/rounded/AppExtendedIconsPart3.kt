package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _deleteOutline: ImageVector? = null
val Icons.Rounded.DeleteOutline: ImageVector
    get() {
        _deleteOutline?.let { return it }
        val vec = buildRoundedIcon("Rounded.DeleteOutline") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 19.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(18.0f, 9.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                lineTo(8.0f, 7.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(10.0f)
                close()
                moveTo(9.0f, 9.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(8.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(9.0f, 19.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-8.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(15.5f, 4.0f)
                lineToRelative(-0.71f, -0.71f)
                curveToRelative(-0.18f, -0.18f, -0.44f, -0.29f, -0.7f, -0.29f)
                lineTo(9.91f, 3.0f)
                curveToRelative(-0.26f, 0.0f, -0.52f, 0.11f, -0.7f, 0.29f)
                lineTo(8.5f, 4.0f)
                lineTo(6.0f, 4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-2.5f)
                close()
            }
        }
        _deleteOutline = vec
        return vec
    }

private var _deleteSweep: ImageVector? = null
val Icons.Rounded.DeleteSweep: ImageVector
    get() {
        _deleteSweep?.let { return it }
        val vec = buildRoundedIcon("Rounded.DeleteSweep") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.0f, 16.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(16.0f, 8.0f)
                horizontalLineToRelative(5.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-5.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(16.0f, 12.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(3.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(13.0f, 8.0f)
                lineTo(3.0f, 8.0f)
                verticalLineToRelative(10.0f)
                close()
                moveTo(13.0f, 5.0f)
                horizontalLineToRelative(-2.0f)
                lineToRelative(-0.71f, -0.71f)
                curveToRelative(-0.18f, -0.18f, -0.44f, -0.29f, -0.7f, -0.29f)
                lineTo(6.41f, 4.0f)
                curveToRelative(-0.26f, 0.0f, -0.52f, 0.11f, -0.7f, 0.29f)
                lineTo(5.0f, 5.0f)
                lineTo(3.0f, 5.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                close()
            }
        }
        _deleteSweep = vec
        return vec
    }

private var _devices: ImageVector? = null
val Icons.Rounded.Devices: ImageVector
    get() {
        _devices?.let { return it }
        val vec = buildRoundedIcon("Rounded.Devices") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4.0f, 7.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(4.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(11.0f)
                horizontalLineToRelative(-0.5f)
                curveToRelative(-0.83f, 0.0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveTo(0.67f, 20.0f, 1.5f, 20.0f)
                lineTo(14.0f, 20.0f)
                verticalLineToRelative(-3.0f)
                lineTo(4.0f, 17.0f)
                lineTo(4.0f, 7.0f)
                close()
                moveTo(23.0f, 8.0f)
                horizontalLineToRelative(-6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(24.0f, 9.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(22.0f, 17.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(-7.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(7.0f)
                close()
            }
        }
        _devices = vec
        return vec
    }

private var _dns: ImageVector? = null
val Icons.Rounded.Dns: ImageVector
    get() {
        _dns?.let { return it }
        val vec = buildRoundedIcon("Rounded.Dns") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.0f, 13.0f)
                horizontalLineTo(5.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(7.0f, 19.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                close()
                moveTo(19.0f, 3.0f)
                horizontalLineTo(5.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(7.0f, 9.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                close()
            }
        }
        _dns = vec
        return vec
    }

private var _doneAll: ImageVector? = null
val Icons.Rounded.DoneAll: ImageVector
    get() {
        _doneAll?.let { return it }
        val vec = buildRoundedIcon("Rounded.DoneAll") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.3f, 6.3f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                lineToRelative(-5.64f, 5.64f)
                lineToRelative(1.41f, 1.41f)
                lineTo(17.3f, 7.7f)
                curveToRelative(0.38f, -0.38f, 0.38f, -1.02f, 0.0f, -1.4f)
                close()
                moveTo(21.54f, 6.29f)
                lineToRelative(-9.88f, 9.88f)
                lineToRelative(-3.48f, -3.47f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(4.18f, 4.18f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineTo(22.95f, 7.71f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                horizontalLineToRelative(-0.01f)
                curveToRelative(-0.38f, -0.4f, -1.01f, -0.4f, -1.4f, -0.01f)
                close()
                moveTo(1.12f, 14.12f)
                lineTo(5.3f, 18.3f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(0.7f, -0.7f)
                lineToRelative(-4.88f, -4.9f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.03f, 0.0f, 1.42f)
                close()
            }
        }
        _doneAll = vec
        return vec
    }

private var _download: ImageVector? = null
val Icons.Rounded.Download: ImageVector
    get() {
        _download?.let { return it }
        val vec = buildRoundedIcon("Rounded.Download") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.59f, 9.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(4.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(5.0f)
                horizontalLineTo(7.41f)
                curveToRelative(-0.89f, 0.0f, -1.34f, 1.08f, -0.71f, 1.71f)
                lineToRelative(4.59f, 4.59f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(4.59f, -4.59f)
                curveToRelative(0.63f, -0.63f, 0.19f, -1.71f, -0.7f, -1.71f)
                close()
                moveTo(5.0f, 19.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _download = vec
        return vec
    }

private var _downloadDone: ImageVector? = null
val Icons.Rounded.DownloadDone: ImageVector
    get() {
        _downloadDone?.let { return it }
        val vec = buildRoundedIcon("Rounded.DownloadDone") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 18.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(6.0f, 20.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(11.01f, 13.9f)
                curveToRelative(-0.78f, 0.77f, -2.04f, 0.77f, -2.82f, -0.01f)
                lineTo(6.0f, 11.7f)
                curveToRelative(-0.55f, -0.55f, -0.54f, -1.44f, 0.03f, -1.97f)
                curveToRelative(0.54f, -0.52f, 1.4f, -0.5f, 1.92f, 0.02f)
                lineTo(9.6f, 11.4f)
                lineToRelative(6.43f, -6.43f)
                curveToRelative(0.54f, -0.54f, 1.41f, -0.54f, 1.95f, 0.0f)
                lineToRelative(0.04f, 0.04f)
                curveToRelative(0.54f, 0.54f, 0.54f, 1.42f, -0.01f, 1.96f)
                lineToRelative(-7.0f, 6.93f)
                close()
            }
        }
        _downloadDone = vec
        return vec
    }

private var _downloadForOffline: ImageVector? = null
val Icons.Rounded.DownloadForOffline: ImageVector
    get() {
        _downloadForOffline?.let { return it }
        val vec = buildRoundedIcon("Rounded.DownloadForOffline") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 2.0f)
                curveTo(6.49f, 2.0f, 2.0f, 6.49f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.49f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.49f, 10.0f, -10.0f)
                reflectiveCurveTo(17.51f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(11.0f, 10.0f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(0.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(1.79f)
                curveToRelative(0.45f, 0.0f, 0.67f, 0.54f, 0.35f, 0.85f)
                lineToRelative(-2.79f, 2.79f)
                curveToRelative(-0.2f, 0.2f, -0.51f, 0.2f, -0.71f, 0.0f)
                lineToRelative(-2.79f, -2.79f)
                curveTo(8.54f, 10.54f, 8.76f, 10.0f, 9.21f, 10.0f)
                horizontalLineTo(11.0f)
                close()
                moveTo(16.0f, 17.0f)
                horizontalLineTo(8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveTo(17.0f, 16.55f, 16.55f, 17.0f, 16.0f, 17.0f)
                close()
            }
        }
        _downloadForOffline = vec
        return vec
    }

private var _downloading: ImageVector? = null
val Icons.Rounded.Downloading: ImageVector
    get() {
        _downloading?.let { return it }
        val vec = buildRoundedIcon("Rounded.Downloading") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.33f, 3.55f)
                curveToRelative(-0.94f, -0.6f, -1.99f, -1.04f, -3.12f, -1.3f)
                curveTo(13.59f, 2.11f, 13.0f, 2.59f, 13.0f, 3.23f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.45f, 0.3f, 0.87f, 0.74f, 0.97f)
                curveToRelative(0.91f, 0.2f, 1.77f, 0.56f, 2.53f, 1.05f)
                curveToRelative(0.39f, 0.25f, 0.89f, 0.17f, 1.22f, -0.16f)
                lineToRelative(0.0f, 0.0f)
                curveTo(17.94f, 4.64f, 17.87f, 3.89f, 17.33f, 3.55f)
                close()
                moveTo(20.77f, 11.0f)
                lineTo(20.77f, 11.0f)
                curveToRelative(0.64f, 0.0f, 1.13f, -0.59f, 0.98f, -1.21f)
                curveToRelative(-0.26f, -1.12f, -0.7f, -2.17f, -1.3f, -3.12f)
                curveToRelative(-0.34f, -0.54f, -1.1f, -0.61f, -1.55f, -0.16f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(-0.32f, 0.32f, -0.4f, 0.83f, -0.16f, 1.22f)
                curveToRelative(0.49f, 0.77f, 0.85f, 1.62f, 1.05f, 2.53f)
                curveTo(19.9f, 10.7f, 20.31f, 11.0f, 20.77f, 11.0f)
                close()
                moveTo(18.9f, 17.49f)
                lineTo(18.9f, 17.49f)
                curveToRelative(0.45f, 0.45f, 1.21f, 0.38f, 1.55f, -0.15f)
                curveToRelative(0.6f, -0.94f, 1.04f, -1.99f, 1.3f, -3.11f)
                curveToRelative(0.14f, -0.62f, -0.35f, -1.21f, -0.98f, -1.21f)
                horizontalLineToRelative(0.0f)
                curveToRelative(-0.45f, 0.0f, -0.87f, 0.3f, -0.97f, 0.74f)
                curveToRelative(-0.2f, 0.91f, -0.57f, 1.76f, -1.05f, 2.53f)
                curveTo(18.5f, 16.66f, 18.58f, 17.17f, 18.9f, 17.49f)
                close()
                moveTo(13.0f, 20.77f)
                lineTo(13.0f, 20.77f)
                curveToRelative(0.0f, 0.64f, 0.59f, 1.13f, 1.21f, 0.98f)
                curveToRelative(1.12f, -0.26f, 2.17f, -0.7f, 3.11f, -1.3f)
                curveToRelative(0.54f, -0.34f, 0.61f, -1.1f, 0.16f, -1.55f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(-0.32f, -0.32f, -0.83f, -0.4f, -1.21f, -0.15f)
                curveToRelative(-0.76f, 0.49f, -1.61f, 0.85f, -2.53f, 1.05f)
                curveTo(13.3f, 19.9f, 13.0f, 20.31f, 13.0f, 20.77f)
                close()
                moveTo(13.0f, 12.0f)
                verticalLineTo(8.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(0.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                horizontalLineTo(9.41f)
                curveToRelative(-0.89f, 0.0f, -1.34f, 1.08f, -0.71f, 1.71f)
                lineToRelative(2.59f, 2.59f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(2.59f, -2.59f)
                curveToRelative(0.63f, -0.63f, 0.18f, -1.71f, -0.71f, -1.71f)
                horizontalLineTo(13.0f)
                close()
                moveTo(11.0f, 20.77f)
                lineTo(11.0f, 20.77f)
                curveToRelative(0.0f, 0.64f, -0.59f, 1.13f, -1.21f, 0.99f)
                curveTo(5.33f, 20.75f, 2.0f, 16.77f, 2.0f, 12.0f)
                reflectiveCurveToRelative(3.33f, -8.75f, 7.79f, -9.75f)
                curveTo(10.41f, 2.11f, 11.0f, 2.59f, 11.0f, 3.23f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.46f, -0.31f, 0.87f, -0.76f, 0.97f)
                curveTo(6.67f, 5.0f, 4.0f, 8.19f, 4.0f, 12.0f)
                reflectiveCurveToRelative(2.67f, 7.0f, 6.24f, 7.8f)
                curveTo(10.69f, 19.9f, 11.0f, 20.31f, 11.0f, 20.77f)
                close()
            }
        }
        _downloading = vec
        return vec
    }

