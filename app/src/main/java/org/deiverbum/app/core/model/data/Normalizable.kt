package org.deiverbum.app.core.model.data

/**
 * Interfaz que implementan todos los elementos que necesiten ser normalizados.
 *
 * @author A. Cedano
 * @since 2025.1
 */
interface Normalizable {
    fun normalizeByTime(calendarTime: Int)
}