package org.deiverbum.app.core.model.alteri

import androidx.room.ColumnInfo
import androidx.room.Ignore

open class Sanctus(
    @ColumnInfo(name = "theMonth")
    var mensis: Int = 0,
    @ColumnInfo(name = "theDay")
    var dies: Int = 0,
    @ColumnInfo(name = "theName")
    var nomen: String = "",
    @ColumnInfo(name = "martirologio")
    @Ignore val martyrologyum: String = "",
    @Ignore
    var vita: String = ""
) {
    var saintID: Int? = null

    @Ignore
    var liturgyFK: Int? = null
    var typeFK: Int? = null

    @Ignore
    var crg = false

    val monthName: String
        get() {
            val monthNames = mapOf(
                1 to "Enero",
                2 to "Febrero",
                3 to "Marzo",
                4 to "Abril",
                5 to "Mayo",
                6 to "Junio",
                7 to "Julio",
                8 to "Agosto",
                9 to "Septiembre",
                10 to "Octubre",
                11 to "Noviembre",
                12 to "Diciembre"
            )
            return "$dies de ${monthNames.getValue(mensis)}"
        }
}