package org.deiverbum.app.util

import android.graphics.Color
import android.os.Build
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import org.deiverbum.app.core.designsystem.theme.Red40

/**
 * Clase utilitaria para manejar la diferencia de colores en las rúbricas entre el modo noche y modo día.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
object ComposeColorUtil {
    val LS: String? = System.getProperty("line.separator")
    private val redDefault = ForegroundColorSpan(Color.parseColor("#A52A2A"))
    private val redNightMode = ForegroundColorSpan(Color.parseColor("#FFDAB9"))
    var isNightMode = false

    @JvmStatic
    val red: ForegroundColorSpan
        get() = if (isNightMode) redNightMode else redDefault

    @OptIn(ExperimentalStdlibApi::class)
    @JvmStatic
    val redCode: String
        get() = rubricColor.toArgb().toHexString(HexFormat.UpperCase).takeLast(6)

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalStdlibApi::class)
    fun getHexColor(rubricColor: androidx.compose.ui.graphics.Color): String {
        return rubricColor.toArgb().toHexString(HexFormat.UpperCase).takeLast(6)
    }

    var rubricColor: androidx.compose.ui.graphics.Color = Red40

}