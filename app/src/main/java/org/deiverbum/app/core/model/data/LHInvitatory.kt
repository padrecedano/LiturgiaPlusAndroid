package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.room.Ignore
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class LHInvitatory(override var antiphonae: MutableList<LHAntiphon>) :
    LHPsalmody(antiphonae, 0) {

    constructor(antiphonae: MutableList<LHAntiphon>, psalmus: MutableList<LHPsalm>) : this(
        antiphonae
    ) {
        this.psalmus = psalmus
    }

    init {
        if (psalmus.isEmpty()) {
            psalmus.add(LHPsalm(0, "Salmo 94", unique))
        }
    }

    @Ignore
    var isMultiple = false
    var caseID = 0
    var psalmFK = 315

    //@get:Ignore
    val allForView: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            val s = psalmus[0]
            if (!isMultiple) {
                s.psalmus = unique
                s.pericopa = "Salmo 94"
            }
            ssb.append(Utils.formatSubTitleToLower(Constants.TITLE_INVITATORY))
            ssb.append(Utils.LS2)
            //ssb.append(Utils.toRed("Ant. "))
            ssb.append(antiphonae[0].getBeforeForView(false))
            ssb.append(Utils.LS2)
            ssb.append(Utils.toRed(s.pericopa))
            ssb.append(Constants.LS2)
            ssb.append(s.psalmForView)
            //ssb.append(Utils.LS2)
            //ssb.append(thePsalm.)
            ssb.append(Utils.LS2)
            //ssb.append(Utils.toRed("Ant. "))
            ssb.append(antiphonae[0].afterForView)
            return ssb
        }

    /**
     * Obtiene el contenido del Invitatorio para la vista.
     *
     * @param calendarTime Identificador del tiempo litúrgico, para aplicar las sustituciones que correspondan.
     * @param hourIndex El índice de la hora. Para este caso, siempre se deberá llamar con valor **`-1`**.
     * @return Un [SpannableStringBuilder] con el Invitatorio formateado.
     * @see [BreviaryHour.getMixtoForView]
     * @see [LHOfficium.getForView]
     * @see [LHLaudes.getForView]
     */

    @Composable
    override fun getComposable(
        hourIndex: Int,
        calendarTime: Int,
        userData: UserDataDynamic
    ): AnnotatedString {
        val rubricColor = getRubricColor(userData = userData)
        SectionTitle(text = Constants.TITLE_INVITATORY.lowercase(), level = 1).getComposable()

        val s = psalmus[0]
        antiphonae[0].normalizeByTime(calendarTime)
        if (!userData.useMultipleInvitatory) {
            s.psalmus = unique
            s.pericopa = "Salmo 94"
        }
        return buildAnnotatedString {
            append(antiphonae[0].getComposableBefore(false, rubricColor))
            append(Utils.LS2)
            append(s.pericopaComposable(rubricColor))
            append(Constants.LS2)
            append(s.psalmForView)
            append(Utils.LS2)
            append(antiphonae[0].getComposableAfter(rubricColor))
        }
    }

    override fun getAllForView(hourIndex: Int, calendarTime: Int): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        val s = psalmus[0]
        antiphonae[0].normalizeByTime(calendarTime)

        if (!isMultiple) {
            s.psalmus = unique
            s.pericopa = "Salmo 94"
        }
        ssb.append(Utils.formatSubTitleToLower(Constants.TITLE_INVITATORY))
        ssb.append(Utils.LS2)
        ssb.append(antiphonae[0].getBeforeForView(false))
        ssb.append(Utils.LS2)
        ssb.append(Utils.toRed(s.pericopa))
        ssb.append(Constants.LS2)
        ssb.append(s.psalmForView)
        ssb.append(Utils.LS2)
        ssb.append(antiphonae[0].afterForView)
        return ssb
    }

    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(Utils.pointAtEnd(Constants.TITLE_INVITATORY))
            sb.append(antiphonae[0].antiphon)
            sb.append(Utils.normalizeEnd(psalmus[0].pericopa))
            sb.append(psalmus[0].psalmForRead)
            //sb.append(endPsalmForRead)
            sb.append(antiphonae[0].antiphon)
            return sb
        }
    val unique: String
        get() = "Venid, aclamemos al Señor,_demos vítores a la Roca que nos salva;_entremos a su presencia dándole gracias,_aclamándolo con cantos.§Porque el Señor es un Dios grande,_soberano de todos los dioses,_tiene en su mano las simas de la tierra,_son suyas las cumbres de los montes. _Suyo es el mar, porque él lo hizo,_la tierra firme que modelaron sus manos.§Venid, postrémonos por tierra,_bendiciendo al Señor, creador nuestro. _Porque él es nuestro Dios,_y nosotros su pueblo,_el rebaño que él guía.§Ojalá escuchéis hoy su voz:_«No endurezcáis el corazón como en Meribá, _como el día de Masá en el desierto:_cuando vuestros padres me pusieron a prueba,_y dudaron de mí, aunque habían visto mis obras.§Durante cuarenta años_aquella generación me repugnó, y dije:_“Es un pueblo de corazón extraviado,_que no reconoce mi camino;_por eso he jurado en mi cólera_que no entrarán en mi descanso”»."

    /**
     * Normaliza la antífona del invitatorio, cambiando el marcador ƞ por el nombre del santo.
     *
     * @see [LHOfficium]
     * @see [LHLaudes]
     * @see [LHMixtus]
     */
    fun normalizeIsSaint(s: String) {
        antiphonae[0].antiphon =
            antiphonae[0].antiphon.replace("ƞ", s.substring(s.indexOf(" ") + 1))
    }
}