package org.deiverbum.app.core.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import org.deiverbum.app.core.designsystem.component.textForAudio
import org.deiverbum.app.core.designsystem.component.textFromHtmlAudio
import org.deiverbum.app.core.designsystem.component.textFromListAudio
import org.deiverbum.app.core.designsystem.component.transformEpigraphAudio
import org.deiverbum.app.core.designsystem.component.transformTextAudio
import org.deiverbum.app.core.model.data.alteri.AlteriRosarium
import org.deiverbum.app.core.model.data.alteri.AlteriSanctii
import org.deiverbum.app.core.model.data.breviarium.Breviarium
import org.deiverbum.app.core.model.data.breviarium.BreviariumCompletorium
import org.deiverbum.app.core.model.data.breviarium.BreviariumIntermedia
import org.deiverbum.app.core.model.data.breviarium.BreviariumLaudes
import org.deiverbum.app.core.model.data.breviarium.BreviariumMixtus
import org.deiverbum.app.core.model.data.breviarium.BreviariumOfficium
import org.deiverbum.app.core.model.data.breviarium.BreviariumVesperas
import org.deiverbum.app.core.model.data.breviarium.LHAntiphon
import org.deiverbum.app.core.model.data.breviarium.LHHymn
import org.deiverbum.app.core.model.data.breviarium.LHIntercession
import org.deiverbum.app.core.model.data.breviarium.LHInvitatory
import org.deiverbum.app.core.model.data.breviarium.LHLectioBrevis
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumLectioAltera
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumLectioPrior
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumLectionis
import org.deiverbum.app.core.model.data.breviarium.LHPsalm
import org.deiverbum.app.core.model.data.breviarium.LHPsalmody
import org.deiverbum.app.core.model.data.breviarium.LHResponsoriumBrevis
import org.deiverbum.app.core.model.data.breviarium.LHSanctus
import org.deiverbum.app.core.model.data.breviarium.normalizeForRead
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.liturgia.Introitus
import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.core.model.liturgia.PadreNuestro
import org.deiverbum.app.core.model.liturgia.RitusConclusionis
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.ErrorHelper
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.LiturgyHelper.Companion.gloriaNonDicitur
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.splitParts

/**
 * Devuelve el contenido de Lecturas del Oficio + Laudes para audio.
 *
 * @param data Un objeto [BreviariumMixtus] con el contenido de la hora
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [BreviariumMixtus]
 */

fun audioMixtus(
    data: BreviariumMixtus,
): AnnotatedString {
    return buildAnnotatedString {
        if (data.sanctus != null && data.hasSaint) {
            //data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            append(sanctusAudio(data.sanctus!!))
        }
        append(introitusPrimaAudio())
        append(hymnusAudio(data.hymnus))
        append(psalmodiaAudio(data.psalmodia, -1))
        append(lectioBrevisAudio(data.lectioBrevis))
        append(
            officiumLectionisAudio(
                officiumLectionis = data.officiumLectionis
            )
        )
        append(
            canticumEvangelicumAudio(
                psalmodia = data.canticumEvangelicum
            )
        )
        append(precesAudio(preces = data.preces))
        append(transformTextAudio(text = PadreNuestro.texto))
        append(oratioAudio(data = data.oratio))
        append(conclusionisMaiorAudio())
    }
}


/**
 * Devuelve el contenido del Oficio para audio.
 *
 * @param data Un objeto [BreviariumOfficium] con el contenido de la hora
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [BreviariumOfficium]
 */

fun audioOfficium(
    data: BreviariumOfficium,
): AnnotatedString {

    //data.officiumLectionis.normalizeByTime(calendarTime)
    return buildAnnotatedString {
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            append(AnnotatedString.fromHtml(data.sanctus!!.vitaBrevis))
        }
        append(introitusPrimaAudio())
        append(invitatoriumAudio(data.invitatorium))
        append(hymnusAudio(data.hymnus))
        append(psalmodiaAudio(data.psalmodia, -1))
        append(
            officiumLectionisAudio(
                officiumLectionis = data.officiumLectionis
            )
        )
        append(oratioAudio(data = data.oratio))
        append(conclusionisMaiorAudio())
    }
}

fun audioIntermedia(
    data: BreviariumIntermedia,
): AnnotatedString {
    return buildAnnotatedString {
        append(introitusAlteraAudio())
        append(hymnusAudio(data.hymnus))
        append(
            psalmodiaAudio(
                data.psalmodia,
                LiturgyHelper.psalmodiaIndex(data.typusID)
            )
        )
        append(lectioBrevisAudio(data.lectioBrevis))
        append(oratioAudio(data = data.oratio))
        append(conclusionisMinorAudio())
    }
}


/**
 * Devuelve el contenido de Laudes para audio.
 *
 * @param data Un objeto [BreviariumLaudes] con el contenido de la hora
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [BreviariumLaudes]
 */

fun audioLaudes(
    data: BreviariumLaudes,
): AnnotatedString {
    return buildAnnotatedString {
        append(introitusPrimaAudio())
        append(hymnusAudio(data.hymnus))
        append(psalmodiaAudio(data.psalmodia, -1))
        append(lectioBrevisAudio(data.lectioBrevis))
        append(
            canticumEvangelicumAudio(
                psalmodia = data.canticumEvangelicum
            )
        )
        append(precesAudio(preces = data.preces))
        append(transformTextAudio(text = PadreNuestro.texto))
        append(oratioAudio(data = data.oratio))
        append(conclusionisMaiorAudio())
    }
}

/**
 * Devuelve el contenido de Vísperas para audio.
 *
 * @param data Un objeto [BreviariumVesperas] con el contenido de la hora
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [BreviariumVesperas]
 */

fun audioVesperas(
    data: BreviariumVesperas,
): AnnotatedString {
    return buildAnnotatedString {
        append(introitusAlteraAudio())
        append(hymnusAudio(data.hymnus))
        append(psalmodiaAudio(data.psalmodia, -1))
        append(lectioBrevisAudio(data.lectioBrevis))
        append(
            canticumEvangelicumAudio(
                psalmodia = data.canticumEvangelicum
            )
        )
        append(precesAudio(preces = data.preces))
        append(transformTextAudio(text = PadreNuestro.texto))
        append(oratioAudio(data = data.oratio))
        append(conclusionisMaiorAudio())
    }
    //}
}

/**
 * Devuelve el contenido de Completas para audio.
 *
 * @param data Un objeto [BreviariumCompletorium] con el contenido de la hora
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [BreviariumCompletorium]
 */

fun audioCompletorium(
    data: BreviariumCompletorium,
): AnnotatedString {
    return buildAnnotatedString {
        append(introitusPrimaAudio())
        append(hymnusAudio(data.hymnus))
        append(psalmodiaAudio(data.psalmodia, -1))
        append(lectioBrevisAudio(data.lectioBrevis))
        append(
            canticumEvangelicumAudio(
                psalmodia = data.canticumEvangelicum
            )
        )
        //append(precesAudio(preces = data.preces, userData = userData.dynamic)
        append(transformTextAudio(text = PadreNuestro.texto))
        append(oratioAudio(data = data.oratio))
        append(conclusionisMaiorAudio())
    }
    //}
}

/**
 * Devuelve el contenido de la Misa para audio.
 *
 * @param data Un objeto [Missae] con el contenido de la hora.
 * @param id Un valor numérico para diferenciar entre Homilías o Lecturas de la Misa.
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [Missae]
 */

fun audioMissae(
    data: Missae,
    id: Int,
): AnnotatedString {
    return buildAnnotatedString {
        when (id) {
            11 -> {

            }

            13 -> {

            }
        }

    }
    //}
}

/**
 * Devuelve el contenido de los comentarios al Evangelio de la Misa para audio.
 *
 * @param data Un objeto [Commentarii] con el contenido de los comentarios.
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [Commentarii]
 */

fun audioCommentarii(
    data: Commentarii,
): AnnotatedString {
    return buildAnnotatedString {

    }
    //}
}

/**
 * Devuelve el contenido de las Homilías para audio.
 *
 * @param data Un objeto [Missae] con el contenido de las homilías
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [Missae]
 */

fun audioHomiliae(
    data: Missae,
): AnnotatedString {
    return buildAnnotatedString {

    }
    //}
}

/**
 * Devuelve el contenido de la vida del Santo de una fecha dada.
 *
 * @param data Un objeto [AlteriSanctii] que representa la vida del Santo.
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [AlteriSanctii]
 */

fun audioSanctii(
    data: AlteriSanctii,
): AnnotatedString {
    return buildAnnotatedString {

    }
    //}
}

/**
 * Devuelve el contenido del Santo Rosario correspondiente al día de la fecha dada.
 *
 * @param data Un objeto [AlteriRosarium] que representa el Santo Rosario.
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [AlteriRosarium]
 */

fun audioRosarium(
    data: AlteriRosarium,
): AnnotatedString {
    return buildAnnotatedString {

    }
    //}
}


/**
 * Devuelve la vida breve del Santo del día para audio.
 *
 * @param sanctus Un objeto [LHSanctus] que representa el santo del día.
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [LHSanctus]
 */

fun sanctusAudio(sanctus: LHSanctus): AnnotatedString {
    return AnnotatedString.fromHtml(sanctus.vitaBrevis)
}


/**
 * Devuelve la introducción de las horas mayores para audio.
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

fun introitusPrimaAudio(): AnnotatedString {
    return buildAnnotatedString {
        append(Constants.TITLE_INITIAL_INVOCATION)
        append(".")
        append(textFromListAudio(texts = Introitus().prima))
        append(finisPsalmusAudio())
    }
}

/**
 * Crea la introducción de las horas menores para audio.
 *
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun introitusAlteraAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_INITIAL_INVOCATION}.")
        append(textFromListAudio(texts = Introitus().altera))
        append(finisPsalmusAudio())
    }
}

/**
 * Crea el contenido del Invitatorio para audio.
 *
 * @param psalmodia Un objeto [LHInvitatory] con el invitatorio
 *
 * @return Un objeto [AnnotatedString] con el invitatorio formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun invitatoriumAudio(
    psalmodia: LHInvitatory,
): AnnotatedString {
    return buildAnnotatedString {
        append(Constants.TITLE_INVITATORY)
        append(".")
        append(
            psalmodiaUnicaAudio(
                psalmodia
            )
        )
    }
}

/**
 * Crea el contenido del Himno para audio.
 *
 * @param data Un objeto [LHHymn] con el Himno
 *
 * @return Un objeto [AnnotatedString] con el himno formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun hymnusAudio(
    data: LHHymn,
): AnnotatedString {
    return buildAnnotatedString {
        append(textForAudio("${Constants.TITLE_HYMN}."))
        append(transformTextAudio(data.hymnus))
    }
}


/**
 * Crea el contenido de la Salmodia, según los diferentes tipos.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 * @param indexHora El índice de la hora (para el caso de la Hora Intermedia)
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
fun psalmodiaAudio(
    psalmodia: LHPsalmody,
    indexHora: Int
): AnnotatedString {
    return try {
        val antiphonaeAnte = AnnotatedString.Builder()
        val antiphonaePost = AnnotatedString.Builder()
        buildAnnotatedString {
            append(textForAudio("${Constants.TITLE_PSALMODY}."))
            if (psalmodia.typus == 1) {
                if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                    //psalmodia.antiphonae[indexHora].normalizeByTime(calendarTime)
                    antiphonaeAnte.append(
                        antiphonaeAnteAudio(
                            psalmodia.antiphonae[indexHora],
                            false
                        )
                    )
                } else {
                    //psalmodia.antiphonae[0].normalizeByTime(calendarTime)
                    antiphonaeAnte.append(
                        antiphonaeAnteAudio(
                            psalmodia.antiphonae[0],
                            true
                        )
                    )
                    antiphonaePost.append(
                        antiphonaePostAudio(
                            psalmodia.antiphonae[0],
                        )
                    )
                }

                append(antiphonaeAnte.toAnnotatedString())
                for (s in psalmodia.psalmus) {
                    append(psalmusAudio(s))
                }
                append(antiphonaePostAudio(psalmodia.antiphonae[indexHora]))
            }

            if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                psalmodia.psalmus.lastIndex
                psalmodia.psalmus.forEach { s ->
                    //psalmodia.antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                    append(
                        antiphonaeAnteAudio(
                            psalmodia.antiphonae[s.theOrder - 1],
                            true
                        )
                    )
                    append(psalmusAudio(s))
                    append(antiphonaePostAudio(psalmodia.antiphonae[s.theOrder - 1]))
                }
            }
        }
    } catch (e: Exception) {
        return AnnotatedString(Utils.createErrorMessage(e.message))
    }
}

/**
 * Crea el contenido de la Salmodia para audio, cuando hay un solo Salmo / Antífona.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
fun psalmodiaUnicaAudio(
    psalmodia: LHPsalmody,
): AnnotatedString {
    //psalmodia.antiphonae[0].normalizeByTime(calendarTime)
    return antiphonaeEtPsalmusAudio(
        psalmodia.antiphonae[0],
        false,
        psalmodia.psalmus[0]
    )
}

/**
 * Crea el contenido para audio de la Antífona y el Salmo para la Salmodia.
 *
 * @param antiphonae Un objeto [LHAntiphon] con la antífona
 * @param withOrder Para indicar si la antífona lleva orden numérico
 * @param psalmus Un objeto [LHPsalm] con el salmo
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 * @see [LHAntiphon]
 * @see [LHPsalm]
 */
fun antiphonaeEtPsalmusAudio(
    antiphonae: LHAntiphon,
    withOrder: Boolean,
    psalmus: LHPsalm,
): AnnotatedString {
    //antiphonae.normalizeByTime(calendarTime)
    return buildAnnotatedString {
        append(antiphonaeAnteAudio(antiphonae, withOrder))
        append(psalmusAudio(psalmus))
        append(antiphonaePostAudio(antiphonae))
    }
}

/**
 * Prepara para la vista la antífona antes del salmo.
 * En la salmodia, cuando corresponden varias antífonas, las mismas se presentan antes del
 * salmo precedidas de la palabra "Ant. " más el número de la antífona, que se determina
 * mediante la propiedad [LHAntiphon.theOrder].
 *
 * También aquí se determina mediante el valor de [LHAntiphon.haveSymbol] si el texto de la antífona contiene el símbolo †
 * para colocarlo al final de la misma formateado con el color de rúbrica.
 *
 * @param antiphonae antífona representada por un objeto [LHAntiphon].
 * @param withOrder Para determinar si se requiere el orden de la antífona.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun antiphonaeAnteAudio(
    antiphonae: LHAntiphon,
    withOrder: Boolean = true
): AnnotatedString {
    return buildAnnotatedString {
        if (withOrder) {
            append("Antífona ${antiphonae.theOrder}. ")
        } else {
            append("Antífona: ")
        }
        append(antiphonae.antiphon)
        /*if (antiphonae.haveSymbol) {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
            append(textRubric(" † ", rubricColor, fontSize))
        } else {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
        }*/
    }
}

/**
 * Prepara para la vista la antífona después del salmo.
 * En la salmodia, las antífonas después del salmo se presentan siempre
 * precedidas de la palabra "Ant. " y luego la antífona.
 *
 * @param antiphonae antífona representada por un objeto [LHAntiphon].
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun antiphonaePostAudio(
    antiphonae: LHAntiphon,
): AnnotatedString {
    return buildAnnotatedString {
        append("Antífona: ")
        append(antiphonae.antiphon)
    }
}

/**
 * Prepara un objeto [LHPsalm] para la vista.
 * Verifica si el salmo termina con el marcador "∸", en cuyo caso,
 * se omite al final el "Gloria al Padre ..."
 *
 * @param psalmus salmo representado por un objeto [LHPsalm].
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun psalmusAudio(
    psalmus: LHPsalm,
): AnnotatedString {
    return buildAnnotatedString {
        //val normalizeText = psalmus.normalize()
        if (psalmus.pericopa != "") {
            append(textForAudio("${psalmus.pericopa}."))
        }
        if ((psalmus.theme != null) && (psalmus.theme != "")) {
            append(textForAudio("${psalmus.theme!!}."))
        }
        if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
            append(transformEpigraphAudio(psalmus.epigraph!!))
        }

        append(transformTextAudio(text = psalmus.psalmus))
        append(finisPsalmusAudio(psalmus.haveGloria))
    }
}

fun finisPsalmusAudio(haveGloria: Boolean = true): AnnotatedString {
    return if (haveGloria) {
        textFromListAudio(texts = LiturgyHelper.finisPsalmus)
    } else {
        buildAnnotatedString {
            append(gloriaNonDicitur)

        }
    }
}

fun lectioBrevisAudio(
    data: LHLectioBrevis
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_SHORT_READING}.")
        append(textForAudio(data.biblica))
        append(
            responsoriumAudio(
                data = data.responsorium,

                )
        )
    }
}

fun responsoriumAudio(
    data: LHResponsoriumBrevis,

    ): AnnotatedString {
    return buildAnnotatedString {
        responsoriumBrevisTitleAudio(
            typus = data.typus,
        )
        append(responsoriumTextAudio(data))
    }
}

fun responsoriumBrevisTitleAudio(
    typus: Int
): AnnotatedString {
    return buildAnnotatedString {
        if (typus > 0) {
            append("${Constants.TITLE_RESPONSORY_SHORT}.")
        } else {
            append(Constants.NOT_RESPONSORY)
        }
    }
}

fun responsoriumTextAudio(
    data: LHResponsoriumBrevis,
): AnnotatedString {
    val respArray = data.responsorium.splitParts("\\|")
    return buildAnnotatedString {
        when (data.typus) {
            0 -> {
                append(textForAudio(data.responsorium))
            }

            1 -> if (respArray.size == 3) {
                append(textForAudio(text = respArray[0]))
                append(" ")
                append(textForAudio(text = respArray[1]))
                append(" ")
                append(textForAudio(text = respArray[2]))
                append(" ")
                append(textForAudio(text = respArray[1]))
            }

            2 -> {
                append(textForAudio(text = respArray[0]))
                append(" ")
                append(textForAudio(text = respArray[1]))
                append(" ")
                append(textForAudio(text = respArray[2]))
                append(" ")
                append(textForAudio(text = respArray[1]))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textForAudio(text = respArray[0]))
                    append(textForAudio(text = respArray[0]))
                    append(textForAudio(text = respArray[1]))
                    append(textForAudio(text = respArray[2]))
                    append(textForAudio(text = respArray[3]))
                    append(textForAudio(text = respArray[0]))
                }

            6001020 -> if (respArray.size == 3) {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[2]))
                append(textForAudio(text = respArray[0]))
            }

            4 -> {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[2]))
                append(textForAudio(text = respArray[0]))
            }

            201 -> {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
            }

            else -> {
                append(LS2)
                append(errorMessageAudio(ErrorHelper.RESPONSORIUM))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(data.typus.toString())

            }
        }
    }
}


fun officiumLectionisAudio(
    officiumLectionis: LHOfficiumLectionis
): AnnotatedString {
    return buildAnnotatedString {
        val list = officiumLectionis.responsorium.splitParts("\\|").toList()
        return buildAnnotatedString {
            append(textForAudio("${Constants.TITLE_OFFICE_OF_READING}."))
            append(textFromListAudio(list))
            append(lectioPriorAudio(officiumLectionis.lectioPrior))
            append(lectioAlteraAudio(officiumLectionis.lectioAltera))
        }
    }
}

fun lectioPriorAudio(
    lectioPrior: MutableList<LHOfficiumLectioPrior>
): AnnotatedString {
    return buildAnnotatedString {

        for (item in lectioPrior) {
            append(textForAudio("${Constants.TITLE_LECTIO_PRIOR}."))
            append(textForAudio("${item.book.liturgyName}."))
            //append(textForAudio("${item.pericopa}.")
            append(textForAudio("${item.tema}."))
            append(textFromHtmlAudio(item.biblica))
            append(textForAudio("${item.book.liturgyName}."))
            append(textForAudio("${Constants.TITLE_RESPONSORY}."))
            append(responsoriumAudio(item.responsorium))
        }
    }
}

fun lectioAlteraAudio(
    lectioAltera: MutableList<LHOfficiumLectioAltera>
): AnnotatedString {
    return buildAnnotatedString {
        for (item in lectioAltera) {
            append(textForAudio("${Constants.TITLE_LECTIO_ALTERA}."))
            append(textForAudio("${item.paterOpus?.opusForView!!}."))
            //append(textForAudio("${item.theSource!!}."))
            append(textForAudio("${item.tema!!}."))
            append(AnnotatedString.fromHtml(item.homilia))
            append(textForAudio("${item.paterOpus?.opusForView!!}."))

            append(textForAudio("${Constants.TITLE_RESPONSORY}."))
            append(responsoriumTextAudio(item.responsorium!!))
        }
    }
}

fun canticumEvangelicumAudio(
    psalmodia: LHPsalmody
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_GOSPEL_CANTICLE}.")
        append(psalmodiaUnicaAudio(psalmodia))
    }
}

fun precesAudio(
    preces: LHIntercession
): AnnotatedString {
    val introArray = preces.intro.splitParts("\\|")
    val listPreces = preces.normalizeForRead().splitParts("∞")
    return buildAnnotatedString {
        if (listPreces.isNotEmpty() && introArray.size == 3) {
            val response = introArray[1]
            val firstPart = listPreces[0].splitParts("§")
            append("${Constants.TITLE_INTERCESSIONS}.")
            append(textForAudio(introArray[0]))
            append(response)
            firstPart.forEach {
                append(it)
                append(response)
            }
            if (listPreces.size == 2) {
                append(listPreces[1])
                append(response)
            }
            append(textForAudio(introArray[2]))
        } else {
            append(errorMessageAudio(ErrorHelper.PRECES))
            append(textForAudio(preces.intro))
            append(" ")
            append(transformTextAudio(text = preces.normalizeForRead()))
        }
    }
}


fun oratioAudio(data: Oratio): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_PRAYER}.")
        append(transformTextAudio(text = data.oratio))
    }
}

fun conclusionisMaiorAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_CONCLUSION}.")
        append(textFromListAudio(RitusConclusionis().maior))
    }
}

fun conclusionisMinorAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_CONCLUSION}.")
        append(textFromListAudio(RitusConclusionis().minor))
    }
}
