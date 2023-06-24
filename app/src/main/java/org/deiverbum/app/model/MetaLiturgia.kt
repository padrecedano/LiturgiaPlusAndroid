package org.deiverbum.app.model

import org.deiverbum.app.util.Utils
@Suppress("unused")
class MetaLiturgia {
    private var fecha: String? = null
    get() = if(field.isNullOrEmpty()) "" else Utils.getLongDate(field)
    var tiempo = 0
    var semana = 0
    var color = 0
    private var idLecturas = 0
    private var idHour = 0
    var mensaje: String? = null
    private var titulo: String? = null
    private var idPrevio = 0
    private var idTiempoPrevio = 0
    var hasSaint = false
    var weekDay = 0

    fun setIdTiempo(idTiempo: Int) {
        tiempo = idTiempo
    }

    fun setIdSemana(idSemana: Int) {
        semana = idSemana
    }

}