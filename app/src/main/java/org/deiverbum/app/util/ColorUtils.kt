package org.deiverbum.app.util

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import org.deiverbum.app.core.designsystem.theme.Red40

/**
 * Clase utilitaria para manejar la diferencia de colores en las rúbricas entre el modo noche y modo día.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
object ColorUtils {
    val LS: String? = System.lineSeparator()
    private val redDefault = ForegroundColorSpan(Color.parseColor("#A52A2A"))
    private val redNightMode = ForegroundColorSpan(Color.parseColor("#FFDAB9"))
    var isNightMode = false

    @JvmStatic
    val red: ForegroundColorSpan
        get() = if (isNightMode) redNightMode else redDefault

    @JvmStatic
    val redCode: String
        get() = if (isNightMode) "#FFDAB9" else "#A52A2A"

    var rubricColor: androidx.compose.ui.graphics.Color = Red40

}