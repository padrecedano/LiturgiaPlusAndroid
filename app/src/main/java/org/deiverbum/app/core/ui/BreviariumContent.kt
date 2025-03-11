package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.component.TextLeftRight
import org.deiverbum.app.core.designsystem.component.TextSmall
import org.deiverbum.app.core.designsystem.component.TextVR
import org.deiverbum.app.core.designsystem.component.textDefault
import org.deiverbum.app.core.designsystem.component.textDefaultItalic
import org.deiverbum.app.core.designsystem.component.textFromHtml
import org.deiverbum.app.core.designsystem.component.textFromHtmlWithMarks
import org.deiverbum.app.core.designsystem.component.textLines
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textVR
import org.deiverbum.app.core.designsystem.component.textWithR
import org.deiverbum.app.core.designsystem.component.textWithV
import org.deiverbum.app.core.designsystem.component.textusRubrica
import org.deiverbum.app.core.designsystem.component.transformBodyText
import org.deiverbum.app.core.designsystem.component.transformEpigraph
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
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
import org.deiverbum.app.core.model.data.breviarium.LHResponsorium
import org.deiverbum.app.core.model.data.breviarium.LHResponsoriumBrevis
import org.deiverbum.app.core.model.data.missae.MissaeLectionumList
import org.deiverbum.app.core.model.liturgia.Introitus
import org.deiverbum.app.core.model.liturgia.Kyrie
import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.core.model.liturgia.PadreNuestro
import org.deiverbum.app.core.model.liturgia.RitusConclusionis
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.TITLE_GOSPEL
import org.deiverbum.app.util.Constants.TITLE_LECTIO_ALTERA
import org.deiverbum.app.util.Constants.TITLE_LECTIO_PRIOR
import org.deiverbum.app.util.Constants.TITLE_RESPONSORY
import org.deiverbum.app.util.ErrorHelper
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.LiturgyHelper.Companion.gloriaNonDicitur
import org.deiverbum.app.util.Utils.toRoman
import org.deiverbum.app.util.firstUpper
import org.deiverbum.app.util.splitParts

/**
 * Pantalla para  Mixto: Oficio + Laudes
 *
 * @param data Una instancia de [BreviariumOfficium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun MixtusContent(
    data: BreviariumMixtus,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    if (data.sanctus != null && data.hasSaint) {
        TextSmall(data.sanctus!!.vitaBrevis, userData.dynamic, bodyStyle)
    }
    introitusPrima(
        userData = userData,
        style = bodyStyle
    )

    invitatorium(
        psalmodia = data.invitatorium,
        userData = userData,
        style = bodyStyle
    )
    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )


    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = -1,
        userData = userData.dynamic,
        style = bodyStyle
    )

    lectioBrevis(
        data = data.lectioBrevis,
        userData = userData.dynamic,
        style = bodyStyle
    )
    officiumLectionis(
        officiumLectionis = data.officiumLectionis,
        userData = userData.dynamic,
        style = bodyStyle
    )

    missaeLectionum(
        lectionum = data.lectionumList,
        userData = userData.dynamic,
        style = bodyStyle
    )

    canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        userData = userData.dynamic,
        style = bodyStyle
    )
    preces(
        preces = data.preces,
        userData = userData.dynamic,
        style = bodyStyle
    )
    paterNoster(
        userData = userData.dynamic,
        style = bodyStyle
    )
    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )
    conclusionisMaior(
        userData = userData.dynamic,
        style = bodyStyle
    )
}


/**
 * Pantalla para  el Oficio
 *
 * @param data Una instancia de [BreviariumOfficium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun OfficiumContent(
    data: BreviariumOfficium,
    userData: UserData

) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge
    if (data.sanctus != null && data.hasSaint) {
        TextSmall(
            text = data.sanctus!!.vitaBrevis,
            userData = userData.dynamic,
            style = bodyStyle
        )
    }
    introitusPrima(
        userData = userData,
        style = bodyStyle
    )

    invitatorium(
        psalmodia = data.invitatorium,
        userData = userData,
        style = bodyStyle
    )
    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )

    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = -1,
        userData = userData.dynamic,
        style = bodyStyle
    )

    officiumLectionis(
        officiumLectionis = data.officiumLectionis,
        userData = userData.dynamic,
        style = bodyStyle
    )

    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )

    conclusionisMaior(
        userData = userData.dynamic,
        style = bodyStyle
    )
}

/**
 * Pantalla para Laudes
 *
 * @param data Una instancia de [BreviariumLaudes]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun LaudesContent(
    data: BreviariumLaudes,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    if (data.sanctus != null && data.hasSaint) {
        TextSmall(
            text = data.sanctus!!.vitaBrevis,
            userData = userData.dynamic,
            style = bodyStyle
        )
    }
    introitusPrima(
        userData = userData,
        style = bodyStyle
    )
    invitatorium(
        psalmodia = data.invitatorium,
        userData = userData,
        style = bodyStyle
    )
    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )
    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = -1,
        userData = userData.dynamic,
        style = bodyStyle
    )
    lectioBrevis(
        data = data.lectioBrevis,
        userData = userData.dynamic,
        style = bodyStyle
    )
    canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        userData = userData.dynamic,
        style = bodyStyle
    )
    preces(
        preces = data.preces,
        userData = userData.dynamic,
        style = bodyStyle
    )
    paterNoster(
        userData = userData.dynamic,
        style = bodyStyle
    )
    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )
    conclusionisMaior(
        userData = userData.dynamic,
        style = bodyStyle
    )
}

/**
 * Pantalla para  las horas intermedias.
 *
 * @param data Una instancia de [BreviariumIntermedia]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun IntermediaContent(
    data: BreviariumIntermedia,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    introitusAltera(
        userData = userData,
        style = bodyStyle
    )

    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )
    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = LiturgyHelper.psalmodiaIndex(data.typusID),
        userData = userData.dynamic,
        style = bodyStyle
    )
    lectioBrevis(
        data = data.lectioBrevis,
        userData = userData.dynamic,
        style = bodyStyle
    )

    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )
    conclusionisMaior(
        userData = userData.dynamic,
        style = bodyStyle
    )
}

/**
 * Pantalla para  Vísperas
 *
 * @param data Una instancia de [BreviariumVesperas]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun VesperasContent(
    data: BreviariumVesperas,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    introitusAltera(
        userData = userData,
        style = bodyStyle
    )

    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )
    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = -1,
        userData = userData.dynamic,
        style = bodyStyle
    )
    lectioBrevis(
        data = data.lectioBrevis,
        userData = userData.dynamic,
        style = bodyStyle
    )
    canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        userData = userData.dynamic,
        style = bodyStyle
    )
    preces(
        preces = data.preces,
        userData = userData.dynamic,
        style = bodyStyle
    )
    paterNoster(
        userData = userData.dynamic,
        style = bodyStyle
    )
    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )
    conclusionisMaior(
        userData = userData.dynamic,
        style = bodyStyle
    )
}


/**
 * Pantalla para  Completas
 *
 * @param data Una instancia de [BreviariumCompletorium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun CompletoriumContent(
    data: BreviariumCompletorium,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    introitusAltera(
        userData = userData,
        style = bodyStyle
    )

    completoriumKyrie(
        data = data.kyrie,
        userData = userData.dynamic,
        style = bodyStyle
    )

    hymnuss(
        data = data.hymnus,
        userData = userData,
        style = bodyStyle
    )
    psalmodia(
        psalmodia = data.psalmodia,
        indexHora = -1,
        userData = userData.dynamic,
        style = bodyStyle
    )
    lectioBrevis(
        data = data.lectioBrevis,
        userData = userData.dynamic,
        style = bodyStyle
    )
    canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        userData = userData.dynamic,
        style = bodyStyle
    )
    oratio(
        data = data.oratio,
        userData = userData.dynamic,
        style = bodyStyle
    )
    conclusionisCompletorium(
        userData = userData.dynamic,
        data = data,
        style = bodyStyle
    )
}


/**
 * Devuelve la introducción de las horas mayores.
 *
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
@Composable
fun introitusPrima(
    userData: UserData,
    style: TextStyle
) {
    HorizontalDivider(
        thickness = 1.dp, modifier = Modifier
            .padding(vertical = 5.dp)
    )
    val text = buildAnnotatedString {
        append(textVR(texts = Introitus().prima))
        append(finisPsalmus())
    }
    ContentTitle(
        Constants.TITLE_INITIAL_INVOCATION,
        2,
        userData.dynamic
    )
    Text(text = text, style = style)
}

/**
 * Crea la introducción de las horas menores.
 **
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
@Composable
fun introitusAltera(
    userData: UserData,
    style: TextStyle
) {
    HorizontalDivider(
        thickness = 1.dp, modifier = Modifier
            .padding(vertical = 5.dp)
    )
    val text = buildAnnotatedString {
        append(textVR(texts = Introitus().altera))
        append(finisPsalmus())
    }
    ContentTitle(
        Constants.TITLE_INITIAL_INVOCATION,
        2,
        userData.dynamic
    )
    Text(text = text, style = style)
}

/**
 * Crea el contenido del Invitatorio.
 *
 * @param psalmodia Un objeto [LHInvitatory] con el invitatorio
 * @param calendarTime Identificador del tiempo litúrgico
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con el invitatorio formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
@Composable
fun invitatorium(
    psalmodia: LHInvitatory,
    userData: UserData,
    style: TextStyle
) {

    SectionTitlee(Constants.TITLE_INVITATORY, 1, userData.dynamic)
    psalmodiaUnica(
        psalmodia = psalmodia,
        userData = userData.dynamic,
        style = style
    )
}

/**
 * Crea el contenido del Himno.
 *
 * @param data Un objeto [LHHymn] con el Himno
 * @param userData Un objeto [UserDataDynamic] con la configuración del usuario
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con el himno formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
//@Composable
@Composable
fun hymnuss(
    data: LHHymn,
    userData: UserData,
    style: TextStyle
) {
    ContentTitle(Constants.TITLE_HYMN, 2, userData.dynamic)
    val text = buildAnnotatedString {
        //append("\n")
        append(transformBodyText(data.hymnus))
        //append("\n")
    }
    Text(text = text, style = style)
}

@Composable
fun hymnus(
    data: LHHymn,
    userData: UserData,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        //append(textLines(2,fontSize))
        append(contentTitle(Constants.TITLE_HYMN, 2, userData.dynamic, rubricColor))
        append(textLines(2, fontSize))
        append(transformBodyText(data.hymnus))
    }
}


/**
 * Crea el contenido de la Salmodia, según los diferentes tipos.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 * @param indexHora El índice de la hora (para el caso de la Hora Intermedia))
 * @param calendarTime Identificador del tiempo litúrgico
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
@Composable
fun psalmodia(
    psalmodia: LHPsalmody,
    indexHora: Int,
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(
        data = Constants.TITLE_PSALMODY,
        level = 2,
        userData = userData
    )



    if (psalmodia.typus == 1) {
        val antiphonaeAnte = AnnotatedString.Builder()
        val antiphonaePost = AnnotatedString.Builder()
        if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
            antiphonaeAnte.append(
                antiphonaeAnte(
                    psalmodia.antiphonae[indexHora],
                    false
                )
            )
            antiphonaePost.append(
                antiphonaePost(
                    psalmodia.antiphonae[indexHora],
                    withExtra = true
                )
            )
        } else {
            antiphonaeAnte.append(
                antiphonaeAnte(
                    psalmodia.antiphonae[0],
                    true
                )
            )
            antiphonaePost.append(
                antiphonaePost(
                    psalmodia.antiphonae[0],
                    withExtra = true
                )
            )
        }
        Text(text = antiphonaeAnte.toAnnotatedString(), style = style)
        val psalmus = buildAnnotatedString {
            for (s in psalmodia.psalmus) {
                psalmus(s, userData, style)
            }
        }
        //Text(text = psalmus, style = style)
        Text(
            text = antiphonaePost.toAnnotatedString(),
            style = style
        )
    }

    if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
        psalmodia.psalmus.forEachIndexed { index, s ->
            Text(
                text = antiphonaeAnte(
                    psalmodia.antiphonae[s.theOrder - 1],
                    true
                ),
                style = style
            )
            psalmus(s, userData, style)
            /*Text(
                text = psalmus(s, userData, style),
                style = style
            )*/
            Text(
                text = antiphonaePost(
                    psalmodia.antiphonae[s.theOrder - 1],
                    withExtra = true
                ),
                style = style
            )
        }
    }
}

/**
 * Crea el texto de la Antífona con el tamaño por defecto.
 *
 * @param text La antífona
 * @param fontSize El tamaño de la fuente, según los ajustes del usuario
 *
 * @return Un objeto [AnnotatedString] con el texto de la antífona.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHAntiphon]
 */
fun antiphonaTextus(
    text: String,
    fontSize: TextUnit
): AnnotatedString {
    return textDefault(text, fontSize)
}

/**
 * Crea el contenido de la Salmodia, cuando hay un solo Salmo / Antífona.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 * @param calendarTime Identificador del tiempo litúrgico
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
@Composable
fun psalmodiaUnica(
    psalmodia: LHPsalmody,
    userData: UserDataDynamic,
    style: TextStyle
) {
    //psalmodia.antiphonae[0].normalizeByTime(calendarTime))
    /*antiphonaeEtPsalmus(
        antiphonae = psalmodia.antiphonae[0],
        withOrder = false,
        psalmus = psalmodia.psalmus[0],
        userData = userData,
        withExtra = false,
        style = style
    )*/
    Text(text = antiphonaeAnte(psalmodia.antiphonae[0], false), style = style)
    psalmus(psalmodia.psalmus[0], userData, style)
    Text(text = antiphonaePost(psalmodia.antiphonae[0], false), style = style)
}

/**
 * Crea el contenido de la Antífona y el Salmo para la Salmodia.
 *
 * @param antiphonae Un objeto [LHAntiphon] con la antífona
 * @param withOrder Para indicar si la antífona lleva orden numérico
 * @param psalmus Un objeto [LHPsalm] con el salmo
 * @param calendarTime Identificador del tiempo litúrgico
 * @param rubricColor El color para las rúbricas según la configuración del usuario
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
@Composable
fun antiphonaeEtPsalmus(
    antiphonae: LHAntiphon,
    withOrder: Boolean,
    psalmus: LHPsalm,
    userData: UserDataDynamic,
    withExtra: Boolean = false,
    style: TextStyle
) {
    Text(text = antiphonaeAnte(antiphonae, withOrder), style = style)
    psalmus(psalmus, userData, style)
    Text(text = antiphonaePost(antiphonae, withExtra), style = style)
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
 * @param rubricColor el color para las rúbricas, según el modo claro u oscuro.
 * @param withOrder Para determinar si se requiere el orden de la antífona.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */
@Composable
fun antiphonaeAnte(
    antiphonae: LHAntiphon,
    withOrder: Boolean = true
): AnnotatedString {
    return buildAnnotatedString {
        if (withOrder) {
            append(textusRubrica("Ant. ${antiphonae.theOrder}. "))
        } else {
            append(textusRubrica("Ant. "))
        }
        if (antiphonae.haveSymbol) {
            append(textusRubrica(" † "))
        }
        append(antiphonae.antiphon)
        //append("\n")

    }
}


/**
 * Prepara para la vista la antífona después del salmo.
 * En la salmodia, las antífonas después del salmo se presentan siempre
 * precedidas de la palabra "Ant. " y luego la antífona.
 *
 * @param antiphonae antífona representada por un objeto [LHAntiphon].
 * @param withExtra booleano para indicar cuando la antífona necesite un espacio extra posterior.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

@Composable
fun antiphonaePost(
    antiphonae: LHAntiphon,
    withExtra: Boolean = true,
): AnnotatedString {
    return buildAnnotatedString {
        append("\n")
        append(textusRubrica("Ant. "))
        append(antiphonae.antiphon)
        if (withExtra) {
            append("\n")
        }
    }
}

/**
 * Prepara un objeto [LHPsalm] para la vista.
 * Verifica si el salmo termina con el marcador "∸", en cuyo caso,
 * se omite al final el "Gloria al Padre ..."
 *
 * @param psalmus salmo representado por un objeto [LHPsalm].
 * @param rubricColor el color para las rúbricas, según el modo claro u oscuro.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

@Composable
fun psalmus(
    psalmus: LHPsalm,
    userData: UserDataDynamic,
    style: TextStyle,
): AnnotatedString {
    return buildAnnotatedString {
        //val normalizeText = psalmus.normalize())
        if (psalmus.pericopa != "") {
            //append("\n")
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = textusRubrica(psalmus.pericopa),
                style = style
            )

            append(textusRubrica(psalmus.pericopa))
            //append("\n\n")
            //append(textLines(1, fontSize))
        }
        if ((psalmus.theme != null) && (psalmus.theme != "")) {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = textusRubrica(psalmus.theme!!),
                style = style
            )

            append(textusRubrica(psalmus.theme!!))
            append("\n\n")

            //append(textLines(1, fontSize))
        }
        if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
            append(transformEpigraph(psalmus.epigraph!!, userData))
            append("\n\n")
            Text(text = transformEpigraph(psalmus.epigraph!!, userData), style = style)
            //append(textLines(1, fontSize))
        }
        if ((psalmus.thePart != null) && (psalmus.thePart != 0)) {
            //append("\n\n")
            //append(textusRubrica(toRoman(psalmus.thePart)))
            //append("\n\n")
            Text(text = textusRubrica(toRoman(psalmus.thePart)), style = style)

            //append(textLines(1, fontSize))
        }
        Text(
            text = buildAnnotatedString {
                append(transformBodyText(text = psalmus.psalmus).trim())
                append(finisPsalmus(psalmus.haveGloria))
            },
            style = style
        )

        //append(textLines(1, fontSize))
        //append("\n")
        //Text(text = finisPsalmus(psalmus.haveGloria), style = style)
        //append(finisPsalmus(psalmus.haveGloria))
    }
}

@Composable
fun lectioBrevis(
    data: LHLectioBrevis,
    userData: UserDataDynamic,
    style: TextStyle
) {


    ContentTitleAndText(
        texts = listOf(Constants.TITLE_SHORT_READING, textusRubrica(data.pericopa).text),
        level = 2,
        userData = userData,
        uppercase = true,
        style = style
    )

    Text(text = data.biblica, style = style)
    responsoriumBrevis(
        data = data.responsorium,
        userData = userData,
        style = style
        )

}

@Composable
fun responsoriumBrevis(
    data: LHResponsoriumBrevis,
    userData: UserDataDynamic,
    style: TextStyle
) {

    responsoriumBrevisTitle(
        typus = data.typus,
        userData = userData,
        style = style
    )
    Text(
        text = responsoriumBrevisText(data),
        style = style
    )

}

@Composable
fun responsoriumText(
    data: LHResponsorium
): AnnotatedString {
    return responsoriumByType(data.responsorium, data.typus)
}

@Composable
fun responsoriumBrevisText(
    data: LHResponsoriumBrevis
): AnnotatedString {
    return responsoriumByType(data.responsorium, data.typus)
}

@Composable
fun responsoriumByType(
    data: String,
    typus: Int
): AnnotatedString {
    val respArray = data.splitParts("\\|")
    return buildAnnotatedString {
        when (typus) {
            0 -> {
                append(data)
            }

            1 -> if (respArray.size == 3) {
                append(textWithR(text = respArray[0]))
                append(textusRubrica(" * "))
                append(respArray[1])
                append("\n\n")
                append(textWithV(text = respArray[2]))
                append("\n\n")
                append(textWithR(text = respArray[1].firstUpper()))
            }

            2 -> {
                append(textWithR(respArray[0]))
                append(textusRubrica(" * "))
                append(respArray[1])
                append("\n\n")
                append(textWithV(respArray[2]))
                append("\n\n")
                append(textWithR(text = respArray[1].firstUpper()))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textWithV(respArray[0]))
                    append("\n")
                    append(textWithR(respArray[0]))
                    append("\n\n")
                    append(textWithV(respArray[1]))
                    append("\n")
                    append(textWithR(respArray[2]))
                    append("\n\n")
                    append(textWithV(respArray[3]))
                    append("\n")
                    append(textWithR(respArray[0]))
                }

            6001020 -> if (respArray.size == 3) {
                append(textWithV(respArray[0]))
                append("\n")
                append(textWithR(respArray[0]))
                append("\n\n")
                append(textWithV(respArray[1]))
                append("\n")
                append(textWithR(respArray[0]))
                append("\n\n")
                append(textWithV(respArray[2]))
                append("\n")
                append(textWithR(respArray[0]))
            }

            4 -> {
                append(textWithV(respArray[0]))
                append("\n")
                append(textWithR(respArray[0]))
                append("\n\n")
                append(textWithV(respArray[1]))
                append("\n")
                append(textWithR(respArray[0]))
                append("\n\n")
                append(textWithV(respArray[2]))
                append("\n")
                append(textWithR(respArray[0]))
            }

            201 -> {
                append(textWithV(respArray[0]))
                append("\n\n")
                append(textWithR(respArray[1]))
            }

            else -> {
                append("\n\n")
                append(errorMessage(ErrorHelper.RESPONSORIUM))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(typus.toString())
                append("\n\n")
            }
        }
    }
}

@Composable
fun responsoriumBrevisTitle(
    typus: Int,
    userData: UserDataDynamic,
    style: TextStyle
) {
    if (typus > 0) {
        ContentTitle(
            Constants.TITLE_RESPONSORY_SHORT,
            2,
            userData
        )
    } else {
        Text(text = textusRubrica(Constants.NOT_RESPONSORY), style = style)
    }
}

@Composable
fun officiumLectionis(
    officiumLectionis: LHOfficiumLectionis,
    userData: UserDataDynamic,
    style: TextStyle
) {
    SectionTitlee(Constants.TITLE_OFFICE_OF_READING, 1, userData)

    val list = officiumLectionis.responsorium.splitParts("\\|").toList()
    TextVR(texts = list, style = style)

    lectioPrior(
        lectioPrior = officiumLectionis.lectioPrior,
        userData = userData,
        style = style
    )

    lectioAltera(
        lectioAltera = officiumLectionis.lectioAltera,
        userData = userData,
        style = style
    )
}

@Composable
fun lectioPrior(
    lectioPrior: MutableList<LHOfficiumLectioPrior>,
    userData: UserDataDynamic,
    style: TextStyle
) {
    for (item in lectioPrior) {
        ContentTitle(TITLE_LECTIO_PRIOR, 2, userData)

        TextLeftRight(
            texts = listOf(item.book.liturgyName, item.pericopa),
            style = style,
            colorCode = 3
        )

        Text(text = textusRubrica(item.tema), style = style)
        Text(
            text = buildAnnotatedString {
                append(textFromHtmlWithMarks(data = item.biblica).trim())
            },
            style = style
        )
        TextLeftRight(
            texts = listOf(TITLE_RESPONSORY, item.responsorium.source),
            style = style,
            colorCode = 0
        )

        Text(
            text = responsoriumText(
                data = item.responsorium
            ), style = style
        )
    }
}

@Composable
fun lectioAltera(
    lectioAltera: MutableList<LHOfficiumLectioAltera>,
    userData: UserDataDynamic,
    style: TextStyle
) {
    for (item in lectioAltera) {
        ContentTitle(TITLE_LECTIO_ALTERA, 2, userData)
        Text(text = item.paterOpus?.opusForView!!, style = style)
        Text(text = textusRubrica(item.theSource!!), style = style)
        Text(text = textusRubrica(item.tema!!), style = style)
        //TextFromHtml(item.homilia, style)
        Text(
            text = buildAnnotatedString {
                append(textFromHtml(data = item.homilia).trim())
            },
            style = style
        )
        TextLeftRight(
            texts = listOf(TITLE_RESPONSORY, item.responsorium!!.source),
            style = style,
            colorCode = 0
        )
        Text(
            text = responsoriumText(
                data = item.responsorium!!
            ), style = style
        )
    }
}

@Composable
fun canticumEvangelicum(
    psalmodia: LHPsalmody,
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(
        data = Constants.TITLE_GOSPEL_CANTICLE,
        level = 2,
        userData = userData
    )
    psalmodiaUnica(psalmodia = psalmodia, userData = userData, style = style)
}

@Composable
fun missaeLectionum(
    lectionum: MissaeLectionumList,
    userData: UserDataDynamic,
    style: TextStyle
): AnnotatedString {
    ContentTitle(TITLE_GOSPEL, 2, userData)

    return buildAnnotatedString {
        for (item in lectionum.lectionum) {
            TextLeftRight(
                texts = listOf(item!!.book.liturgyName, item.pericopa),
                style = style,
                colorCode = 3
            )

            Text(text = textusRubrica(item.tema), style = style)
            lectioSimplex(
                data = item,
                type = -1,
                userData = userData,
                style = style
            )


        }
    }
}

@Composable
fun preces(
    preces: LHIntercession,
    userData: UserDataDynamic,
    style: TextStyle
) {
    val introArray = preces.intro.splitParts("\\|")
    val listPreces = preces.preces.splitParts("∞")
    ContentTitle(Constants.TITLE_INTERCESSIONS, 2, userData, true)

    val text = buildAnnotatedString {

        if (listPreces.isNotEmpty() && introArray.size == 3) {
            val response = introArray[1]
            val firstPart = listPreces[0].splitParts("§")
            append(introArray[0])
            append("\n\n")
            append(textDefaultItalic(response))
            firstPart.forEach {
                append("\n\n")
                append(transformBodyText(it))
                append("\n\n")
                append(textDefaultItalic(response))
            }
            if (listPreces.size == 2) {
                append("\n\n")
                append(
                    transformBodyText(
                        listPreces[1]
                    )
                )

                append("\n\n")
                append(textDefaultItalic(response))
            }
            append("\n\n")
            append(introArray[2])
        } else {
            append(preces.intro)
            append("\n\n")
            append(
                transformBodyText(
                    text = preces.preces
                )
            )

        }
    }
    Text(text = text, style = style)
}

@Composable
fun paterNoster(
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(Constants.TITLE_PATER_NOSTER, 2, userData, true)
    Text(
        text = transformBodyText(
            text = PadreNuestro.texto
        ),
        style = style
    )

}

@Composable
fun oratio(
    data: Oratio,
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(Constants.TITLE_PRAYER, 2, userData, true)
    Text(
        text = transformBodyText(text = data.oratio),
        style = style
    )
}

@Composable
fun conclusionisTitle(
    userData: UserDataDynamic
) {

    ContentTitle(
        data = Constants.TITLE_CONCLUSION,
        level = 2,
        userData = userData,
        uppercase = true
    )
}

@Composable
fun conclusionisMinor(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        conclusionisTitle(userData)
        append(
            textVR(
                texts = RitusConclusionis().minor
            )
        )

    }
}

@Composable
fun conclusionisMaior(
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(
        data = Constants.TITLE_CONCLUSION,
        level = 2,
        userData = userData,
        uppercase = true
    )
    TextVR(
        texts = RitusConclusionis().maior,
        style = style
    )

}

@Composable
fun completoriumKyrie(
    data: Kyrie,
    userData: UserDataDynamic,
    style: TextStyle
) {
    ContentTitle(
        data = Constants.TITLE_SOUL_SEARCHING,
        level = 2,
        userData = userData,
        uppercase = true
    )
    Text(text = textusRubrica(data.primumRubrica), style = style)
    Text(text = data.introductio, style = style)
    Text(text = textusRubrica(data.secundoRubrica), style = style)
    Text(text = transformBodyText(data.kyrie), style = style)
    Text(text = textusRubrica(data.tertiaRubrica), style = style)
    Text(text = textusRubrica(data.quartaRubrica), style = style)
    TextVR(texts = data.conclusio, style = style)
}

@Composable
fun conclusionisCompletorium(
    userData: UserDataDynamic,
    data: BreviariumCompletorium,
    style: TextStyle
) {
    ContentTitle(
        data = Constants.TITLE_CONCLUSION,
        level = 2,
        userData = userData,
        uppercase = true
    )
    TextVR(
        texts = data.conclusio.benedictio,
        style = style
    )
    ContentTitle(
        data = Constants.TITLE_VIRGIN_ANTIHPON,
        level = 2,
        userData = userData,
        uppercase = true
    )
    Text(
        text = AnnotatedString.fromHtml(data.conclusio.antiphon.antiphon),
        style = style
    )
}

@Composable
fun finisPsalmus(haveGloria: Boolean = true): AnnotatedString {
    return buildAnnotatedString {
        if (haveGloria) {
            append("\n")
            append(textSpaced(texts = LiturgyHelper.finisPsalmus))
        } else {
            append("\n\n")
            append(textusRubrica(text = gloriaNonDicitur))
            append("\n")
        }
    }
}

//@Composable
