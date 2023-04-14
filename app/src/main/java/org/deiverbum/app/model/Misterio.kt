package org.deiverbum.app.model

import java.util.*

class Misterio {
    val ordinalName = Arrays.asList(
        "Primer Misterio",
        "Segundo Misterio",
        "Tercer Misterio",
        "Cuarto Misterio",
        "Quinto Misterio"
    )
    var id = 0
    var titulo: String? = null
    var contenido: List<String>? = null
    fun getContenidoAll(dayCode: Int): List<String>? {
        return contenido
    }
}