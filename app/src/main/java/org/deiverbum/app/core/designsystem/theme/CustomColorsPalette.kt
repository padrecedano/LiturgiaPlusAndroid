package org.deiverbum.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val rubricColor: Color = Color.Unspecified,
)


val LightRubricColor = Red40

val DarkRubricColor = Red80

val LightCustomColorsPalette = CustomColorsPalette(
    rubricColor = LightRubricColor,
)

val DarkCustomColorsPalette = CustomColorsPalette(
    rubricColor = DarkRubricColor,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
