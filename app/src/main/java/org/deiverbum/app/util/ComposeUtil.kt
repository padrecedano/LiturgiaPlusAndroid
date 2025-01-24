package org.deiverbum.app.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Clase utilitaria para Composables
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
//@SuppressWarnings("all")
//@ExperimentalStdlibApi
object ComposeUtil {
    @ExperimentalStdlibApi
    fun getLectioHeaderForView(type: Int, theOrder: Int, rubricColor: Color): String {
        return if (type == 0) {
            var header = ""
            if (theOrder in 1..19) {
                header = "PRIMERA LECTURA"
            }
            if (theOrder in 20..29) {
                header = "SALMO RESPONSORIAL"
            }
            if (theOrder in 30..39) {
                header = "SEGUNDA LECTURA"
            }
            if (theOrder >= 40) {
                header = "EVANGELIO"
            }
            toRedFontCompose(header, rubricColor)
        } else {
            ""//getHeaderByType(type)
        }
    }

    @ExperimentalStdlibApi
    fun toRedFontCompose(s: String, rubricColor: Color): String {
        var color = "#" + rubricColor.toArgb()
        //#ffff0000
        val str = rubricColor.toArgb().toHexString(HexFormat.UpperCase).takeLast(6)

        return String.format("<font color=\"#%s\">%s</font>", str, s)
    }
}