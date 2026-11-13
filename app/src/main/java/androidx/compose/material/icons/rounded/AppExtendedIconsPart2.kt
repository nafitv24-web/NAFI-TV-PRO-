package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _campaign: ImageVector? = null
val Icons.Rounded.Campaign: ImageVector
    get() {
        _campaign?.let { return it }
        val vec = buildRoundedIcon("Rounded.Campaign") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.0f, 12.0f)
                lineTo(18.0f, 12.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-2.0f)
                curveTo(18.45f, 11.0f, 18.0f, 11.45f, 18.0f, 12.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.59f, 16.82f)
                curveToRelative(-0.33f, 0.44f, -0.24f, 1.05f, 0.2f, 1.37f)
                curveToRelative(0.53f, 0.39f, 1.09f, 0.81f, 1.62f, 1.21f)
                curveToRelative(0.44f, 0.33f, 1.06f, 0.24f, 1.38f, -0.2f)
                curveToRelative(0.0f, -0.01f, 0.01f, -0.01f, 0.01f, -0.02f)
                curveToRelative(0.33f, -0.44f, 0.24f, -1.06f, -0.2f, -1.38f)
                curveToRelative(-0.53f, -0.4f, -1.09f, -0.82f, -1.61f, -1.21f)
                curveToRelative(-0.44f, -0.33f, -1.06f, -0.23f, -1.39f, 0.21f)
                curveTo(16.6f, 16.81f, 16.59f, 16.82f, 16.59f, 16.82f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.81f, 4.81f)
                curveToRelative(0.0f, -0.01f, -0.01f, -0.01f, -0.01f, -0.02f)
                curveToRelative(-0.33f, -0.44f, -0.95f, -0.53f, -1.38f, -0.2f)
                curveToRelative(-0.53f, 0.4f, -1.1f, 0.82f, -1.62f, 1.22f)
                curveToRelative(-0.44f, 0.33f, -0.52f, 0.95f, -0.19f, 1.38f)
                curveToRelative(0.0f, 0.01f, 0.01f, 0.01f, 0.01f, 0.02f)
                curveToRelative(0.33f, 0.44f, 0.94f, 0.53f, 1.38f, 0.2f)
                curveToRelative(0.53f, -0.39f, 1.09f, -0.82f, 1.62f, -1.22f)
                curveTo(20.05f, 5.87f, 20.13f, 5.25f, 19.81f, 4.81f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(8.0f, 9.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(0.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(1.0f)
                lineToRelative(5.0f, 3.0f)
                verticalLineTo(6.0f)
                lineTo(8.0f, 9.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.5f, 12.0f)
                curveToRelative(0.0f, -1.33f, -0.58f, -2.53f, -1.5f, -3.35f)
                verticalLineToRelative(6.69f)
                curveTo(14.92f, 14.53f, 15.5f, 13.33f, 15.5f, 12.0f)
                close()
            }
        }
        _campaign = vec
        return vec
    }

private var _chevronRight: ImageVector? = null
val Icons.Rounded.ChevronRight: ImageVector
    get() {
        _chevronRight?.let { return it }
        val vec = buildRoundedIcon("Rounded.ChevronRight") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.29f, 6.71f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineTo(13.17f, 12.0f)
                lineToRelative(-3.88f, 3.88f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(4.59f, -4.59f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(10.7f, 6.7f)
                curveToRelative(-0.38f, -0.38f, -1.02f, -0.38f, -1.41f, 0.01f)
                close()
            }
        }
        _chevronRight = vec
        return vec
    }

private var _cleaningServices: ImageVector? = null
val Icons.Rounded.CleaningServices: ImageVector
    get() {
        _cleaningServices?.let { return it }
        val vec = buildRoundedIcon("Rounded.CleaningServices") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.0f, 11.0f)
                horizontalLineToRelative(-1.0f)
                verticalLineTo(4.0f)
                curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                horizontalLineToRelative(0.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                verticalLineToRelative(7.0f)
                horizontalLineTo(8.0f)
                curveToRelative(-2.76f, 0.0f, -5.0f, 2.24f, -5.0f, 5.0f)
                verticalLineToRelative(5.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-5.0f)
                curveTo(21.0f, 13.24f, 18.76f, 11.0f, 16.0f, 11.0f)
                close()
                moveTo(19.0f, 21.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                horizontalLineTo(9.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(-5.0f)
                curveToRelative(0.0f, -1.65f, 1.35f, -3.0f, 3.0f, -3.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.65f, 0.0f, 3.0f, 1.35f, 3.0f, 3.0f)
                verticalLineTo(21.0f)
                close()
            }
        }
        _cleaningServices = vec
        return vec
    }

private var _cloudDone: ImageVector? = null
val Icons.Rounded.CloudDone: ImageVector
    get() {
        _cloudDone?.let { return it }
        val vec = buildRoundedIcon("Rounded.CloudDone") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.35f, 10.04f)
                curveTo(18.67f, 6.59f, 15.64f, 4.0f, 12.0f, 4.0f)
                curveTo(9.11f, 4.0f, 6.6f, 5.64f, 5.35f, 8.04f)
                curveTo(2.34f, 8.36f, 0.0f, 10.91f, 0.0f, 14.0f)
                curveToRelative(0.0f, 3.31f, 2.69f, 6.0f, 6.0f, 6.0f)
                horizontalLineToRelative(13.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                curveToRelative(0.0f, -2.64f, -2.05f, -4.78f, -4.65f, -4.96f)
                close()
                moveTo(10.71f, 16.29f)
                curveToRelative(-0.39f, 0.39f, -1.02f, 0.39f, -1.41f, 0.0f)
                lineTo(7.2f, 14.2f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                lineTo(10.0f, 14.18f)
                lineToRelative(4.48f, -4.48f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(-5.18f, 5.18f)
                close()
            }
        }
        _cloudDone = vec
        return vec
    }

private var _cloudDownload: ImageVector? = null
val Icons.Rounded.CloudDownload: ImageVector
    get() {
        _cloudDownload?.let { return it }
        val vec = buildRoundedIcon("Rounded.CloudDownload") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.35f, 10.04f)
                curveTo(18.67f, 6.59f, 15.64f, 4.0f, 12.0f, 4.0f)
                curveTo(9.11f, 4.0f, 6.6f, 5.64f, 5.35f, 8.04f)
                curveTo(2.34f, 8.36f, 0.0f, 10.91f, 0.0f, 14.0f)
                curveToRelative(0.0f, 3.31f, 2.69f, 6.0f, 6.0f, 6.0f)
                horizontalLineToRelative(13.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                curveToRelative(0.0f, -2.64f, -2.05f, -4.78f, -4.65f, -4.96f)
                close()
                moveTo(17.0f, 13.0f)
                lineToRelative(-4.65f, 4.65f)
                curveToRelative(-0.2f, 0.2f, -0.51f, 0.2f, -0.71f, 0.0f)
                lineTo(7.0f, 13.0f)
                horizontalLineToRelative(3.0f)
                verticalLineTo(9.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(3.0f)
                close()
            }
        }
        _cloudDownload = vec
        return vec
    }

private var _cloudSync: ImageVector? = null
val Icons.Rounded.CloudSync: ImageVector
    get() {
        _cloudSync?.let { return it }
        val vec = buildRoundedIcon("Rounded.CloudSync") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(24.0f, 17.48f)
                curveToRelative(0.0f, 1.38f, -1.12f, 2.5f, -2.5f, 2.5f)
                lineTo(15.0f, 20.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, -1.34f, -3.0f, -3.0f)
                curveToRelative(0.0f, -1.6f, 1.26f, -2.9f, 2.84f, -2.98f)
                curveTo(15.4f, 12.83f, 16.6f, 12.0f, 18.0f, 12.0f)
                curveToRelative(1.76f, 0.0f, 3.2f, 1.3f, 3.45f, 2.99f)
                curveToRelative(0.02f, 0.0f, 0.03f, -0.01f, 0.05f, -0.01f)
                curveTo(22.88f, 14.98f, 24.0f, 16.1f, 24.0f, 17.48f)
                close()
                moveTo(10.0f, 15.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(1.44f)
                curveToRelative(-1.22f, -1.1f, -2.0f, -2.67f, -2.0f, -4.44f)
                curveToRelative(0.0f, -2.38f, 1.39f, -4.43f, 3.4f, -5.4f)
                curveTo(9.77f, 6.42f, 10.0f, 6.04f, 10.0f, 5.63f)
                curveToRelative(0.0f, -0.71f, -0.73f, -1.18f, -1.37f, -0.88f)
                curveTo(5.89f, 6.03f, 4.0f, 8.79f, 4.0f, 12.0f)
                curveToRelative(0.0f, 2.4f, 1.06f, 4.54f, 2.73f, 6.0f)
                horizontalLineTo(5.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(15.0f)
                close()
                moveTo(19.0f, 6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(7.56f)
                curveToRelative(0.98f, 0.89f, 1.68f, 2.08f, 1.92f, 3.44f)
                lineToRelative(2.02f, 0.0f)
                curveToRelative(-0.25f, -1.99f, -1.23f, -3.74f, -2.66f, -5.0f)
                horizontalLineTo(19.0f)
                close()
            }
        }
        _cloudSync = vec
        return vec
    }

private var _cloudUpload: ImageVector? = null
val Icons.Rounded.CloudUpload: ImageVector
    get() {
        _cloudUpload?.let { return it }
        val vec = buildRoundedIcon("Rounded.CloudUpload") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.35f, 10.04f)
                curveTo(18.67f, 6.59f, 15.64f, 4.0f, 12.0f, 4.0f)
                curveTo(9.11f, 4.0f, 6.6f, 5.64f, 5.35f, 8.04f)
                curveTo(2.34f, 8.36f, 0.0f, 10.91f, 0.0f, 14.0f)
                curveToRelative(0.0f, 3.31f, 2.69f, 6.0f, 6.0f, 6.0f)
                horizontalLineToRelative(13.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                curveToRelative(0.0f, -2.64f, -2.05f, -4.78f, -4.65f, -4.96f)
                close()
                moveTo(14.0f, 13.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineTo(7.0f)
                lineToRelative(4.65f, -4.65f)
                curveToRelative(0.2f, -0.2f, 0.51f, -0.2f, 0.71f, 0.0f)
                lineTo(17.0f, 13.0f)
                horizontalLineToRelative(-3.0f)
                close()
            }
        }
        _cloudUpload = vec
        return vec
    }

private var _contentCopy: ImageVector? = null
val Icons.Rounded.ContentCopy: ImageVector
    get() {
        _contentCopy?.let { return it }
        val vec = buildRoundedIcon("Rounded.ContentCopy") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.0f, 20.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(0.0f)
                curveTo(3.45f, 6.0f, 3.0f, 6.45f, 3.0f, 7.0f)
                verticalLineToRelative(13.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveTo(16.0f, 20.45f, 15.55f, 20.0f, 15.0f, 20.0f)
                close()
                moveTo(20.0f, 16.0f)
                verticalLineTo(4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineTo(9.0f)
                curveTo(7.9f, 2.0f, 7.0f, 2.9f, 7.0f, 4.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(9.0f)
                curveTo(19.1f, 18.0f, 20.0f, 17.1f, 20.0f, 16.0f)
                close()
                moveTo(18.0f, 16.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(4.0f)
                horizontalLineToRelative(9.0f)
                verticalLineTo(16.0f)
                close()
            }
        }
        _contentCopy = vec
        return vec
    }

private var _deleteForever: ImageVector? = null
val Icons.Rounded.DeleteForever: ImageVector
    get() {
        _deleteForever?.let { return it }
        val vec = buildRoundedIcon("Rounded.DeleteForever") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 19.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(19.0f)
                close()
                moveTo(9.17f, 12.59f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                lineTo(12.0f, 12.59f)
                lineToRelative(1.41f, -1.41f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                reflectiveCurveToRelative(0.39f, 1.02f, 0.0f, 1.41f)
                lineTo(13.41f, 14.0f)
                lineToRelative(1.41f, 1.41f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                reflectiveCurveToRelative(-1.02f, 0.39f, -1.41f, 0.0f)
                lineTo(12.0f, 15.41f)
                lineToRelative(-1.41f, 1.41f)
                curveToRelative(-0.39f, 0.39f, -1.02f, 0.39f, -1.41f, 0.0f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(10.59f, 14.0f)
                lineTo(9.17f, 12.59f)
                close()
                moveTo(18.0f, 4.0f)
                horizontalLineToRelative(-2.5f)
                lineToRelative(-0.71f, -0.71f)
                curveTo(14.61f, 3.11f, 14.35f, 3.0f, 14.09f, 3.0f)
                horizontalLineTo(9.91f)
                curveToRelative(-0.26f, 0.0f, -0.52f, 0.11f, -0.7f, 0.29f)
                lineTo(8.5f, 4.0f)
                horizontalLineTo(6.0f)
                curveTo(5.45f, 4.0f, 5.0f, 4.45f, 5.0f, 5.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveTo(18.55f, 4.0f, 18.0f, 4.0f)
                close()
            }
        }
        _deleteForever = vec
        return vec
    }

