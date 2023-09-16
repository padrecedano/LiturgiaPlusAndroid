package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/*
    IDs de los salmos invitatorios:
        LHPsalm 94: ID 315, insertado recientemente
        LHPsalm 99: ID 86
        LHPsalm 66: ID 62
        LHPsalm 23: ID 101
 */
class LHInvitatory  {
    @Ignore
    var isMultiple = false
    var caseID = 1
    var psalmFK = 315
    @Ignore var lhPsalm: LHPsalm? = null

    //var psalmID = 0
    //var readingID = 0
    //var quote: String? = ""


    /**
     * Determina si el usuario tiene configurado un invitatorio variable
     * y devuelve el texto que corresponda.
     *
     * @return El texto obtenido del archivo correspondiente o el texto por defecto
     * @since 2022.1
     */
    val textoSpan: Spanned
        get() {
            val ssb = SpannableStringBuilder()
            if (isMultiple) {
                //ssb.append(ref)
                ssb.append(Constants.LS2)
                //ssb.append(Utils.fromHtml(psalm))
            } else {
                ssb.append(Utils.toRed("Salmo 94."))
                ssb.append(Constants.LS2)
                ssb.append(Utils.fromHtml(unique))
            }
            return ssb
        }//sb.append(LS2);

    /**
     * Determina si el usuario tiene configurado un invitatorio variable
     * y devuelve el texto que corresponda.
     *
     * @return El texto obtenido del archivo correspondiente o el texto por defecto
     * @since 2022.1
     */
    private val textoForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            if (isMultiple) {
                //sb.append(Utils.normalizeEnd(ref.toString()))
                //sb.append(LS2);
                //sb.append(Utils.getFormatoForRead(psalm))
            } else {
                sb.append("Salmo 94.")
                sb.append(Utils.getFormatoForRead(unique))
            }
            return sb
        }
    val title: String
        get() = Constants.TITLE_INVITATORY
    private val titleForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_INVITATORY)
    @Suppress("unused")
    val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_INVITATORY)
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.formatSubTitleToLower(title))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed("Ant. "))
            sb.append(lhPsalm?.antiphon)
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS2)
            sb.append(lhPsalm?.finSalmo)
            sb.append(Utils.LS2)
            sb.append(Utils.toRed("Ant. "))
            sb.append(lhPsalm?.antiphon)

            //sb.append(antiphon)
            return sb
        }
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(titleForRead)
            sb.append(lhPsalm?.antifonaForRead)
            sb.append(textoForRead)
            sb.append(lhPsalm?.finSalmoForRead)
            sb.append(lhPsalm?.antifonaForRead)

            //sb.append(antiphon)
            return sb
        }

    fun normalizeIsSaint(s: String) {
        lhPsalm?.antiphon = lhPsalm?.antiphon!!.replace("ƞ", s.substring(s.indexOf(" ") + 1))
    }

    val unique: String
        get() = "Venid, aclamemos al Señor,_demos vítores a la Roca que nos salva;_entremos a su presencia dándole gracias,_aclamándolo con cantos.§Porque el Señor es un Dios grande,_soberano de todos los dioses,_tiene en su mano las simas de la tierra,_son suyas las cumbres de los montes. _Suyo es el mar, porque él lo hizo,_la tierra firme que modelaron sus manos.§Venid, postrémonos por tierra,_bendiciendo al Señor, creador nuestro. _Porque él es nuestro Dios,_y nosotros su pueblo,_el rebaño que él guía.§Ojalá escuchéis hoy su voz:_«No endurezcáis el corazón como en Meribá, _como el día de Masá en el desierto:_cuando vuestros padres me pusieron a prueba,_y dudaron de mí, aunque habían visto mis obras.§Durante cuarenta años_aquella generación me repugnó, y dije:_“Es un pueblo de corazón extraviado,_que no reconoce mi camino;_por eso he jurado en mi cólera_que no entrarán en mi descanso”»."
}