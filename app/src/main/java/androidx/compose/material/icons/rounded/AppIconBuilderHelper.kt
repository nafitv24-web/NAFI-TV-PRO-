package androidx.compose.material.icons.rounded

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

internal inline fun buildRoundedIcon(
    name: String,
    autoMirror: Boolean = false,
    block: ImageVector.Builder.() -> Unit
): ImageVector {
    return ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = autoMirror
    ).apply(block).build()
}
