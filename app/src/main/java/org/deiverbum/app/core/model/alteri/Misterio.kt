package org.deiverbum.app.core.model.alteri

class Misterio {
    val ordinalName: MutableList<String> = mutableListOf(
        "Primer Misterio",
        "Segundo Misterio",
        "Tercer Misterio",
        "Cuarto Misterio",
        "Quinto Misterio"
    )
    var id = 0
    var titulo: String? = null
    var contenido: List<String>? = null
    @Suppress("unused")
    fun getContenidoAll(): List<String>? {
        return contenido
    }
}