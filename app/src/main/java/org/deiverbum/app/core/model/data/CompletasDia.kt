package org.deiverbum.app.core.model.data

class CompletasDia {
    var salmodia: LHPsalmody? = null
    var oracion: Oratio? = null

    //BiblicalShort biblicaBreve;
    private var lecturaBreve: LHLectioBrevis? = null


    fun getLecturaBreve(): LHLectioBrevis? {
        return lecturaBreve
    }

    @Suppress("unused")
    fun setLecturaBreve(lecturaBreve: LHLectioBrevis?) {
        this.lecturaBreve = lecturaBreve
    }

}