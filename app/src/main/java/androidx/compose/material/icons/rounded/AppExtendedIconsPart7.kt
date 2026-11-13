package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _lockReset: ImageVector? = null
val Icons.Rounded.LockReset: ImageVector
    get() {
        _lockReset?.let { return it }
        val vec = buildRoundedIcon("Rounded.LockReset") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13.26f, 3.0f)
                curveTo(8.17f, 2.86f, 4.0f, 6.94f, 4.0f, 12.0f)
                horizontalLineTo(2.21f)
                curveToRelative(-0.45f, 0.0f, -0.67f, 0.54f, -0.35f, 0.85f)
                lineToRelative(2.79f, 2.79f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineToRelative(2.8f, -2.79f)
                curveTo(8.46f, 12.54f, 8.24f, 12.0f, 7.79f, 12.0f)
                horizontalLineTo(6.0f)
                curveToRelative(0.0f, -3.89f, 3.2f, -7.06f, 7.1f, -7.0f)
                curveToRelative(3.71f, 0.05f, 6.84f, 3.18f, 6.9f, 6.9f)
                curveToRelative(0.06f, 3.91f, -3.1f, 7.1f, -7.0f, 7.1f)
                curveToRelative(-1.59f, 0.0f, -3.05f, -0.53f, -4.23f, -1.43f)
                curveToRelative(-0.4f, -0.3f, -0.96f, -0.27f, -1.31f, 0.09f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(-0.43f, 0.43f, -0.39f, 1.14f, 0.09f, 1.5f)
                curveTo(9.06f, 20.31f, 10.95f, 21.0f, 13.0f, 21.0f)
                curveToRelative(5.06f, 0.0f, 9.14f, -4.17f, 9.0f, -9.25f)
                curveTo(21.87f, 7.05f, 17.95f, 3.13f, 13.26f, 3.0f)
                close()
                moveTo(15.0f, 11.0f)
                verticalLineToRelative(-1.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(1.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveTo(16.0f, 11.45f, 15.55f, 11.0f, 15.0f, 11.0f)
                close()
                moveTo(14.0f, 11.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-1.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineTo(11.0f)
                close()
            }
        }
        _lockReset = vec
        return vec
    }

private var _login: ImageVector? = null
val Icons.Rounded.Login: ImageVector
    get() {
        _login?.let { return it }
        val vec = buildRoundedIcon("Rounded.Login") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.3f, 7.7f)
                lineTo(10.3f, 7.7f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.01f, 0.0f, 1.4f)
                lineToRelative(1.9f, 1.9f)
                horizontalLineTo(3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(9.2f)
                lineToRelative(-1.9f, 1.9f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.01f, 0.0f, 1.4f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(0.39f, 0.39f, 1.01f, 0.39f, 1.4f, 0.0f)
                lineToRelative(3.59f, -3.59f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(11.7f, 7.7f)
                curveTo(11.31f, 7.31f, 10.69f, 7.31f, 10.3f, 7.7f)
                close()
                moveTo(20.0f, 19.0f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(7.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(7.0f)
                verticalLineTo(19.0f)
                close()
            }
        }
        _login = vec
        return vec
    }

private var _logout: ImageVector? = null
val Icons.Rounded.Logout: ImageVector
    get() {
        _logout?.let { return it }
        val vec = buildRoundedIcon("Rounded.Logout") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.0f, 5.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 3.0f, 3.0f, 3.9f, 3.0f, 5.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(20.65f, 11.65f)
                lineToRelative(-2.79f, -2.79f)
                curveTo(17.54f, 8.54f, 17.0f, 8.76f, 17.0f, 9.21f)
                verticalLineTo(11.0f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(1.79f)
                curveToRelative(0.0f, 0.45f, 0.54f, 0.67f, 0.85f, 0.35f)
                lineToRelative(2.79f, -2.79f)
                curveTo(20.84f, 12.16f, 20.84f, 11.84f, 20.65f, 11.65f)
                close()
            }
        }
        _logout = vec
        return vec
    }

private var _menuOpen: ImageVector? = null
val Icons.Rounded.MenuOpen: ImageVector
    get() {
        _menuOpen?.let { return it }
        val vec = buildRoundedIcon("Rounded.MenuOpen") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4.0f, 18.0f)
                horizontalLineToRelative(11.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveTo(3.0f, 17.55f, 3.45f, 18.0f, 4.0f, 18.0f)
                close()
                moveTo(4.0f, 13.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(0.0f)
                curveTo(3.0f, 12.55f, 3.45f, 13.0f, 4.0f, 13.0f)
                close()
                moveTo(3.0f, 7.0f)
                lineTo(3.0f, 7.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(11.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(4.0f)
                curveTo(3.45f, 6.0f, 3.0f, 6.45f, 3.0f, 7.0f)
                close()
                moveTo(20.3f, 14.88f)
                lineTo(17.42f, 12.0f)
                lineToRelative(2.88f, -2.88f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                lineToRelative(-3.59f, 3.59f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(3.59f, 3.59f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                curveTo(20.68f, 15.91f, 20.69f, 15.27f, 20.3f, 14.88f)
                close()
            }
        }
        _menuOpen = vec
        return vec
    }

private var _movie: ImageVector? = null
val Icons.Rounded.Movie: ImageVector
    get() {
        _movie?.let { return it }
        val vec = buildRoundedIcon("Rounded.Movie") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.0f, 4.0f)
                lineToRelative(1.82f, 3.64f)
                curveToRelative(0.08f, 0.16f, -0.04f, 0.36f, -0.22f, 0.36f)
                horizontalLineToRelative(-1.98f)
                curveToRelative(-0.38f, 0.0f, -0.73f, -0.21f, -0.89f, -0.55f)
                lineTo(15.0f, 4.0f)
                horizontalLineToRelative(-2.0f)
                lineToRelative(1.82f, 3.64f)
                curveToRelative(0.08f, 0.16f, -0.04f, 0.36f, -0.22f, 0.36f)
                horizontalLineToRelative(-1.98f)
                curveToRelative(-0.38f, 0.0f, -0.73f, -0.21f, -0.89f, -0.55f)
                lineTo(10.0f, 4.0f)
                horizontalLineTo(8.0f)
                lineToRelative(1.82f, 3.64f)
                curveToRelative(0.08f, 0.16f, -0.04f, 0.36f, -0.22f, 0.36f)
                horizontalLineTo(7.62f)
                curveToRelative(-0.38f, 0.0f, -0.73f, -0.21f, -0.9f, -0.55f)
                lineTo(5.0f, 4.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-3.0f)
                close()
            }
        }
        _movie = vec
        return vec
    }

private var _movieFilter: ImageVector? = null
val Icons.Rounded.MovieFilter: ImageVector
    get() {
        _movieFilter?.let { return it }
        val vec = buildRoundedIcon("Rounded.MovieFilter") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21.5f, 4.0f)
                lineTo(18.0f, 4.0f)
                lineToRelative(1.74f, 2.61f)
                curveToRelative(0.11f, 0.17f, -0.01f, 0.39f, -0.21f, 0.39f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.33f, 0.0f, -0.65f, -0.17f, -0.83f, -0.45f)
                lineTo(15.0f, 4.0f)
                horizontalLineToRelative(-2.0f)
                lineToRelative(1.74f, 2.61f)
                curveToRelative(0.11f, 0.17f, -0.01f, 0.39f, -0.21f, 0.39f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.33f, 0.0f, -0.65f, -0.17f, -0.83f, -0.45f)
                lineTo(10.0f, 4.0f)
                lineTo(8.0f, 4.0f)
                lineToRelative(1.74f, 2.61f)
                curveToRelative(0.11f, 0.17f, -0.01f, 0.39f, -0.21f, 0.39f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.33f, 0.0f, -0.64f, -0.17f, -0.83f, -0.45f)
                lineTo(5.0f, 4.0f)
                lineTo(4.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 4.5f)
                curveToRelative(0.0f, -0.28f, -0.22f, -0.5f, -0.5f, -0.5f)
                close()
                moveTo(11.25f, 15.25f)
                lineTo(10.0f, 18.0f)
                lineToRelative(-1.25f, -2.75f)
                lineTo(6.0f, 14.0f)
                lineToRelative(2.75f, -1.25f)
                lineTo(10.0f, 10.0f)
                lineToRelative(1.25f, 2.75f)
                lineTo(14.0f, 14.0f)
                lineToRelative(-2.75f, 1.25f)
                close()
                moveTo(16.94f, 11.94f)
                lineTo(16.0f, 14.0f)
                lineToRelative(-0.94f, -2.06f)
                lineTo(13.0f, 11.0f)
                lineToRelative(2.06f, -0.94f)
                lineTo(16.0f, 8.0f)
                lineToRelative(0.94f, 2.06f)
                lineTo(19.0f, 11.0f)
                lineToRelative(-2.06f, 0.94f)
                close()
            }
        }
        _movieFilter = vec
        return vec
    }

private var _newReleases: ImageVector? = null
val Icons.Rounded.NewReleases: ImageVector
    get() {
        _newReleases?.let { return it }
        val vec = buildRoundedIcon("Rounded.NewReleases") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(22.42f, 11.34f)
                lineToRelative(-1.86f, -2.12f)
                lineToRelative(0.26f, -2.81f)
                curveToRelative(0.05f, -0.5f, -0.29f, -0.96f, -0.77f, -1.07f)
                lineToRelative(-2.76f, -0.63f)
                lineToRelative(-1.44f, -2.43f)
                curveToRelative(-0.26f, -0.43f, -0.79f, -0.61f, -1.25f, -0.41f)
                lineTo(12.0f, 3.0f)
                lineTo(9.41f, 1.89f)
                curveToRelative(-0.46f, -0.2f, -1.0f, -0.02f, -1.25f, 0.41f)
                lineTo(6.71f, 4.72f)
                lineToRelative(-2.75f, 0.62f)
                curveToRelative(-0.49f, 0.11f, -0.83f, 0.56f, -0.78f, 1.07f)
                lineToRelative(0.26f, 2.8f)
                lineToRelative(-1.86f, 2.13f)
                curveToRelative(-0.33f, 0.38f, -0.33f, 0.94f, 0.0f, 1.32f)
                lineToRelative(1.86f, 2.12f)
                lineToRelative(-0.26f, 2.82f)
                curveToRelative(-0.05f, 0.5f, 0.29f, 0.96f, 0.77f, 1.07f)
                lineToRelative(2.76f, 0.63f)
                lineToRelative(1.44f, 2.42f)
                curveToRelative(0.26f, 0.43f, 0.79f, 0.61f, 1.26f, 0.41f)
                lineTo(12.0f, 21.0f)
                lineToRelative(2.59f, 1.11f)
                curveToRelative(0.46f, 0.2f, 1.0f, 0.02f, 1.25f, -0.41f)
                lineToRelative(1.44f, -2.43f)
                lineToRelative(2.76f, -0.63f)
                curveToRelative(0.49f, -0.11f, 0.82f, -0.57f, 0.77f, -1.07f)
                lineToRelative(-0.26f, -2.81f)
                lineToRelative(1.86f, -2.12f)
                curveToRelative(0.34f, -0.36f, 0.34f, -0.92f, 0.01f, -1.3f)
                close()
                moveTo(13.0f, 17.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(12.0f, 13.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(11.0f, 8.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _newReleases = vec
        return vec
    }

private var _notificationsActive: ImageVector? = null
val Icons.Rounded.NotificationsActive: ImageVector
    get() {
        _notificationsActive?.let { return it }
        val vec = buildRoundedIcon("Rounded.NotificationsActive") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18.0f, 16.0f)
                verticalLineToRelative(-5.0f)
                curveToRelative(0.0f, -3.07f, -1.64f, -5.64f, -4.5f, -6.32f)
                lineTo(13.5f, 4.0f)
                curveToRelative(0.0f, -0.83f, -0.68f, -1.5f, -1.51f, -1.5f)
                reflectiveCurveTo(10.5f, 3.17f, 10.5f, 4.0f)
                verticalLineToRelative(0.68f)
                curveTo(7.63f, 5.36f, 6.0f, 7.92f, 6.0f, 11.0f)
                verticalLineToRelative(5.0f)
                lineToRelative(-1.3f, 1.29f)
                curveToRelative(-0.63f, 0.63f, -0.19f, 1.71f, 0.7f, 1.71f)
                horizontalLineToRelative(13.17f)
                curveToRelative(0.89f, 0.0f, 1.34f, -1.08f, 0.71f, -1.71f)
                lineTo(18.0f, 16.0f)
                close()
                moveTo(11.99f, 22.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(6.77f, 4.73f)
                curveToRelative(0.42f, -0.38f, 0.43f, -1.03f, 0.03f, -1.43f)
                curveToRelative(-0.38f, -0.38f, -1.0f, -0.39f, -1.39f, -0.02f)
                curveTo(3.7f, 4.84f, 2.52f, 6.96f, 2.14f, 9.34f)
                curveToRelative(-0.09f, 0.61f, 0.38f, 1.16f, 1.0f, 1.16f)
                curveToRelative(0.48f, 0.0f, 0.9f, -0.35f, 0.98f, -0.83f)
                curveToRelative(0.3f, -1.94f, 1.26f, -3.67f, 2.65f, -4.94f)
                close()
                moveTo(18.6f, 3.28f)
                curveToRelative(-0.4f, -0.37f, -1.02f, -0.36f, -1.4f, 0.02f)
                curveToRelative(-0.4f, 0.4f, -0.38f, 1.04f, 0.03f, 1.42f)
                curveToRelative(1.38f, 1.27f, 2.35f, 3.0f, 2.65f, 4.94f)
                curveToRelative(0.07f, 0.48f, 0.49f, 0.83f, 0.98f, 0.83f)
                curveToRelative(0.61f, 0.0f, 1.09f, -0.55f, 0.99f, -1.16f)
                curveToRelative(-0.38f, -2.37f, -1.55f, -4.48f, -3.25f, -6.05f)
                close()
            }
        }
        _notificationsActive = vec
        return vec
    }

