package androidx.compose.material.icons.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

private var _traffic: ImageVector? = null
val Icons.Rounded.Traffic: ImageVector
    get() {
        _traffic?.let { return it }
        val vec = buildRoundedIcon("Rounded.Traffic") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.96f, 10.59f)
                curveToRelative(0.04f, -0.31f, -0.19f, -0.59f, -0.5f, -0.59f)
                lineTo(17.0f, 10.0f)
                lineTo(17.0f, 8.86f)
                curveToRelative(1.54f, -0.4f, 2.72f, -1.68f, 2.96f, -3.27f)
                curveToRelative(0.04f, -0.31f, -0.19f, -0.59f, -0.5f, -0.59f)
                lineTo(17.0f, 5.0f)
                lineTo(17.0f, 4.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(8.0f, 3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(1.0f)
                lineTo(4.54f, 5.0f)
                curveToRelative(-0.31f, 0.0f, -0.54f, 0.28f, -0.5f, 0.59f)
                curveTo(4.28f, 7.18f, 5.46f, 8.46f, 7.0f, 8.86f)
                lineTo(7.0f, 10.0f)
                lineTo(4.54f, 10.0f)
                curveToRelative(-0.31f, 0.0f, -0.54f, 0.28f, -0.5f, 0.59f)
                curveToRelative(0.24f, 1.59f, 1.42f, 2.87f, 2.96f, 3.27f)
                lineTo(7.0f, 15.0f)
                lineTo(4.54f, 15.0f)
                curveToRelative(-0.31f, 0.0f, -0.54f, 0.28f, -0.5f, 0.59f)
                curveToRelative(0.24f, 1.59f, 1.42f, 2.87f, 2.96f, 3.27f)
                lineTo(7.0f, 20.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-1.14f)
                curveToRelative(1.54f, -0.4f, 2.72f, -1.68f, 2.96f, -3.27f)
                curveToRelative(0.04f, -0.31f, -0.19f, -0.59f, -0.5f, -0.59f)
                lineTo(17.0f, 15.0f)
                verticalLineToRelative(-1.14f)
                curveToRelative(1.54f, -0.4f, 2.72f, -1.68f, 2.96f, -3.27f)
                close()
                moveTo(12.0f, 19.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.89f, -2.0f, 2.0f, -2.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.89f, 2.0f, -2.0f, 2.0f)
                close()
                moveTo(12.0f, 14.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.89f, -2.0f, 2.0f, -2.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.89f, 2.0f, -2.0f, 2.0f)
                close()
                moveTo(12.0f, 9.0f)
                curveToRelative(-1.11f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                curveToRelative(0.0f, -1.11f, 0.89f, -2.0f, 2.0f, -2.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.89f, 2.0f, 2.0f)
                curveToRelative(0.0f, 1.1f, -0.89f, 2.0f, -2.0f, 2.0f)
                close()
            }
        }
        _traffic = vec
        return vec
    }

private var _tv: ImageVector? = null
val Icons.Rounded.Tv: ImageVector
    get() {
        _tv?.let { return it }
        val vec = buildRoundedIcon("Rounded.Tv") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21.0f, 3.0f)
                lineTo(3.0f, 3.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(1.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(6.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-1.0f)
                horizontalLineToRelative(5.0f)
                curveToRelative(1.1f, 0.0f, 1.99f, -0.9f, 1.99f, -2.0f)
                lineTo(23.0f, 5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(20.0f, 17.0f)
                lineTo(4.0f, 17.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(3.0f, 6.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }
        _tv = vec
        return vec
    }

private var _tvOff: ImageVector? = null
val Icons.Rounded.TvOff: ImageVector
    get() {
        _tvOff?.let { return it }
        val vec = buildRoundedIcon("Rounded.TvOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21.0f, 8.0f)
                verticalLineToRelative(9.88f)
                lineToRelative(1.85f, 1.85f)
                curveToRelative(0.1f, -0.22f, 0.15f, -0.47f, 0.15f, -0.73f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -1.11f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-7.59f)
                lineToRelative(2.94f, -2.94f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.51f, 0.0f, -0.71f)
                reflectiveCurveToRelative(-0.51f, -0.2f, -0.71f, 0.0f)
                lineTo(12.0f, 4.99f)
                lineTo(8.36f, 1.35f)
                curveToRelative(-0.2f, -0.2f, -0.51f, -0.2f, -0.71f, 0.0f)
                reflectiveCurveToRelative(-0.2f, 0.51f, 0.0f, 0.71f)
                lineTo(10.59f, 5.0f)
                horizontalLineTo(8.12f)
                lineToRelative(2.0f, 2.0f)
                horizontalLineTo(20.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                close()
                moveTo(3.12f, 2.83f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(0.82f, 0.82f)
                curveTo(1.65f, 5.28f, 1.0f, 6.06f, 1.0f, 7.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(15.46f)
                lineToRelative(1.29f, 1.29f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                curveToRelative(0.36f, -0.36f, 0.37f, -0.92f, 0.07f, -1.31f)
                horizontalLineToRelative(0.03f)
                lineTo(3.12f, 2.83f)
                close()
                moveTo(3.0f, 18.0f)
                verticalLineTo(8.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                horizontalLineToRelative(0.46f)
                lineToRelative(12.0f, 12.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                close()
            }
        }
        _tvOff = vec
        return vec
    }

private var _verified: ImageVector? = null
val Icons.Rounded.Verified: ImageVector
    get() {
        _verified?.let { return it }
        val vec = buildRoundedIcon("Rounded.Verified") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(23.0f, 12.0f)
                lineToRelative(-2.44f, -2.79f)
                lineToRelative(0.34f, -3.69f)
                lineToRelative(-3.61f, -0.82f)
                lineTo(15.4f, 1.5f)
                lineTo(12.0f, 2.96f)
                lineTo(8.6f, 1.5f)
                lineTo(6.71f, 4.69f)
                lineTo(3.1f, 5.5f)
                lineTo(3.44f, 9.2f)
                lineTo(1.0f, 12.0f)
                lineToRelative(2.44f, 2.79f)
                lineToRelative(-0.34f, 3.7f)
                lineToRelative(3.61f, 0.82f)
                lineTo(8.6f, 22.5f)
                lineToRelative(3.4f, -1.47f)
                lineToRelative(3.4f, 1.46f)
                lineToRelative(1.89f, -3.19f)
                lineToRelative(3.61f, -0.82f)
                lineToRelative(-0.34f, -3.69f)
                lineTo(23.0f, 12.0f)
                close()
                moveTo(9.38f, 16.01f)
                lineTo(7.0f, 13.61f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                lineToRelative(0.07f, -0.07f)
                curveToRelative(0.39f, -0.39f, 1.03f, -0.39f, 1.42f, 0.0f)
                lineToRelative(1.61f, 1.62f)
                lineToRelative(5.15f, -5.16f)
                curveToRelative(0.39f, -0.39f, 1.03f, -0.39f, 1.42f, 0.0f)
                lineToRelative(0.07f, 0.07f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(-5.92f, 5.94f)
                curveTo(10.41f, 16.4f, 9.78f, 16.4f, 9.38f, 16.01f)
                close()
            }
        }
        _verified = vec
        return vec
    }

private var _verifiedUser: ImageVector? = null
val Icons.Rounded.VerifiedUser: ImageVector
    get() {
        _verifiedUser?.let { return it }
        val vec = buildRoundedIcon("Rounded.VerifiedUser") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.19f, 1.36f)
                lineToRelative(-7.0f, 3.11f)
                curveTo(3.47f, 4.79f, 3.0f, 5.51f, 3.0f, 6.3f)
                lineTo(3.0f, 11.0f)
                curveToRelative(0.0f, 5.55f, 3.84f, 10.74f, 9.0f, 12.0f)
                curveToRelative(5.16f, -1.26f, 9.0f, -6.45f, 9.0f, -12.0f)
                lineTo(21.0f, 6.3f)
                curveToRelative(0.0f, -0.79f, -0.47f, -1.51f, -1.19f, -1.83f)
                lineToRelative(-7.0f, -3.11f)
                curveToRelative(-0.51f, -0.23f, -1.11f, -0.23f, -1.62f, 0.0f)
                close()
                moveTo(9.29f, 16.29f)
                lineTo(6.7f, 13.7f)
                curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0.0f, -1.41f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                lineTo(10.0f, 14.17f)
                lineToRelative(5.88f, -5.88f)
                curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0.0f)
                curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(-6.59f, 6.59f)
                curveToRelative(-0.38f, 0.39f, -1.02f, 0.39f, -1.41f, 0.0f)
                close()
            }
        }
        _verifiedUser = vec
        return vec
    }

private var _videoLibrary: ImageVector? = null
val Icons.Rounded.VideoLibrary: ImageVector
    get() {
        _videoLibrary?.let { return it }
        val vec = buildRoundedIcon("Rounded.VideoLibrary") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 6.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(13.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(13.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(5.0f, 20.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                lineTo(4.0f, 7.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                close()
                moveTo(20.0f, 2.0f)
                lineTo(8.0f, 2.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(22.0f, 4.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(12.0f, 14.5f)
                verticalLineToRelative(-9.0f)
                lineToRelative(5.47f, 4.1f)
                curveToRelative(0.27f, 0.2f, 0.27f, 0.6f, 0.0f, 0.8f)
                lineTo(12.0f, 14.5f)
                close()
            }
        }
        _videoLibrary = vec
        return vec
    }

private var _viewCarousel: ImageVector? = null
val Icons.Rounded.ViewCarousel: ImageVector
    get() {
        _viewCarousel?.let { return it }
        val vec = buildRoundedIcon("Rounded.ViewCarousel") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 7.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(8.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineTo(3.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineTo(8.0f)
                curveTo(2.0f, 7.45f, 2.45f, 7.0f, 3.0f, 7.0f)
                close()
                moveTo(8.0f, 19.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(8.0f)
                curveTo(7.45f, 5.0f, 7.0f, 5.45f, 7.0f, 6.0f)
                verticalLineToRelative(12.0f)
                curveTo(7.0f, 18.55f, 7.45f, 19.0f, 8.0f, 19.0f)
                close()
                moveTo(19.0f, 7.0f)
                horizontalLineToRelative(2.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineToRelative(8.0f)
                curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-2.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                verticalLineTo(8.0f)
                curveTo(18.0f, 7.45f, 18.45f, 7.0f, 19.0f, 7.0f)
                close()
            }
        }
        _viewCarousel = vec
        return vec
    }

private var _viewStream: ImageVector? = null
val Icons.Rounded.ViewStream: ImageVector
    get() {
        _viewStream?.let { return it }
        val vec = buildRoundedIcon("Rounded.ViewStream") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.0f, 17.0f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -1.1f, 0.9f, -2.0f, 2.0f, -2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 19.0f, 3.0f, 18.1f, 3.0f, 17.0f)
                close()
                moveTo(3.0f, 7.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 5.0f, 3.0f, 5.9f, 3.0f, 7.0f)
                close()
            }
        }
        _viewStream = vec
        return vec
    }

private var _visibility: ImageVector? = null
val Icons.Rounded.Visibility: ImageVector
    get() {
        _visibility?.let { return it }
        val vec = buildRoundedIcon("Rounded.Visibility") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 4.0f)
                curveTo(7.0f, 4.0f, 2.73f, 7.11f, 1.0f, 11.5f)
                curveTo(2.73f, 15.89f, 7.0f, 19.0f, 12.0f, 19.0f)
                reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
                curveTo(21.27f, 7.11f, 17.0f, 4.0f, 12.0f, 4.0f)
                close()
                moveTo(12.0f, 16.5f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                close()
                moveTo(12.0f, 8.5f)
                curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
                reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                close()
            }
        }
        _visibility = vec
        return vec
    }

private var _visibilityOff: ImageVector? = null
val Icons.Rounded.VisibilityOff: ImageVector
    get() {
        _visibilityOff?.let { return it }
        val vec = buildRoundedIcon("Rounded.VisibilityOff") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 6.5f)
                curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
                curveToRelative(0.0f, 0.51f, -0.1f, 1.0f, -0.24f, 1.46f)
                lineToRelative(3.06f, 3.06f)
                curveToRelative(1.39f, -1.23f, 2.49f, -2.77f, 3.18f, -4.53f)
                curveTo(21.27f, 7.11f, 17.0f, 4.0f, 12.0f, 4.0f)
                curveToRelative(-1.27f, 0.0f, -2.49f, 0.2f, -3.64f, 0.57f)
                lineToRelative(2.17f, 2.17f)
                curveToRelative(0.47f, -0.14f, 0.96f, -0.24f, 1.47f, -0.24f)
                close()
                moveTo(2.71f, 3.16f)
                curveToRelative(-0.39f, 0.39f, -0.39f, 1.02f, 0.0f, 1.41f)
                lineToRelative(1.97f, 1.97f)
                curveTo(3.06f, 7.83f, 1.77f, 9.53f, 1.0f, 11.5f)
                curveTo(2.73f, 15.89f, 7.0f, 19.0f, 12.0f, 19.0f)
                curveToRelative(1.52f, 0.0f, 2.97f, -0.3f, 4.31f, -0.82f)
                lineToRelative(2.72f, 2.72f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(4.13f, 3.16f)
                curveToRelative(-0.39f, -0.39f, -1.03f, -0.39f, -1.42f, 0.0f)
                close()
                moveTo(12.0f, 16.5f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                curveToRelative(0.0f, -0.77f, 0.18f, -1.5f, 0.49f, -2.14f)
                lineToRelative(1.57f, 1.57f)
                curveToRelative(-0.03f, 0.18f, -0.06f, 0.37f, -0.06f, 0.57f)
                curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
                curveToRelative(0.2f, 0.0f, 0.38f, -0.03f, 0.57f, -0.07f)
                lineTo(14.14f, 16.0f)
                curveToRelative(-0.65f, 0.32f, -1.37f, 0.5f, -2.14f, 0.5f)
                close()
                moveTo(14.97f, 11.17f)
                curveToRelative(-0.15f, -1.4f, -1.25f, -2.49f, -2.64f, -2.64f)
                lineToRelative(2.64f, 2.64f)
                close()
            }
        }
        _visibilityOff = vec
        return vec
    }

