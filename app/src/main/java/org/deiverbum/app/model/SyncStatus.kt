package org.deiverbum.app.model

import androidx.room.Ignore
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

/**
 *
 *
 * Esta clase maneja el estado de la sincronización.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class SyncStatus {
    var tableName = ""

    //@Ignore
    var versionDB = 1
    var lastUpdate = ""

    constructor() {}

    @Ignore
    constructor(lastUpdate: String) {
        this.lastUpdate = lastUpdate
    }

    fun getAll(isNightMode: Boolean): String {
        ColorUtils.isNightMode = isNightMode
        return String.format(
            "Última fecha disponible en el calendario: %s%s" +
                    "<b>%s</b> (*)" +
                    "%sÚltima sincronización realizada: %s" +
                    "<b>%s</b>" +
                    "%s<small>..............%s%s(*) El calendario se sincroniza periódicamente cuando tienes conexión a internet.</small>",
            Constants.BR, Constants.NBSP_4,
            Utils.toRedFont(tableName),
            Constants.BRS, Constants.NBSP_4,
            Utils.toRedFont(lastUpdate),
            Constants.BRS, Constants.BR, Constants.BR
        )
    }

    fun getLastUpdate(isNightMode: Boolean): String {
        ColorUtils.isNightMode = isNightMode
        return String.format(
            "<p><b>%s</b></p>Es posible que la sincronización no esté activada. " +
                    "Sin esta opción no podrás recibir las fechas futuras del calendario ni la corrección de eventuales errores.%sPulsa en el botón <b>Sincronizar</b> para activarla.",
            Utils.toRedFont("AVISO"),
            Constants.BRS
        )
    }
}