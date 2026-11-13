package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _folder: ImageVector? = null
val Icons.Rounded.Folder: ImageVector
    get() {
        _folder?.let { return it }
        val vec = buildRoundedIcon("Rounded.Folder") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.59f, 4.59f)
                curveTo(10.21f, 4.21f, 9.7f, 4.0f, 9.17f, 4.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(8.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-8.0f)
                lineToRelative(-1.41f, -1.41f)
                close()
            }
        }
        _folder = vec
        return vec
    }

private var _folderZip: ImageVector? = null
val Icons.Rounded.FolderZip: ImageVector
    get() {
        _folderZip?.let { return it }
        val vec = buildRoundedIcon("Rounded.FolderZip") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.0f, 6.0f)
                horizontalLineToRelative(-8.0f)
                lineToRelative(-1.41f, -1.41f)
                curveTo(10.21f, 4.21f, 9.7f, 4.0f, 9.17f, 4.0f)
                horizontalLineTo(4.0f)
                curveTo(2.9f, 4.0f, 2.01f, 4.9f, 2.01f, 6.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(8.0f)
                curveTo(22.0f, 6.9f, 21.1f, 6.0f, 20.0f, 6.0f)
                close()
                moveTo(18.0f, 12.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(12.0f)
                close()
            }
        }
        _folderZip = vec
        return vec
    }

private var _formatListBulleted: ImageVector? = null
val Icons.Rounded.FormatListBulleted: ImageVector
    get() {
        _formatListBulleted?.let { return it }
        val vec = buildRoundedIcon("Rounded.FormatListBulleted") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4.0f, 10.5f)
                curveToRelative(-0.83f, 0.0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveToRelative(0.67f, 1.5f, 1.5f, 1.5f)
                reflectiveCurveToRelative(1.5f, -0.67f, 1.5f, -1.5f)
                reflectiveCurveToRelative(-0.67f, -1.5f, -1.5f, -1.5f)
                close()
                moveTo(4.0f, 4.5f)
                curveToRelative(-0.83f, 0.0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveTo(3.17f, 7.5f, 4.0f, 7.5f)
                reflectiveCurveTo(5.5f, 6.83f, 5.5f, 6.0f)
                reflectiveCurveTo(4.83f, 4.5f, 4.0f, 4.5f)
                close()
                moveTo(4.0f, 16.5f)
                curveToRelative(-0.83f, 0.0f, -1.5f, 0.68f, -1.5f, 1.5f)
                reflectiveCurveToRelative(0.68f, 1.5f, 1.5f, 1.5f)
                reflectiveCurveToRelative(1.5f, -0.68f, 1.5f, -1.5f)
                reflectiveCurveToRelative(-0.67f, -1.5f, -1.5f, -1.5f)
                close()
                moveTo(8.0f, 19.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(8.0f, 17.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(8.0f, 13.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(8.0f, 11.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(7.0f, 6.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(8.0f, 5.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _formatListBulleted = vec
        return vec
    }

private var _forward10: ImageVector? = null
val Icons.Rounded.Forward10: ImageVector
    get() {
        _forward10?.let { return it }
        val vec = buildRoundedIcon("Rounded.Forward10") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.92f, 13.0f)
                curveToRelative(-0.5f, 0.0f, -0.91f, 0.37f, -0.98f, 0.86f)
                curveToRelative(-0.48f, 3.37f, -3.77f, 5.84f, -7.42f, 4.96f)
                curveToRelative(-2.25f, -0.54f, -3.91f, -2.27f, -4.39f, -4.53f)
                curveTo(5.32f, 10.42f, 8.27f, 7.0f, 12.0f, 7.0f)
                verticalLineToRelative(2.79f)
                curveToRelative(0.0f, 0.45f, 0.54f, 0.67f, 0.85f, 0.35f)
                lineToRelative(3.79f, -3.79f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineToRelative(-3.79f, -3.79f)
                curveToRelative(-0.31f, -0.31f, -0.85f, -0.09f, -0.85f, 0.36f)
                lineTo(12.0f, 5.0f)
                curveToRelative(-4.94f, 0.0f, -8.84f, 4.48f, -7.84f, 9.6f)
                curveToRelative(0.6f, 3.11f, 2.9f, 5.5f, 5.99f, 6.19f)
                curveToRelative(4.83f, 1.08f, 9.15f, -2.2f, 9.77f, -6.67f)
                curveToRelative(0.09f, -0.59f, -0.4f, -1.12f, -1.0f, -1.12f)
                close()
                moveTo(10.9f, 16.0f)
                verticalLineToRelative(-4.27f)
                horizontalLineToRelative(-0.09f)
                lineToRelative(-1.77f, 0.63f)
                verticalLineToRelative(0.69f)
                lineToRelative(1.01f, -0.31f)
                lineTo(10.05f, 16.0f)
                close()
                moveTo(14.32f, 11.78f)
                curveToRelative(-0.18f, -0.07f, -0.37f, -0.1f, -0.59f, -0.1f)
                reflectiveCurveToRelative(-0.41f, 0.03f, -0.59f, 0.1f)
                reflectiveCurveToRelative(-0.33f, 0.18f, -0.45f, 0.33f)
                reflectiveCurveToRelative(-0.23f, 0.34f, -0.29f, 0.57f)
                reflectiveCurveToRelative(-0.1f, 0.5f, -0.1f, 0.82f)
                verticalLineToRelative(0.74f)
                curveToRelative(0.0f, 0.32f, 0.04f, 0.6f, 0.11f, 0.82f)
                reflectiveCurveToRelative(0.17f, 0.42f, 0.3f, 0.57f)
                reflectiveCurveToRelative(0.28f, 0.26f, 0.46f, 0.33f)
                reflectiveCurveToRelative(0.37f, 0.1f, 0.59f, 0.1f)
                reflectiveCurveToRelative(0.41f, -0.03f, 0.59f, -0.1f)
                reflectiveCurveToRelative(0.33f, -0.18f, 0.45f, -0.33f)
                reflectiveCurveToRelative(0.22f, -0.34f, 0.29f, -0.57f)
                reflectiveCurveToRelative(0.1f, -0.5f, 0.1f, -0.82f)
                verticalLineToRelative(-0.74f)
                curveToRelative(0.0f, -0.32f, -0.04f, -0.6f, -0.11f, -0.82f)
                reflectiveCurveToRelative(-0.17f, -0.42f, -0.3f, -0.57f)
                reflectiveCurveToRelative(-0.29f, -0.26f, -0.46f, -0.33f)
                close()
                moveTo(14.33f, 14.35f)
                curveToRelative(0.0f, 0.19f, -0.01f, 0.35f, -0.04f, 0.48f)
                reflectiveCurveToRelative(-0.06f, 0.24f, -0.11f, 0.32f)
                reflectiveCurveToRelative(-0.11f, 0.14f, -0.19f, 0.17f)
                reflectiveCurveToRelative(-0.16f, 0.05f, -0.25f, 0.05f)
                reflectiveCurveToRelative(-0.18f, -0.02f, -0.25f, -0.05f)
                reflectiveCurveToRelative(-0.14f, -0.09f, -0.19f, -0.17f)
                reflectiveCurveToRelative(-0.09f, -0.19f, -0.12f, -0.32f)
                reflectiveCurveToRelative(-0.04f, -0.29f, -0.04f, -0.48f)
                verticalLineToRelative(-0.97f)
                curveToRelative(0.0f, -0.19f, 0.01f, -0.35f, 0.04f, -0.48f)
                reflectiveCurveToRelative(0.06f, -0.23f, 0.12f, -0.31f)
                reflectiveCurveToRelative(0.11f, -0.14f, 0.19f, -0.17f)
                reflectiveCurveToRelative(0.16f, -0.05f, 0.25f, -0.05f)
                reflectiveCurveToRelative(0.18f, 0.02f, 0.25f, 0.05f)
                reflectiveCurveToRelative(0.14f, 0.09f, 0.19f, 0.17f)
                reflectiveCurveToRelative(0.09f, 0.18f, 0.12f, 0.31f)
                reflectiveCurveToRelative(0.04f, 0.29f, 0.04f, 0.48f)
                verticalLineToRelative(0.97f)
                close()
            }
        }
        _forward10 = vec
        return vec
    }

private var _fullscreen: ImageVector? = null
val Icons.Rounded.Fullscreen: ImageVector
    get() {
        _fullscreen?.let { return it }
        val vec = buildRoundedIcon("Rounded.Fullscreen") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 14.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(7.0f, 17.0f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(6.0f, 10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(7.0f, 7.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(6.0f, 5.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(17.0f, 17.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(14.0f, 6.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(19.0f, 6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _fullscreen = vec
        return vec
    }

private var _fullscreenExit: ImageVector? = null
val Icons.Rounded.FullscreenExit: ImageVector
    get() {
        _fullscreenExit?.let { return it }
        val vec = buildRoundedIcon("Rounded.FullscreenExit") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 16.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(6.0f, 14.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(8.0f, 8.0f)
                lineTo(6.0f, 8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(10.0f, 6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(15.0f, 19.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(16.0f, 8.0f)
                lineTo(16.0f, 6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }
        _fullscreenExit = vec
        return vec
    }

private var _gamepad: ImageVector? = null
val Icons.Rounded.Gamepad: ImageVector
    get() {
        _gamepad?.let { return it }
        val vec = buildRoundedIcon("Rounded.Gamepad") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.0f, 7.29f)
                lineTo(15.0f, 3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.29f)
                curveToRelative(0.0f, 0.13f, 0.05f, 0.26f, 0.15f, 0.35f)
                lineToRelative(2.5f, 2.5f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineToRelative(2.5f, -2.5f)
                curveToRelative(0.09f, -0.09f, 0.14f, -0.21f, 0.14f, -0.35f)
                close()
                moveTo(7.29f, 9.0f)
                lineTo(3.0f, 9.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.29f)
                curveToRelative(0.13f, 0.0f, 0.26f, -0.05f, 0.35f, -0.15f)
                lineToRelative(2.5f, -2.5f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineToRelative(-2.5f, -2.5f)
                curveTo(7.55f, 9.05f, 7.43f, 9.0f, 7.29f, 9.0f)
                close()
                moveTo(9.0f, 16.71f)
                lineTo(9.0f, 21.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-4.29f)
                curveToRelative(0.0f, -0.13f, -0.05f, -0.26f, -0.15f, -0.35f)
                lineToRelative(-2.5f, -2.5f)
                curveToRelative(-0.2f, -0.2f, -0.51f, -0.2f, -0.71f, 0.0f)
                lineToRelative(-2.5f, 2.5f)
                curveToRelative(-0.09f, 0.09f, -0.14f, 0.21f, -0.14f, 0.35f)
                close()
                moveTo(16.35f, 9.15f)
                lineToRelative(-2.5f, 2.5f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(2.5f, 2.5f)
                curveToRelative(0.09f, 0.09f, 0.22f, 0.15f, 0.35f, 0.15f)
                lineTo(21.0f, 15.01f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-4.29f)
                curveToRelative(-0.14f, -0.01f, -0.26f, 0.04f, -0.36f, 0.14f)
                close()
            }
        }
        _gamepad = vec
        return vec
    }

