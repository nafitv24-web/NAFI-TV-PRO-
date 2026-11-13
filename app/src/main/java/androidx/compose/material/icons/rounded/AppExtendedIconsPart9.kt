package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _playlistAdd: ImageVector? = null
val Icons.Rounded.PlaylistAdd: ImageVector
    get() {
        _playlistAdd?.let { return it }
        val vec = buildRoundedIcon("Rounded.PlaylistAdd") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13.0f, 10.0f)
                lineTo(3.0f, 10.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(13.0f, 6.0f)
                lineTo(3.0f, 6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(18.0f, 14.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(-3.0f)
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
                close()
                moveTo(3.0f, 16.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(3.0f, 14.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                close()
            }
        }
        _playlistAdd = vec
        return vec
    }

private var _playlistPlay: ImageVector? = null
val Icons.Rounded.PlaylistPlay: ImageVector
    get() {
        _playlistPlay?.let { return it }
        val vec = buildRoundedIcon("Rounded.PlaylistPlay") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5.0f, 10.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(5.0f, 12.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(5.0f, 6.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(5.0f, 8.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(5.0f, 14.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                lineTo(5.0f, 16.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(14.0f, 14.88f)
                verticalLineToRelative(4.23f)
                curveToRelative(0.0f, 0.39f, 0.42f, 0.63f, 0.76f, 0.43f)
                lineToRelative(3.53f, -2.12f)
                curveToRelative(0.32f, -0.19f, 0.32f, -0.66f, 0.0f, -0.86f)
                lineToRelative(-3.53f, -2.12f)
                curveToRelative(-0.34f, -0.19f, -0.76f, 0.05f, -0.76f, 0.44f)
                close()
            }
        }
        _playlistPlay = vec
        return vec
    }

private var _public: ImageVector? = null
val Icons.Rounded.Public: ImageVector
    get() {
        _public?.let { return it }
        val vec = buildRoundedIcon("Rounded.Public") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(11.0f, 19.93f)
                curveToRelative(-3.95f, -0.49f, -7.0f, -3.85f, -7.0f, -7.93f)
                curveToRelative(0.0f, -0.62f, 0.08f, -1.21f, 0.21f, -1.79f)
                lineTo(9.0f, 15.0f)
                verticalLineToRelative(1.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                verticalLineToRelative(1.93f)
                close()
                moveTo(17.9f, 17.39f)
                curveToRelative(-0.26f, -0.81f, -1.0f, -1.39f, -1.9f, -1.39f)
                horizontalLineToRelative(-1.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(8.0f, 12.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                lineTo(11.0f, 7.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-0.41f)
                curveToRelative(2.93f, 1.19f, 5.0f, 4.06f, 5.0f, 7.41f)
                curveToRelative(0.0f, 2.08f, -0.8f, 3.97f, -2.1f, 5.39f)
                close()
            }
        }
        _public = vec
        return vec
    }

private var _queueMusic: ImageVector? = null
val Icons.Rounded.QueueMusic: ImageVector
    get() {
        _queueMusic?.let { return it }
        val vec = buildRoundedIcon("Rounded.QueueMusic") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(14.0f, 6.0f)
                lineTo(4.0f, 6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(14.0f, 10.0f)
                lineTo(4.0f, 10.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(4.0f, 16.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(4.0f, 14.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
                close()
                moveTo(19.0f, 6.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(6.18f)
                curveToRelative(-0.31f, -0.11f, -0.65f, -0.18f, -1.0f, -0.18f)
                curveToRelative(-1.84f, 0.0f, -3.28f, 1.64f, -2.95f, 3.54f)
                curveToRelative(0.21f, 1.21f, 1.2f, 2.2f, 2.41f, 2.41f)
                curveToRelative(1.9f, 0.33f, 3.54f, -1.11f, 3.54f, -2.95f)
                lineTo(19.0f, 8.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }
        _queueMusic = vec
        return vec
    }

private var _replay10: ImageVector? = null
val Icons.Rounded.Replay10: ImageVector
    get() {
        _replay10?.let { return it }
        val vec = buildRoundedIcon("Rounded.Replay10") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.99f, 5.0f)
                lineTo(11.99f, 2.21f)
                curveToRelative(0.0f, -0.45f, -0.54f, -0.67f, -0.85f, -0.35f)
                lineTo(7.35f, 5.65f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(3.79f, 3.79f)
                curveToRelative(0.31f, 0.31f, 0.85f, 0.09f, 0.85f, -0.35f)
                lineTo(11.99f, 7.0f)
                curveToRelative(3.73f, 0.0f, 6.68f, 3.42f, 5.86f, 7.29f)
                curveToRelative(-0.47f, 2.27f, -2.31f, 4.1f, -4.57f, 4.57f)
                curveToRelative(-3.57f, 0.75f, -6.75f, -1.7f, -7.23f, -5.01f)
                curveToRelative(-0.06f, -0.48f, -0.48f, -0.85f, -0.98f, -0.85f)
                curveToRelative(-0.6f, 0.0f, -1.08f, 0.53f, -1.0f, 1.13f)
                curveToRelative(0.62f, 4.39f, 4.8f, 7.64f, 9.53f, 6.72f)
                curveToRelative(3.12f, -0.61f, 5.63f, -3.12f, 6.24f, -6.24f)
                curveToRelative(0.99f, -5.13f, -2.9f, -9.61f, -7.85f, -9.61f)
                close()
                moveTo(10.89f, 16.0f)
                horizontalLineToRelative(-0.85f)
                verticalLineToRelative(-3.26f)
                lineToRelative(-1.01f, 0.31f)
                verticalLineToRelative(-0.69f)
                lineToRelative(1.77f, -0.63f)
                horizontalLineToRelative(0.09f)
                lineTo(10.89f, 16.0f)
                close()
                moveTo(15.17f, 14.24f)
                curveToRelative(0.0f, 0.32f, -0.03f, 0.6f, -0.1f, 0.82f)
                reflectiveCurveToRelative(-0.17f, 0.42f, -0.29f, 0.57f)
                reflectiveCurveToRelative(-0.28f, 0.26f, -0.45f, 0.33f)
                reflectiveCurveToRelative(-0.37f, 0.1f, -0.59f, 0.1f)
                reflectiveCurveToRelative(-0.41f, -0.03f, -0.59f, -0.1f)
                reflectiveCurveToRelative(-0.33f, -0.18f, -0.46f, -0.33f)
                reflectiveCurveToRelative(-0.23f, -0.34f, -0.3f, -0.57f)
                reflectiveCurveToRelative(-0.11f, -0.5f, -0.11f, -0.82f)
                verticalLineToRelative(-0.74f)
                curveToRelative(0.0f, -0.32f, 0.03f, -0.6f, 0.1f, -0.82f)
                reflectiveCurveToRelative(0.17f, -0.42f, 0.29f, -0.57f)
                reflectiveCurveToRelative(0.28f, -0.26f, 0.45f, -0.33f)
                reflectiveCurveToRelative(0.37f, -0.1f, 0.59f, -0.1f)
                reflectiveCurveToRelative(0.41f, 0.03f, 0.59f, 0.1f)
                reflectiveCurveToRelative(0.33f, 0.18f, 0.46f, 0.33f)
                reflectiveCurveToRelative(0.23f, 0.34f, 0.3f, 0.57f)
                reflectiveCurveToRelative(0.11f, 0.5f, 0.11f, 0.82f)
                verticalLineToRelative(0.74f)
                close()
                moveTo(14.32f, 13.38f)
                curveToRelative(0.0f, -0.19f, -0.01f, -0.35f, -0.04f, -0.48f)
                reflectiveCurveToRelative(-0.07f, -0.23f, -0.12f, -0.31f)
                reflectiveCurveToRelative(-0.11f, -0.14f, -0.19f, -0.17f)
                reflectiveCurveToRelative(-0.16f, -0.05f, -0.25f, -0.05f)
                reflectiveCurveToRelative(-0.18f, 0.02f, -0.25f, 0.05f)
                reflectiveCurveToRelative(-0.14f, 0.09f, -0.19f, 0.17f)
                reflectiveCurveToRelative(-0.09f, 0.18f, -0.12f, 0.31f)
                reflectiveCurveToRelative(-0.04f, 0.29f, -0.04f, 0.48f)
                verticalLineToRelative(0.97f)
                curveToRelative(0.0f, 0.19f, 0.01f, 0.35f, 0.04f, 0.48f)
                reflectiveCurveToRelative(0.07f, 0.24f, 0.12f, 0.32f)
                reflectiveCurveToRelative(0.11f, 0.14f, 0.19f, 0.17f)
                reflectiveCurveToRelative(0.16f, 0.05f, 0.25f, 0.05f)
                reflectiveCurveToRelative(0.18f, -0.02f, 0.25f, -0.05f)
                reflectiveCurveToRelative(0.14f, -0.09f, 0.19f, -0.17f)
                reflectiveCurveToRelative(0.09f, -0.19f, 0.11f, -0.32f)
                reflectiveCurveToRelative(0.04f, -0.29f, 0.04f, -0.48f)
                verticalLineToRelative(-0.97f)
                close()
            }
        }
        _replay10 = vec
        return vec
    }

private var _restartAlt: ImageVector? = null
val Icons.Rounded.RestartAlt: ImageVector
    get() {
        _restartAlt?.let { return it }
        val vec = buildRoundedIcon("Rounded.RestartAlt") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 5.0f)
                verticalLineTo(3.21f)
                curveToRelative(0.0f, -0.45f, -0.54f, -0.67f, -0.85f, -0.35f)
                lineTo(8.35f, 5.65f)
                curveToRelative(-0.2f, 0.2f, -0.2f, 0.51f, 0.0f, 0.71f)
                lineToRelative(2.79f, 2.79f)
                curveTo(11.46f, 9.46f, 12.0f, 9.24f, 12.0f, 8.79f)
                verticalLineTo(7.0f)
                curveToRelative(3.31f, 0.0f, 6.0f, 2.69f, 6.0f, 6.0f)
                curveToRelative(0.0f, 2.72f, -1.83f, 5.02f, -4.31f, 5.75f)
                curveTo(13.27f, 18.87f, 13.0f, 19.27f, 13.0f, 19.7f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, 0.65f, 0.62f, 1.16f, 1.25f, 0.97f)
                curveTo(17.57f, 19.7f, 20.0f, 16.64f, 20.0f, 13.0f)
                curveTo(20.0f, 8.58f, 16.42f, 5.0f, 12.0f, 5.0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.0f, 13.0f)
                curveToRelative(0.0f, -1.34f, 0.44f, -2.58f, 1.19f, -3.59f)
                curveToRelative(0.3f, -0.4f, 0.26f, -0.95f, -0.09f, -1.31f)
                lineToRelative(0.0f, 0.0f)
                curveTo(6.68f, 7.68f, 5.96f, 7.72f, 5.6f, 8.2f)
                curveTo(4.6f, 9.54f, 4.0f, 11.2f, 4.0f, 13.0f)
                curveToRelative(0.0f, 3.64f, 2.43f, 6.7f, 5.75f, 7.67f)
                curveTo(10.38f, 20.86f, 11.0f, 20.35f, 11.0f, 19.7f)
                verticalLineToRelative(0.0f)
                curveToRelative(0.0f, -0.43f, -0.27f, -0.83f, -0.69f, -0.95f)
                curveTo(7.83f, 18.02f, 6.0f, 15.72f, 6.0f, 13.0f)
                close()
            }
        }
        _restartAlt = vec
        return vec
    }

private var _rocketLaunch: ImageVector? = null
val Icons.Rounded.RocketLaunch: ImageVector
    get() {
        _rocketLaunch?.let { return it }
        val vec = buildRoundedIcon("Rounded.RocketLaunch") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.19f, 6.35f)
                curveToRelative(-2.04f, 2.29f, -3.44f, 5.58f, -3.57f, 5.89f)
                lineToRelative(-2.26f, -0.97f)
                curveToRelative(-0.65f, -0.28f, -0.81f, -1.13f, -0.31f, -1.63f)
                lineToRelative(3.01f, -3.01f)
                curveToRelative(0.47f, -0.47f, 1.15f, -0.68f, 1.81f, -0.55f)
                lineTo(9.19f, 6.35f)
                lineTo(9.19f, 6.35f)
                close()
                moveTo(10.68f, 16.51f)
                curveToRelative(0.3f, 0.3f, 0.74f, 0.38f, 1.12f, 0.2f)
                curveToRelative(1.16f, -0.54f, 3.65f, -1.81f, 5.26f, -3.42f)
                curveToRelative(4.59f, -4.59f, 4.63f, -8.33f, 4.36f, -9.93f)
                curveToRelative(-0.07f, -0.4f, -0.39f, -0.72f, -0.79f, -0.79f)
                curveToRelative(-1.6f, -0.27f, -5.34f, -0.23f, -9.93f, 4.36f)
                curveToRelative(-1.61f, 1.61f, -2.87f, 4.1f, -3.42f, 5.26f)
                curveToRelative(-0.18f, 0.38f, -0.09f, 0.83f, 0.2f, 1.12f)
                lineTo(10.68f, 16.51f)
                close()
                moveTo(17.65f, 14.81f)
                curveToRelative(-2.29f, 2.04f, -5.58f, 3.44f, -5.89f, 3.57f)
                lineToRelative(0.97f, 2.26f)
                curveToRelative(0.28f, 0.65f, 1.13f, 0.81f, 1.63f, 0.31f)
                lineToRelative(3.01f, -3.01f)
                curveToRelative(0.47f, -0.47f, 0.68f, -1.15f, 0.55f, -1.81f)
                lineTo(17.65f, 14.81f)
                lineTo(17.65f, 14.81f)
                close()
                moveTo(8.94f, 17.41f)
                curveToRelative(0.2f, 1.06f, -0.15f, 2.04f, -0.82f, 2.71f)
                curveToRelative(-0.77f, 0.77f, -3.16f, 1.34f, -4.71f, 1.64f)
                curveToRelative(-0.69f, 0.13f, -1.3f, -0.48f, -1.17f, -1.17f)
                curveToRelative(0.3f, -1.55f, 0.86f, -3.94f, 1.64f, -4.71f)
                curveToRelative(0.67f, -0.67f, 1.65f, -1.02f, 2.71f, -0.82f)
                curveTo(7.76f, 15.28f, 8.72f, 16.24f, 8.94f, 17.41f)
                close()
                moveTo(13.0f, 9.0f)
                curveToRelative(0.0f, -1.1f, 0.9f, -2.0f, 2.0f, -2.0f)
                reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                reflectiveCurveTo(13.0f, 10.1f, 13.0f, 9.0f)
                close()
            }
        }
        _rocketLaunch = vec
        return vec
    }

private var _save: ImageVector? = null
val Icons.Rounded.Save: ImageVector
    get() {
        _save?.let { return it }
        val vec = buildRoundedIcon("Rounded.Save") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.59f, 3.59f)
                curveToRelative(-0.38f, -0.38f, -0.89f, -0.59f, -1.42f, -0.59f)
                lineTo(5.0f, 3.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(21.0f, 7.83f)
                curveToRelative(0.0f, -0.53f, -0.21f, -1.04f, -0.59f, -1.41f)
                lineToRelative(-2.82f, -2.83f)
                close()
                moveTo(12.0f, 19.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, -1.34f, -3.0f, -3.0f)
                reflectiveCurveToRelative(1.34f, -3.0f, 3.0f, -3.0f)
                reflectiveCurveToRelative(3.0f, 1.34f, 3.0f, 3.0f)
                reflectiveCurveToRelative(-1.34f, 3.0f, -3.0f, 3.0f)
                close()
                moveTo(13.0f, 9.0f)
                lineTo(7.0f, 9.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                close()
            }
        }
        _save = vec
        return vec
    }

