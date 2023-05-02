package org.deiverbum.app.model

class CompletasDia {
    var salmodia: LHPsalmody? = null
    var oracion: Prayer? = null

    //BiblicalShort biblicaBreve;
    private var lecturaBreve: BiblicalShort? = null


    fun getLecturaBreve(): BiblicalShort? {
        return lecturaBreve
    }

    @Suppress("unused")
    fun setLecturaBreve(lecturaBreve: BiblicalShort?) {
        this.lecturaBreve = lecturaBreve
    }

}