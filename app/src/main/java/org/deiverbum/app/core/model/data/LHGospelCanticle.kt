package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForRead
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.Utils

class LHGospelCanticle(var typeID: Int = 0, override var antiphons: MutableList<LHAntiphon>) :
    LHPsalmody() {
    //constructor() : super()
    /*
        constructor(
            mPsalms: MutableList<LHPsalm>,
            mAntiphons: MutableList<LHAntiphon>,
            typeID: Int = 0
        ) : this(typeID)*/

    init {
        //val emList: MutableList<LHPsalm> = mutableListOf(getPsalmus(typeID))
        //emList.add(getPsalmus(typeID))
        psalms.add(getPsalmus(typeID))
        //this.psalms=

    }
    //this.typeID=typeID

    //@Ignore
    //var typeID = 0

    override val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_GOSPEL_CANTICLE)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_GOSPEL_CANTICLE)
    val texto: Spanned
        get() = Utils.fromHtml("text")

    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder(header)
            sb.append(Utils.LS2)
            sb.append(antiphonFormatted)
            sb.append(Utils.LS2)
            sb.append(texto)
            sb.append(Utils.LS2)
            sb.append(endPsalmForView)
            sb.append(Utils.LS2)
            sb.append(antiphonFormatted)
            return sb
        }
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder(headerForRead)
            //sb.append(headerForRead)
            //sb.append(lhAntiphon!!.antiphon)
            sb.append(texto)
            sb.append(endPsalmForRead)
            //sb.append(lhAntiphon!!.antiphon)
            return sb
        }
    private val antiphonFormatted: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder(Utils.toRed("Ant. "))
            //sb.append(Utils.toRed("Ant. "))
            //sb.append(lhAntiphon!!.antiphon)
            return sb
        }

    companion object {
        fun getPsalmus(typeID: Int): LHPsalm {
            return when (typeID) {
                2 -> LHPsalm(
                    1,
                    "Cántico de Zacarías¦Lc 1, 68-79",
                    "El Mesías y su precursor",
                    "Bendito sea el Señor, Dios de Israel,_porque ha " +
                            "visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tinieblas_y en sombras de muerte,_para guiar nuestros pasos_por el camino de la paz."
                )

                6 -> LHPsalm(
                    1,
                    "Cántico de la Santísima Virgen María¦Lc 1, 46-55",
                    "Alegría del alma en el Señor",
                    "Proclama mi alma la grandeza del Señor,_se alegra mi " +
                            "espíritu en Dios, mi salvador;_porque ha mirado la humillación de su esclava.§Desde ahora me felicitarán todas las generaciones,_porque el Poderoso ha hecho obras grandes por mí:_su nombre es santo,_y su misericordia llega a sus fieles_de generación en generación.§Él hace proezas con su brazo:_dispersa a los soberbios de corazón,_derriba del trono a los poderosos y enaltece a los humildes,_a los hambrientos los colma de bienes_y a los ricos los despide vacíos.§Auxilia a Israel, su siervo,_acordándose de la misericordia_—como lo había prometido a nuestros padres—_en favor de Abrahán y su descendencia por siempre."
                )

                7 ->
                    LHPsalm(
                        1,
                        "Cántico de Simeón¦Lc 2, 29-32",
                        "Cristo, luz de las naciones y gloria de Israel",
                        "Ahora, Señor, según tu promesa, ~puedes dejar a tu siervo irse en paz, ¦§porque mis ojos han visto a tu Salvador, ~a quien has presentado ante todos los pueblos: ¦~luz para alumbrar a las naciones~y gloria de tu pueblo Israel."
                    )

                else -> {
                    LHPsalm()
                }
            }
        }
    }
}

