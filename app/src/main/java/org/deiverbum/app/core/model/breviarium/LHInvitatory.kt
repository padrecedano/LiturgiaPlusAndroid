package org.deiverbum.app.core.model.breviarium

import androidx.room.Ignore

class LHInvitatory(override var antiphonae: MutableList<LHAntiphon>) :
    LHPsalmody(antiphonae, 0) {

    constructor(antiphonae: MutableList<LHAntiphon>, psalmus: MutableList<LHPsalm>) : this(
        antiphonae
    ) {
        this.psalmus = psalmus
    }

    init {
        if (psalmus.isEmpty()) {
            psalmus.add(LHPsalm(0, true, "Salmo 94", unique))
        }
    }

    @Ignore
    var isMultiple = false
    var caseID = 0
    var psalmFK = 315

    val unique: String
        get() = "Venid, aclamemos al Señor,_demos vítores a la Roca que nos salva;_entremos a su presencia dándole gracias,_aclamándolo con cantos.§Porque el Señor es un Dios grande,_soberano de todos los dioses,_tiene en su mano las simas de la tierra,_son suyas las cumbres de los montes. _Suyo es el mar, porque él lo hizo,_la tierra firme que modelaron sus manos.§Venid, postrémonos por tierra,_bendiciendo al Señor, creador nuestro. _Porque él es nuestro Dios,_y nosotros su pueblo,_el rebaño que él guía.§Ojalá escuchéis hoy su voz:_«No endurezcáis el corazón como en Meribá, _como el día de Masá en el desierto:_cuando vuestros padres me pusieron a prueba,_y dudaron de mí, aunque habían visto mis obras.§Durante cuarenta años_aquella generación me repugnó, y dije:_“Es un pueblo de corazón extraviado,_que no reconoce mi camino;_por eso he jurado en mi cólera_que no entrarán en mi descanso”»."

    /**
     * Normaliza la antífona del invitatorio, cambiando el marcador ƞ por el nombre del santo.
     *
     * @see [BreviariumOfficium]
     * @see [BreviariumLaudes]
     * @see [BreviariumMixtus]
     */
    fun normalizeIsSaint(s: String) {
        antiphonae[0].antiphon =
            antiphonae[0].antiphon.replace("ƞ", s.substring(s.indexOf(" ") + 1))
    }
}