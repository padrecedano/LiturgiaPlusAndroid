package org.deiverbum.app.util


/**
 * Ayuda a reemplazar los carácteres para el uso específico de la Vista.
 *
 * @since 2025.1
 * @see AudioHelper
 * @see org.deiverbum.app.core.designsystem.component.transformBodyText
 *
 */
class ViewHelper {
    companion object {
        val charsToReplace = mapOf(
            "¦" to "\t",
            "≀" to "\n\t\t",
            "_" to "\n\t\t",
            "~" to "\n\t\t",
            "⊣" to "\n\t\t",
            "§" to "\n\n",
        )
    }
}