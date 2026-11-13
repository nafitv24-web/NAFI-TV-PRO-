package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _schedule: ImageVector? = null
val Icons.Rounded.Schedule: ImageVector
    get() {
        _schedule?.let { return it }
        val vec = buildRoundedIcon("Rounded.Schedule") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.99f, 2.0f)
                curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.47f, 10.0f, 9.99f, 10.0f)
                curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
                reflectiveCurveTo(17.52f, 2.0f, 11.99f, 2.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-4.42f, 0.0f, -8.0f, -3.58f, -8.0f, -8.0f)
                reflectiveCurveToRelative(3.58f, -8.0f, 8.0f, -8.0f)
                reflectiveCurveToRelative(8.0f, 3.58f, 8.0f, 8.0f)
                reflectiveCurveToRelative(-3.58f, 8.0f, -8.0f, 8.0f)
                close()
                moveTo(11.78f, 7.0f)
                horizontalLineToRelative(-0.06f)
                curveToRelative(-0.4f, 0.0f, -0.72f, 0.32f, -0.72f, 0.72f)
                verticalLineToRelative(4.72f)
                curveToRelative(0.0f, 0.35f, 0.18f, 0.68f, 0.49f, 0.86f)
                lineToRelative(4.15f, 2.49f)
                curveToRelative(0.34f, 0.2f, 0.78f, 0.1f, 0.98f, -0.24f)
                curveToRelative(0.21f, -0.34f, 0.1f, -0.79f, -0.25f, -0.99f)
                lineToRelative(-3.87f, -2.3f)
                lineTo(12.5f, 7.72f)
                curveToRelative(0.0f, -0.4f, -0.32f, -0.72f, -0.72f, -0.72f)
                close()
            }
        }
        _schedule = vec
        return vec
    }

private var _scoreboard: ImageVector? = null
val Icons.Rounded.Scoreboard: ImageVector
    get() {
        _scoreboard?.let { return it }
        val vec = buildRoundedIcon("Rounded.Scoreboard") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.5f, 13.5f)
                horizontalLineTo(16.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(1.5f)
                verticalLineTo(13.5f)
                close()
                moveTo(16.0f, 2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(1.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveTo(7.0f, 2.45f, 7.0f, 3.0f)
                verticalLineToRelative(1.0f)
                horizontalLineTo(4.0f)
                curveTo(2.9f, 4.0f, 2.0f, 4.9f, 2.0f, 6.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-3.0f)
                verticalLineTo(3.0f)
                curveTo(17.0f, 2.45f, 16.55f, 2.0f, 16.0f, 2.0f)
                close()
                moveTo(9.5f, 14.25f)
                curveTo(9.5f, 14.66f, 9.16f, 15.0f, 8.75f, 15.0f)
                horizontalLineTo(6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-1.5f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-1.0f)
                horizontalLineTo(5.75f)
                curveTo(5.34f, 10.5f, 5.0f, 10.16f, 5.0f, 9.75f)
                reflectiveCurveTo(5.34f, 9.0f, 5.75f, 9.0f)
                horizontalLineTo(8.5f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(1.5f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(1.0f)
                horizontalLineToRelative(2.25f)
                curveTo(9.16f, 13.5f, 9.5f, 13.84f, 9.5f, 14.25f)
                close()
                moveTo(19.0f, 14.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-2.5f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineToRelative(-4.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineTo(18.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineTo(14.0f)
                close()
                moveTo(12.75f, 6.75f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveToRelative(-0.75f, -0.34f, -0.75f, -0.75f)
                reflectiveCurveTo(11.59f, 6.0f, 12.0f, 6.0f)
                reflectiveCurveTo(12.75f, 6.34f, 12.75f, 6.75f)
                close()
                moveTo(12.75f, 10.25f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveToRelative(-0.75f, -0.34f, -0.75f, -0.75f)
                reflectiveCurveTo(11.59f, 9.5f, 12.0f, 9.5f)
                reflectiveCurveTo(12.75f, 9.84f, 12.75f, 10.25f)
                close()
                moveTo(12.75f, 13.75f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveToRelative(-0.75f, -0.34f, -0.75f, -0.75f)
                reflectiveCurveTo(11.59f, 13.0f, 12.0f, 13.0f)
                reflectiveCurveTo(12.75f, 13.34f, 12.75f, 13.75f)
                close()
                moveTo(12.75f, 17.25f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                reflectiveCurveToRelative(-0.75f, -0.34f, -0.75f, -0.75f)
                reflectiveCurveTo(11.59f, 16.5f, 12.0f, 16.5f)
                reflectiveCurveTo(12.75f, 16.84f, 12.75f, 17.25f)
                close()
            }
        }
        _scoreboard = vec
        return vec
    }

private var _searchOff: ImageVector? = null
val Icons.Rounded.SearchOff: ImageVector
    get() {
        _searchOff?.let { return it }
        val vec = buildRoundedIcon("Rounded.SearchOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.5f, 14.0f)
                horizontalLineToRelative(-0.79f)
                lineToRelative(-0.28f, -0.27f)
                curveToRelative(1.2f, -1.4f, 1.82f, -3.31f, 1.48f, -5.34f)
                curveToRelative(-0.47f, -2.78f, -2.79f, -4.99f, -5.58f, -5.34f)
                curveTo(6.54f, 2.58f, 3.3f, 5.38f, 3.03f, 9.0f)
                horizontalLineToRelative(2.02f)
                curveToRelative(0.24f, -2.12f, 1.92f, -3.8f, 4.06f, -3.98f)
                curveTo(11.65f, 4.8f, 14.0f, 6.95f, 14.0f, 9.5f)
                curveToRelative(0.0f, 2.49f, -2.01f, 4.5f, -4.5f, 4.5f)
                curveToRelative(-0.17f, 0.0f, -0.33f, -0.03f, -0.5f, -0.05f)
                lineToRelative(0.0f, 2.02f)
                curveToRelative(0.0f, 0.0f, 0.0f, 0.0f, 0.01f, 0.01f)
                curveToRelative(1.8f, 0.13f, 3.47f, -0.47f, 4.72f, -1.55f)
                lineTo(14.0f, 14.71f)
                verticalLineToRelative(0.79f)
                lineToRelative(4.25f, 4.25f)
                curveToRelative(0.41f, 0.41f, 1.08f, 0.41f, 1.49f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(0.41f, -0.41f, 0.41f, -1.08f, 0.0f, -1.49f)
                lineTo(15.5f, 14.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.12f, 11.17f)
                lineTo(4.0f, 13.29f)
                lineToRelative(-2.12f, -2.12f)
                curveToRelative(-0.2f, -0.2f, -0.51f, -0.2f, -0.71f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineTo(3.29f, 14.0f)
                lineToRelative(-2.12f, 2.12f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineTo(4.0f, 14.71f)
                lineToRelative(2.12f, 2.12f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineTo(4.71f, 14.0f)
                lineToRelative(2.12f, -2.12f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                lineToRelative(0.0f, 0.0f)
                curveTo(6.63f, 10.98f, 6.32f, 10.98f, 6.12f, 11.17f)
                close()
            }
        }
        _searchOff = vec
        return vec
    }

private var _security: ImageVector? = null
val Icons.Rounded.Security: ImageVector
    get() {
        _security?.let { return it }
        val vec = buildRoundedIcon("Rounded.Security") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.19f, 1.36f)
                lineToRelative(-7.0f, 3.11f)
                curveTo(3.47f, 4.79f, 3.0f, 5.51f, 3.0f, 6.3f)
                verticalLineTo(11.0f)
                curveToRelative(0.0f, 5.55f, 3.84f, 10.74f, 9.0f, 12.0f)
                curveToRelative(5.16f, -1.26f, 9.0f, -6.45f, 9.0f, -12.0f)
                verticalLineTo(6.3f)
                curveToRelative(0.0f, -0.79f, -0.47f, -1.51f, -1.19f, -1.83f)
                lineToRelative(-7.0f, -3.11f)
                curveToRelative(-0.51f, -0.23f, -1.11f, -0.23f, -1.62f, 0.0f)
                close()
                moveTo(12.0f, 11.99f)
                horizontalLineToRelative(7.0f)
                curveToRelative(-0.53f, 4.12f, -3.28f, 7.79f, -7.0f, 8.94f)
                verticalLineTo(12.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(6.3f)
                lineToRelative(7.0f, -3.11f)
                verticalLineToRelative(8.8f)
                close()
            }
        }
        _security = vec
        return vec
    }

private var _securityUpdateGood: ImageVector? = null
val Icons.Rounded.SecurityUpdateGood: ImageVector
    get() {
        _securityUpdateGood?.let { return it }
        val vec = buildRoundedIcon("Rounded.SecurityUpdateGood") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.0f, 1.01f)
                lineTo(7.0f, 1.0f)
                curveTo(5.9f, 1.0f, 5.0f, 1.9f, 5.0f, 3.0f)
                verticalLineToRelative(18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(3.0f)
                curveTo(19.0f, 1.9f, 18.1f, 1.01f, 17.0f, 1.01f)
                close()
                moveTo(17.0f, 18.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(10.0f)
                verticalLineTo(18.0f)
                close()
                moveTo(10.34f, 14.29f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(3.54f, -3.54f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                lineToRelative(-2.83f, 2.83f)
                lineToRelative(-0.71f, -0.71f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineTo(10.34f, 14.29f)
                close()
            }
        }
        _securityUpdateGood = vec
        return vec
    }

private var _settingsSuggest: ImageVector? = null
val Icons.Rounded.SettingsSuggest: ImageVector
    get() {
        _settingsSuggest?.let { return it }
        val vec = buildRoundedIcon("Rounded.SettingsSuggest") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.04f, 7.99f)
                lineToRelative(-0.63f, -1.4f)
                lineToRelative(-1.4f, -0.63f)
                curveToRelative(-0.39f, -0.18f, -0.39f, -0.73f, 0.0f, -0.91f)
                lineToRelative(1.4f, -0.63f)
                lineToRelative(0.63f, -1.4f)
                curveToRelative(0.18f, -0.39f, 0.73f, -0.39f, 0.91f, 0.0f)
                lineToRelative(0.63f, 1.4f)
                lineToRelative(1.4f, 0.63f)
                curveToRelative(0.39f, 0.18f, 0.39f, 0.73f, 0.0f, 0.91f)
                lineToRelative(-1.4f, 0.63f)
                lineToRelative(-0.63f, 1.4f)
                curveTo(18.78f, 8.38f, 18.22f, 8.38f, 18.04f, 7.99f)
                close()
                moveTo(21.28f, 12.72f)
                lineTo(20.96f, 12.0f)
                curveToRelative(-0.18f, -0.39f, -0.73f, -0.39f, -0.91f, 0.0f)
                lineToRelative(-0.32f, 0.72f)
                lineTo(19.0f, 13.04f)
                curveToRelative(-0.39f, 0.18f, -0.39f, 0.73f, 0.0f, 0.91f)
                lineToRelative(0.72f, 0.32f)
                lineTo(20.04f, 15.0f)
                curveToRelative(0.18f, 0.39f, 0.73f, 0.39f, 0.91f, 0.0f)
                lineToRelative(0.32f, -0.72f)
                lineTo(22.0f, 13.96f)
                curveToRelative(0.39f, -0.18f, 0.39f, -0.73f, 0.0f, -0.91f)
                lineTo(21.28f, 12.72f)
                close()
                moveTo(16.24f, 14.37f)
                lineToRelative(1.23f, 0.93f)
                curveToRelative(0.4f, 0.3f, 0.51f, 0.86f, 0.26f, 1.3f)
                lineToRelative(-1.62f, 2.8f)
                curveToRelative(-0.25f, 0.44f, -0.79f, 0.62f, -1.25f, 0.42f)
                lineToRelative(-1.43f, -0.6f)
                curveToRelative(-0.2f, 0.13f, -0.42f, 0.26f, -0.64f, 0.37f)
                lineToRelative(-0.19f, 1.54f)
                curveToRelative(-0.06f, 0.5f, -0.49f, 0.88f, -0.99f, 0.88f)
                horizontalLineTo(8.38f)
                curveToRelative(-0.5f, 0.0f, -0.93f, -0.38f, -0.99f, -0.88f)
                lineTo(7.2f, 19.59f)
                curveToRelative(-0.22f, -0.11f, -0.43f, -0.23f, -0.64f, -0.37f)
                lineToRelative(-1.43f, 0.6f)
                curveToRelative(-0.46f, 0.2f, -1.0f, 0.02f, -1.25f, -0.42f)
                lineToRelative(-1.62f, -2.8f)
                curveToRelative(-0.25f, -0.44f, -0.14f, -0.99f, 0.26f, -1.3f)
                lineToRelative(1.23f, -0.93f)
                curveTo(3.75f, 14.25f, 3.75f, 14.12f, 3.75f, 14.0f)
                reflectiveCurveToRelative(0.0f, -0.25f, 0.01f, -0.37f)
                lineTo(2.53f, 12.7f)
                curveToRelative(-0.4f, -0.3f, -0.51f, -0.86f, -0.26f, -1.3f)
                lineToRelative(1.62f, -2.8f)
                curveToRelative(0.25f, -0.44f, 0.79f, -0.62f, 1.25f, -0.42f)
                lineToRelative(1.43f, 0.6f)
                curveToRelative(0.2f, -0.13f, 0.42f, -0.26f, 0.64f, -0.37f)
                lineToRelative(0.19f, -1.54f)
                curveTo(7.45f, 6.38f, 7.88f, 6.0f, 8.38f, 6.0f)
                horizontalLineToRelative(3.23f)
                curveToRelative(0.5f, 0.0f, 0.93f, 0.38f, 0.99f, 0.88f)
                lineToRelative(0.19f, 1.54f)
                curveToRelative(0.22f, 0.11f, 0.43f, 0.23f, 0.64f, 0.37f)
                lineToRelative(1.43f, -0.6f)
                curveToRelative(0.46f, -0.2f, 1.0f, -0.02f, 1.25f, 0.42f)
                lineToRelative(1.62f, 2.8f)
                curveToRelative(0.25f, 0.44f, 0.14f, 0.99f, -0.26f, 1.3f)
                lineToRelative(-1.23f, 0.93f)
                curveToRelative(0.01f, 0.12f, 0.01f, 0.24f, 0.01f, 0.37f)
                reflectiveCurveTo(16.25f, 14.25f, 16.24f, 14.37f)
                close()
                moveTo(13.0f, 14.0f)
                curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                reflectiveCurveTo(13.0f, 15.66f, 13.0f, 14.0f)
                close()
            }
        }
        _settingsSuggest = vec
        return vec
    }

private var _shield: ImageVector? = null
val Icons.Rounded.Shield: ImageVector
    get() {
        _shield?.let { return it }
        val vec = buildRoundedIcon("Rounded.Shield") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.3f, 2.26f)
                lineToRelative(-6.0f, 2.25f)
                curveTo(4.52f, 4.81f, 4.0f, 5.55f, 4.0f, 6.39f)
                verticalLineToRelative(4.7f)
                curveToRelative(0.0f, 4.83f, 3.13f, 9.37f, 7.43f, 10.75f)
                curveToRelative(0.37f, 0.12f, 0.77f, 0.12f, 1.14f, 0.0f)
                curveToRelative(4.3f, -1.38f, 7.43f, -5.91f, 7.43f, -10.75f)
                verticalLineToRelative(-4.7f)
                curveToRelative(0.0f, -0.83f, -0.52f, -1.58f, -1.3f, -1.87f)
                lineToRelative(-6.0f, -2.25f)
                curveTo(12.25f, 2.09f, 11.75f, 2.09f, 11.3f, 2.26f)
                close()
            }
        }
        _shield = vec
        return vec
    }

private var _skipNext: ImageVector? = null
val Icons.Rounded.SkipNext: ImageVector
    get() {
        _skipNext?.let { return it }
        val vec = buildRoundedIcon("Rounded.SkipNext") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.58f, 16.89f)
                lineToRelative(5.77f, -4.07f)
                curveToRelative(0.56f, -0.4f, 0.56f, -1.24f, 0.0f, -1.63f)
                lineTo(7.58f, 7.11f)
                curveTo(6.91f, 6.65f, 6.0f, 7.12f, 6.0f, 7.93f)
                verticalLineToRelative(8.14f)
                curveToRelative(0.0f, 0.81f, 0.91f, 1.28f, 1.58f, 0.82f)
                close()
                moveTo(16.0f, 7.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                close()
            }
        }
        _skipNext = vec
        return vec
    }

private var _skipPrevious: ImageVector? = null
val Icons.Rounded.SkipPrevious: ImageVector
    get() {
        _skipPrevious?.let { return it }
        val vec = buildRoundedIcon("Rounded.SkipPrevious") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.0f, 6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                reflectiveCurveToRelative(-1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(6.0f, 7.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(10.66f, 12.82f)
                lineToRelative(5.77f, 4.07f)
                curveToRelative(0.66f, 0.47f, 1.58f, -0.01f, 1.58f, -0.82f)
                lineTo(18.01f, 7.93f)
                curveToRelative(0.0f, -0.81f, -0.91f, -1.28f, -1.58f, -0.82f)
                lineToRelative(-5.77f, 4.07f)
                curveToRelative(-0.57f, 0.4f, -0.57f, 1.24f, 0.0f, 1.64f)
                close()
            }
        }
        _skipPrevious = vec
        return vec
    }

