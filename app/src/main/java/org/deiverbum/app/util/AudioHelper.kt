package org.deiverbum.app.util

/**
 * Ayuda a reemplazar los carácteres para el uso específico de Audio.
 *
 * @since 2025.1
 * @see ScreenHelper
 *
 */
class AudioHelper {
    companion object {
        val charsToReplace = mapOf(
            '℣' to ' ',
            '℟' to ' ',
            '†' to ' ',
            '⟨' to ' ',
            '⟩' to ' ',
            'Ɽ' to ' ',
            '¦' to ' ',
            '≀' to ' ',
            '~' to ' ',
            '_' to ' ',
            '§' to ' ',
            '⊣' to ' ',
            '≠' to ' ',
            '*' to ' ',
            '≡' to ' '
        )
    }
}