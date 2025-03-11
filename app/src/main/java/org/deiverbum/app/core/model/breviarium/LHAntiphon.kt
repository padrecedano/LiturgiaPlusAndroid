package org.deiverbum.app.core.model.data.breviarium

import androidx.room.Ignore
import org.deiverbum.app.util.Utils

/**
 * Maneja las antífonas de la salmodia.
 *
 * @property antiphonID El ID de la antífona en la base de datos.
 * @property antiphon El texto de la antífona.
 * @property haveSymbol Determina si la antífona tiene el símbolo "†" en su contenido.
 * @property antiphon El texto de la antífona.
 */
data class LHAntiphon(
    var antiphonID: Int = 0,
    var antiphon: String = "",
    @Ignore
    var theOrder: Int = 0
) {
    @Ignore
    var haveSymbol: Boolean = false

    init {
        if (antiphon.contains("†")) {
            haveSymbol = true
            antiphon = antiphon.replace("†", "")
        }
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario.
     * No se traslada este método a la fuente local (entidades de la base de datos), porque debe resolverse en el modelo,
     * ya sea que los datos vengan de una fuente local, remota u otra.
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        this.antiphon = if (antiphon.isEmpty()) "" else Utils.replaceByTime(antiphon, calendarTime)
    }
}