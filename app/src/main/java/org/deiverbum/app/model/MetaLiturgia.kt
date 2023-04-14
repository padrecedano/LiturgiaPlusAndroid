package org.deiverbum.app.model

import org.deiverbum.app.utils.Utils

class MetaLiturgia {
    protected var fecha: String? = null
    get() = if(field.isNullOrEmpty()) "" else Utils.getLongDate(fecha)
    var tiempo = 0
    var semana = 0
    var color = 0
    protected var idLecturas = 0
    protected var idHour = 0
    var mensaje: String? = null
    protected var titulo: String? = null
    protected var idPrevio = 0
    protected var idTiempoPrevio = 0
    var hasSaint = false
    var weekDay = 0

    fun setIdTiempo(idTiempo: Int) {
        tiempo = idTiempo
    }

    fun setIdSemana(idSemana: Int) {
        semana = idSemana
    }

}