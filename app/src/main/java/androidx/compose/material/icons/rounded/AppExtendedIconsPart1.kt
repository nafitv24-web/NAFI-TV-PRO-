package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _addCircleOutline: ImageVector? = null
val Icons.Rounded.AddCircleOutline: ImageVector
    get() {
        _addCircleOutline?.let { return it }
        val vec = buildRoundedIcon("Rounded.AddCircleOutline") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                lineTo(8.0f, 11.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-3.0f)
                lineTo(13.0f, 8.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-4.41f, 0.0f, -8.0f, -3.59f, -8.0f, -8.0f)
                reflectiveCurveToRelative(3.59f, -8.0f, 8.0f, -8.0f)
                reflectiveCurveToRelative(8.0f, 3.59f, 8.0f, 8.0f)
                reflectiveCurveToRelative(-3.59f, 8.0f, -8.0f, 8.0f)
                close()
            }
        }
        _addCircleOutline = vec
        return vec
    }

private var _adminPanelSettings: ImageVector? = null
val Icons.Rounded.AdminPanelSettings: ImageVector
    get() {
        _adminPanelSettings?.let { return it }
        val vec = buildRoundedIcon("Rounded.AdminPanelSettings") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 11.0f)
                curveToRelative(0.34f, 0.0f, 0.67f, 0.04f, 1.0f, 0.09f)
                verticalLineTo(7.58f)
                curveToRelative(0.0f, -0.8f, -0.47f, -1.52f, -1.2f, -1.83f)
                lineToRelative(-5.5f, -2.4f)
                curveToRelative(-0.51f, -0.22f, -1.09f, -0.22f, -1.6f, 0.0f)
                lineToRelative(-5.5f, 2.4f)
                curveTo(3.47f, 6.07f, 3.0f, 6.79f, 3.0f, 7.58f)
                verticalLineToRelative(3.6f)
                curveToRelative(0.0f, 4.54f, 3.2f, 8.79f, 7.5f, 9.82f)
                curveToRelative(0.55f, -0.13f, 1.08f, -0.32f, 1.6f, -0.55f)
                curveTo(11.41f, 19.47f, 11.0f, 18.28f, 11.0f, 17.0f)
                curveTo(11.0f, 13.69f, 13.69f, 11.0f, 17.0f, 11.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 13.0f)
                curveToRelative(-2.21f, 0.0f, -4.0f, 1.79f, -4.0f, 4.0f)
                curveToRelative(0.0f, 2.21f, 1.79f, 4.0f, 4.0f, 4.0f)
                reflectiveCurveToRelative(4.0f, -1.79f, 4.0f, -4.0f)
                curveTo(21.0f, 14.79f, 19.21f, 13.0f, 17.0f, 13.0f)
                close()
                moveTo(17.0f, 14.38f)
                curveToRelative(0.62f, 0.0f, 1.12f, 0.51f, 1.12f, 1.12f)
                reflectiveCurveToRelative(-0.51f, 1.12f, -1.12f, 1.12f)
                reflectiveCurveToRelative(-1.12f, -0.51f, -1.12f, -1.12f)
                reflectiveCurveTo(16.38f, 14.38f, 17.0f, 14.38f)
                close()
                moveTo(17.0f, 19.75f)
                curveToRelative(-0.93f, 0.0f, -1.74f, -0.46f, -2.24f, -1.17f)
                curveToRelative(0.05f, -0.72f, 1.51f, -1.08f, 2.24f, -1.08f)
                reflectiveCurveToRelative(2.19f, 0.36f, 2.24f, 1.08f)
                curveTo(18.74f, 19.29f, 17.93f, 19.75f, 17.0f, 19.75f)
                close()
            }
        }
        _adminPanelSettings = vec
        return vec
    }

private var _aspectRatio: ImageVector? = null
val Icons.Rounded.AspectRatio: ImageVector
    get() {
        _aspectRatio?.let { return it }
        val vec = buildRoundedIcon("Rounded.AspectRatio") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.0f, 12.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(7.0f, 9.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(6.0f, 7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(7.0f, 9.0f)
                close()
                moveTo(21.0f, 3.0f)
                lineTo(3.0f, 3.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(18.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(23.0f, 5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(20.0f, 19.01f)
                lineTo(4.0f, 19.01f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(3.0f, 5.99f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(12.02f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _aspectRatio = vec
        return vec
    }

private var _audiotrack: ImageVector? = null
val Icons.Rounded.Audiotrack: ImageVector
    get() {
        _audiotrack?.let { return it }
        val vec = buildRoundedIcon("Rounded.Audiotrack") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 5.0f)
                verticalLineToRelative(8.55f)
                curveToRelative(-0.94f, -0.54f, -2.1f, -0.75f, -3.33f, -0.32f)
                curveToRelative(-1.34f, 0.48f, -2.37f, 1.67f, -2.61f, 3.07f)
                curveToRelative(-0.46f, 2.74f, 1.86f, 5.08f, 4.59f, 4.65f)
                curveToRelative(1.96f, -0.31f, 3.35f, -2.11f, 3.35f, -4.1f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                close()
            }
        }
        _audiotrack = vec
        return vec
    }

private var _autoAwesome: ImageVector? = null
val Icons.Rounded.AutoAwesome: ImageVector
    get() {
        _autoAwesome?.let { return it }
        val vec = buildRoundedIcon("Rounded.AutoAwesome") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.46f, 8.0f)
                lineToRelative(0.79f, -1.75f)
                lineTo(22.0f, 5.46f)
                curveToRelative(0.39f, -0.18f, 0.39f, -0.73f, 0.0f, -0.91f)
                lineToRelative(-1.75f, -0.79f)
                lineTo(19.46f, 2.0f)
                curveToRelative(-0.18f, -0.39f, -0.73f, -0.39f, -0.91f, 0.0f)
                lineToRelative(-0.79f, 1.75f)
                lineTo(16.0f, 4.54f)
                curveToRelative(-0.39f, 0.18f, -0.39f, 0.73f, 0.0f, 0.91f)
                lineToRelative(1.75f, 0.79f)
                lineTo(18.54f, 8.0f)
                curveTo(18.72f, 8.39f, 19.28f, 8.39f, 19.46f, 8.0f)
                close()
                moveTo(11.5f, 9.5f)
                lineTo(9.91f, 6.0f)
                curveTo(9.56f, 5.22f, 8.44f, 5.22f, 8.09f, 6.0f)
                lineTo(6.5f, 9.5f)
                lineTo(3.0f, 11.09f)
                curveToRelative(-0.78f, 0.36f, -0.78f, 1.47f, 0.0f, 1.82f)
                lineToRelative(3.5f, 1.59f)
                lineTo(8.09f, 18.0f)
                curveToRelative(0.36f, 0.78f, 1.47f, 0.78f, 1.82f, 0.0f)
                lineToRelative(1.59f, -3.5f)
                lineToRelative(3.5f, -1.59f)
                curveToRelative(0.78f, -0.36f, 0.78f, -1.47f, 0.0f, -1.82f)
                lineTo(11.5f, 9.5f)
                close()
                moveTo(18.54f, 16.0f)
                lineToRelative(-0.79f, 1.75f)
                lineTo(16.0f, 18.54f)
                curveToRelative(-0.39f, 0.18f, -0.39f, 0.73f, 0.0f, 0.91f)
                lineToRelative(1.75f, 0.79f)
                lineTo(18.54f, 22.0f)
                curveToRelative(0.18f, 0.39f, 0.73f, 0.39f, 0.91f, 0.0f)
                lineToRelative(0.79f, -1.75f)
                lineTo(22.0f, 19.46f)
                curveToRelative(0.39f, -0.18f, 0.39f, -0.73f, 0.0f, -0.91f)
                lineToRelative(-1.75f, -0.79f)
                lineTo(19.46f, 16.0f)
                curveTo(19.28f, 15.61f, 18.72f, 15.61f, 18.54f, 16.0f)
                close()
            }
        }
        _autoAwesome = vec
        return vec
    }

private var _autorenew: ImageVector? = null
val Icons.Rounded.Autorenew: ImageVector
    get() {
        _autorenew?.let { return it }
        val vec = buildRoundedIcon("Rounded.Autorenew") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 6.0f)
                verticalLineToRelative(1.79f)
                curveToRelative(0.0f, 0.45f, 0.54f, 0.67f, 0.85f, 0.35f)
                lineToRelative(2.79f, -2.79f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineToRelative(-2.79f, -2.79f)
                curveToRelative(-0.31f, -0.31f, -0.85f, -0.09f, -0.85f, 0.36f)
                lineTo(12.0f, 4.0f)
                curveToRelative(-4.42f, 0.0f, -8.0f, 3.58f, -8.0f, 8.0f)
                curveToRelative(0.0f, 1.04f, 0.2f, 2.04f, 0.57f, 2.95f)
                curveToRelative(0.27f, 0.67f, 1.13f, 0.85f, 1.64f, 0.34f)
                curveToRelative(0.27f, -0.27f, 0.38f, -0.68f, 0.23f, -1.04f)
                curveTo(6.15f, 13.56f, 6.0f, 12.79f, 6.0f, 12.0f)
                curveToRelative(0.0f, -3.31f, 2.69f, -6.0f, 6.0f, -6.0f)
                close()
                moveTo(17.79f, 8.71f)
                curveToRelative(-0.27f, 0.27f, -0.38f, 0.69f, -0.23f, 1.04f)
                curveToRelative(0.28f, 0.7f, 0.44f, 1.46f, 0.44f, 2.25f)
                curveToRelative(0.0f, 3.31f, -2.69f, 6.0f, -6.0f, 6.0f)
                verticalLineToRelative(-1.79f)
                curveToRelative(0.0f, -0.45f, -0.54f, -0.67f, -0.85f, -0.35f)
                lineToRelative(-2.79f, 2.79f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(2.79f, 2.79f)
                curveToRelative(0.31f, 0.31f, 0.85f, 0.09f, 0.85f, -0.35f)
                lineTo(12.0f, 20.0f)
                curveToRelative(4.42f, 0.0f, 8.0f, -3.58f, 8.0f, -8.0f)
                curveToRelative(0.0f, -1.04f, -0.2f, -2.04f, -0.57f, -2.95f)
                curveToRelative(-0.27f, -0.67f, -1.13f, -0.85f, -1.64f, -0.34f)
                close()
            }
        }
        _autorenew = vec
        return vec
    }

private var _block: ImageVector? = null
val Icons.Rounded.Block: ImageVector
    get() {
        _block?.let { return it }
        val vec = buildRoundedIcon("Rounded.Block") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(4.0f, 12.0f)
                curveToRelative(0.0f, -4.42f, 3.58f, -8.0f, 8.0f, -8.0f)
                curveToRelative(1.85f, 0.0f, 3.55f, 0.63f, 4.9f, 1.69f)
                lineTo(5.69f, 16.9f)
                curveTo(4.63f, 15.55f, 4.0f, 13.85f, 4.0f, 12.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-1.85f, 0.0f, -3.55f, -0.63f, -4.9f, -1.69f)
                lineTo(18.31f, 7.1f)
                curveTo(19.37f, 8.45f, 20.0f, 10.15f, 20.0f, 12.0f)
                curveToRelative(0.0f, 4.42f, -3.58f, 8.0f, -8.0f, 8.0f)
                close()
            }
        }
        _block = vec
        return vec
    }

private var _bolt: ImageVector? = null
val Icons.Rounded.Bolt: ImageVector
    get() {
        _bolt?.let { return it }
        val vec = buildRoundedIcon("Rounded.Bolt") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.67f, 21.0f)
                lineTo(10.67f, 21.0f)
                curveToRelative(-0.35f, 0.0f, -0.62f, -0.31f, -0.57f, -0.66f)
                lineTo(11.0f, 14.0f)
                horizontalLineTo(7.5f)
                curveToRelative(-0.88f, 0.0f, -0.33f, -0.75f, -0.31f, -0.78f)
                curveToRelative(1.26f, -2.23f, 3.15f, -5.53f, 5.65f, -9.93f)
                curveToRelative(0.1f, -0.18f, 0.3f, -0.29f, 0.5f, -0.29f)
                horizontalLineToRelative(0.0f)
                curveToRelative(0.35f, 0.0f, 0.62f, 0.31f, 0.57f, 0.66f)
                lineTo(13.01f, 10.0f)
                horizontalLineToRelative(3.51f)
                curveToRelative(0.4f, 0.0f, 0.62f, 0.19f, 0.4f, 0.66f)
                curveToRelative(-3.29f, 5.74f, -5.2f, 9.09f, -5.75f, 10.05f)
                curveTo(11.07f, 20.89f, 10.88f, 21.0f, 10.67f, 21.0f)
                close()
            }
        }
        _bolt = vec
        return vec
    }

private var _bookmark: ImageVector? = null
val Icons.Rounded.Bookmark: ImageVector
    get() {
        _bookmark?.let { return it }
        val vec = buildRoundedIcon("Rounded.Bookmark") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 3.0f)
                horizontalLineTo(7.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(16.0f)
                lineToRelative(7.0f, -3.0f)
                lineToRelative(7.0f, 3.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
            }
        }
        _bookmark = vec
        return vec
    }

private var _brightnessHigh: ImageVector? = null
val Icons.Rounded.BrightnessHigh: ImageVector
    get() {
        _brightnessHigh?.let { return it }
        val vec = buildRoundedIcon("Rounded.BrightnessHigh") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 8.69f)
                lineTo(20.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-2.69f)
                lineToRelative(-1.9f, -1.9f)
                curveToRelative(-0.78f, -0.78f, -2.05f, -0.78f, -2.83f, 0.0f)
                lineTo(8.69f, 4.0f)
                lineTo(6.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(2.69f)
                lineToRelative(-1.9f, 1.9f)
                curveToRelative(-0.78f, 0.78f, -0.78f, 2.05f, 0.0f, 2.83f)
                lineToRelative(1.9f, 1.9f)
                lineTo(4.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(2.69f)
                lineToRelative(1.9f, 1.9f)
                curveToRelative(0.78f, 0.78f, 2.05f, 0.78f, 2.83f, 0.0f)
                lineToRelative(1.9f, -1.9f)
                lineTo(18.0f, 20.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-2.69f)
                lineToRelative(1.9f, -1.9f)
                curveToRelative(0.78f, -0.78f, 0.78f, -2.05f, 0.0f, -2.83f)
                lineTo(20.0f, 8.69f)
                close()
                moveTo(12.0f, 18.0f)
                curveToRelative(-3.31f, 0.0f, -6.0f, -2.69f, -6.0f, -6.0f)
                reflectiveCurveToRelative(2.69f, -6.0f, 6.0f, -6.0f)
                reflectiveCurveToRelative(6.0f, 2.69f, 6.0f, 6.0f)
                reflectiveCurveToRelative(-2.69f, 6.0f, -6.0f, 6.0f)
                close()
                moveTo(12.0f, 8.0f)
                curveToRelative(-2.21f, 0.0f, -4.0f, 1.79f, -4.0f, 4.0f)
                reflectiveCurveToRelative(1.79f, 4.0f, 4.0f, 4.0f)
                reflectiveCurveToRelative(4.0f, -1.79f, 4.0f, -4.0f)
                reflectiveCurveToRelative(-1.79f, -4.0f, -4.0f, -4.0f)
                close()
            }
        }
        _brightnessHigh = vec
        return vec
    }

