package org.deiverbum.app.core.model.data

import android.text.Spanned
import androidx.room.Ignore
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Source
import org.deiverbum.app.util.Utils

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
@JsonClass(generateAdapter = true)
data class SyncStatus(
    var lastUpdate: String = "",
    var version: Int = 1,

    var lastDate: Int = 0
) {
    @Ignore
    var tableName = ""

    @Ignore
    var lastYearCleaned: Int = 0

    @Ignore
    var isWorkScheduled: Boolean = true

    @Ignore
    var source: Source = Source.LOCAL //0=red, 1=firebase

    //constructor()

    //@Ignore

    constructor(lastUpdate: String, lastYearCleaned: Int) : this(lastUpdate = lastUpdate) {
        //this.lastUpdate = lastUpdate
        this.lastYearCleaned = lastYearCleaned
    }


    fun getAll(isNightMode: Boolean): String {
        ColorUtils.isNightMode = isNightMode
        val title = Utils.formatSubTitle(Utils.toRedFont("<b>Informe de Sincronización</b>"))


        val stringDate = Utils.formatDate(lastDate.toString(), "yyyyMMdd", "yyyy-MM-dd")

        val lastYear = if (lastYearCleaned == 0) "" else String.format(
            "Los datos del año <b>%s</b> fueron limpiados.",
            Utils.toRedFont(lastYearCleaned.toString())
        )

        val stringLastUpdate = if (lastUpdate == "") "" else String.format(
            "%sÚltima sincronización: %s<b>%s</b>",
            Constants.BRS, Constants.BR,
            Utils.toRedFont(lastUpdate)
        )
        return String.format(
            "%s" + "%s" +
                    "Última fecha en el calendario: " +
                    "<b>%s</b> (*)" +
                    "%s" +
                    "%s" +
                    "%s%s<small>..............%s%s(*) El calendario se sincroniza periódicamente cuando tienes conexión a internet.</small>",
            title,
            Constants.BRS,
            Utils.toRedFont(stringDate),
            stringLastUpdate,
            Constants.BRS, lastYear,
            Constants.BRS, Constants.BR, Constants.BR
        )
    }

    fun getNotWorkerMessage(isNightMode: Boolean): Spanned {
        ColorUtils.isNightMode = isNightMode
        return Utils.fromHtml(String.format(
            "<p><b>%s</b></p>La sincronización no está activada.",
            Utils.toRedFont("AVISO")
        ))
    }

    fun getWorkerMessage(): Spanned {
        return Utils.fromHtml("<p>La sincronización automática está activada.</p>")
    }
    /*
        override suspend fun collect(collector: FlowCollector<SyncStatus>) {
            collector.emit(this)
        }*/
}