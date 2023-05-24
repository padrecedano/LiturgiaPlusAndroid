package org.deiverbum.app.model

import android.text.Spanned
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
    @Ignore
    var lastYearCleaned = 0

    @Ignore
    var source = 0 //0=red, 1=firebase

    constructor()



    @Ignore
    constructor(lastUpdate: String, lastYearCleaned: Int) {
        this.lastUpdate = lastUpdate
        this.lastYearCleaned = lastYearCleaned

    }

    public fun setSource(_source :Int){
        this.source=_source
    }

    fun getAll(isNightMode: Boolean): String {
        ColorUtils.isNightMode = isNightMode
        //val title=Utils.formatSubTitle(Utils.toRedFont("Informe de Sincronización"))
        val title=Utils.formatSubTitle(Utils.toRedFont("<b>Informe de Sincronización</b>"))

        val lastYear=if (lastYearCleaned==0)  "" else  String.format("Los datos del año <b>%s</b> fueron limpiados.",Utils.toRedFont(lastYearCleaned.toString()))
        return String.format(
            "%s" + "%s" +
                    "Última fecha disponible en el calendario: " +
                    "<b>%s</b> (*)" +
                    "%sÚltima sincronización realizada: %s" +
                    "<b>%s</b>" +
                    "%s"+
                    "%s%s<small>..............%s%s(*) El calendario se sincroniza periódicamente cuando tienes conexión a internet.</small>",
            title,
            Constants.BRS,
            Utils.toRedFont(tableName),
            Constants.BRS, Constants.NBSP_4,
            Utils.toRedFont(lastUpdate),Constants.BRS,lastYear,
            Constants.BRS, Constants.BR, Constants.BR
        )
    }

    fun getNotWorkerMessage(isNightMode: Boolean): Spanned? {
        ColorUtils.isNightMode = isNightMode
        return Utils.fromHtml(String.format(
            "<p><b>%s</b></p>Es posible que la sincronización no esté activada. " +
                    "Sin esta opción no podrás recibir las fechas futuras del calendario ni la corrección de eventuales errores.%sPulsa en el botón <b>Sincronizar</b> para activarla.",
            Utils.toRedFont("AVISO"),
            Constants.BRS
        ))
    }

    fun getWorkerMessage(): Spanned? {
        return Utils.fromHtml("<p>La sincronización automática está activada.</p>")
    }
}