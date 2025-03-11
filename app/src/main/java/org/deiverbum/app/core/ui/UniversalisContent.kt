package org.deiverbum.app.core.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.Sortable
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.alteri.AlteriRosarium
import org.deiverbum.app.core.model.data.alteri.AlteriSanctii
import org.deiverbum.app.core.model.data.breviarium.BreviariumCompletorium
import org.deiverbum.app.core.model.data.breviarium.BreviariumIntermedia
import org.deiverbum.app.core.model.data.breviarium.BreviariumLaudes
import org.deiverbum.app.core.model.data.breviarium.BreviariumMixtus
import org.deiverbum.app.core.model.data.breviarium.BreviariumOfficium
import org.deiverbum.app.core.model.data.breviarium.BreviariumVesperas
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils


//@ExperimentalStdlibApi
@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun UniversalisBody(
    resource: UniversalisResource,
    topicId: Int,
    userData: UserData,
) {
    val onTap = { _: Offset -> }
    //val rubricColor = LocalCustomColorsPalette.current.rubricColor
    val rubricColor = MaterialTheme.colorScheme.error
    val data = resource.data
    if (data.liturgia!!.liturgiaTypus is Sortable) {
        data.liturgia!!.liturgiaTypus?.sort()
    }
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val fontSize = typography.bodyLarge.fontSize
    //data.liturgia!!.liturgiaTypus?.normalizeByTime(data.timeFK)

    val text = buildAnnotatedString {
        append(
            contentHead(
                resource.metaData.tempus,
                3,
                userData.dynamic,
                fontSize,
                rubricColor,
                false
            )
        )
        //append(textLines(1,fontSize))
        append(contentSpace(1))

        append(sectionTitle(resource.metaData.nomen, 2, resource.dynamic.dynamic, false))
        append(contentSpace(2))
        append(
            contentHead(
                resource.metaData.liturgia,
                1,
                userData.dynamic,
                fontSize,
                rubricColor,
                false,
                true
            )
        )
    }

}


@Composable
fun universalisBodyForVieww(
    resource: UniversalisResource,
) {
    val onTap = { _: Offset -> }
    val rubricColor = MaterialTheme.colorScheme.error
    val universalis = resource.data.liturgia!!.liturgiaTypus
    //val data = universalis.liturgia!!.liturgiaTypus
    if (universalis is Sortable) {
        universalis.sort()
    }
    val userData = resource.dynamic
    val calendarTime = resource.data.timeFK
    val typography = getPersonalizedTypography(resource.dynamic.dynamic.fontSize)
    val fontSize = typography.bodyLarge.fontSize
    val date = Utils.formatDate(
        resource.date.toString(),
        "yyyyMMdd",
        "EEEE d 'de' MMMM 'de' yyyy"
    )

    /*Text(
        text=contentHead(LiturgyHelper.titulusMap[resource.id].toString() ,1,userData.dynamic,fontSize,rubricColor,false,true),
                //lineHeight = fontSize*2
    )*/
    //ContentLabell(resource,1,userData.dynamic,rubricColor,false)

    //contentHeadd(resource.metaData.tempus,3,userData.dynamic,fontSize,rubricColor,false)


    Row(
        modifier = Modifier.padding(1.dp),
        // causes narrow chips
        //modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            ContentLabell(
                data = date,
                level = 1,
                userData = userData,
                rubricColor = rubricColor,
                uppercase = false,
                withColor = false
            )
            Spacer(modifier = Modifier.width(100.dp))

            ContentHeadd(
                resource.metaData.tempus,
                3,
                userData,
                fontSize,
                rubricColor,
                false
            )

            SectionTitlee(resource.metaData.nomen, 2, resource.dynamic.dynamic, false)
            ContentHeadd(
                LiturgyHelper.titulusMap[resource.id].toString(),
                2,
                userData,
                fontSize,
                rubricColor,
                false,
                true
            )

            when (universalis) {
                is BreviariumMixtus -> {
                    MixtusContent(universalis, userData)
                }

                is BreviariumOfficium -> {
                    OfficiumContent(universalis, userData)

                }

                is BreviariumLaudes -> {
                    LaudesContent(universalis, userData)
                }

                is BreviariumIntermedia -> {
                    IntermediaContent(universalis, userData)
                }

                is BreviariumVesperas -> {
                    VesperasContent(universalis, userData)
                }

                is BreviariumCompletorium -> {
                    CompletoriumContent(universalis, userData)
                }

                is Missae -> {
                    //append(audioMissae(data, resource.id))
                    MissaeLectionumContent(universalis, userData, fontSize)

                }

                is Commentarii -> {
                    //append(audioCommentarii(data))
                }

                is AlteriSanctii -> {
                    //append(audioSanctii(data))
                }

                is AlteriRosarium -> {
                    //append(audioRosarium(data))
                }

            }
        }

    }


}

//@Composable
@Composable
fun universalisBodyForView(
    resource: UniversalisResource,
): AnnotatedString {
    val onTap = { _: Offset -> }
    val rubricColor = MaterialTheme.colorScheme.error
    val universalis = resource.data.liturgia!!.liturgiaTypus
    //val data = universalis.liturgia!!.liturgiaTypus
    if (universalis is Sortable) {
        universalis.sort()
    }
    val userData = resource.dynamic
    val calendarTime = resource.data.timeFK
    val typography = getPersonalizedTypography(resource.dynamic.dynamic.fontSize)
    val fontSize = typography.bodyLarge.fontSize
    val date = Utils.formatDate(
        resource.date.toString(),
        "yyyyMMdd",
        "EEEE d 'de' MMMM 'de' yyyy"
    )

    /*Text(
        text=contentHead(LiturgyHelper.titulusMap[resource.id].toString() ,1,userData.dynamic,fontSize,rubricColor,false,true),
                //lineHeight = fontSize*2
    )*/
    ContentLabell(resource, 1, userData.dynamic, rubricColor, false)

    //contentHeadd(resource.metaData.tempus,3,userData.dynamic,fontSize,rubricColor,false)


    val text = buildAnnotatedString {
        append(contentLabel(date, 1, userData.dynamic, rubricColor, false))
        append(contentSpace(1))

        //append(sectionTitle(date, 2, userData.dynamic, false))
        //append(contentSpace(1))

        append(
            contentHead(
                resource.metaData.tempus,
                3,
                userData.dynamic,
                fontSize,
                rubricColor,
                false
            )
        )
        //append(textLines(1,fontSize))
        append(contentSpace(1))

        append(sectionTitle(resource.metaData.nomen, 2, resource.dynamic.dynamic, false))
        append(contentSpace(2))
        append(
            contentHead(
                LiturgyHelper.titulusMap[resource.id].toString(),
                1,
                userData.dynamic,
                fontSize,
                rubricColor,
                false,
                true
            )
        )

        append(contentSpace(2))

        when (universalis) {
            is BreviariumMixtus -> {
                //append(viewMixtus(universalis,userData,rubricColor,calendarTime, onTap,fontSize))

            }

            is BreviariumOfficium -> {
                append(audioOfficium(universalis))
            }

            is BreviariumLaudes -> {
                //append(audioLaudes(data))
            }

            is BreviariumIntermedia -> {
                //append(audioIntermedia(data))
            }

            is BreviariumVesperas -> {
                //append(audioVesperas(data))
            }

            is BreviariumCompletorium -> {
                //append(audioCompletorium(data))
            }

            is Missae -> {
                //append(audioMissae(data, resource.id))
            }

            is Commentarii -> {
                //append(audioCommentarii(data))
            }

            is AlteriSanctii -> {
                //append(audioSanctii(data))
            }

            is AlteriRosarium -> {
                //append(audioRosarium(data))
            }

        }
    }
    //Text(text=text)

    return text
}

fun universalisBodyForRead(
    resource: UniversalisResource,
): AnnotatedString {
    return buildAnnotatedString {
        append(
            Utils.formatDate(
                resource.date.toString(),
                "yyyyMMdd",
                "EEEE d 'de' MMMM 'de' yyyy"
            )
        )

        append(".")
        append("${resource.metaData.liturgia}.")
        append("${resource.metaData.tempus}.")
        append("${resource.metaData.nomen}.")

        when (val typus = resource.data.liturgia!!.liturgiaTypus) {
            is BreviariumMixtus -> {
                append(audioMixtus(typus))
            }

            is BreviariumOfficium -> {
                append(audioOfficium(typus))
            }

            is BreviariumLaudes -> {
                append(audioLaudes(typus))
            }

            is BreviariumIntermedia -> {
                append(audioIntermedia(typus))
            }

            is BreviariumVesperas -> {
                append(audioVesperas(typus))
            }

            is BreviariumCompletorium -> {
                append(audioCompletorium(typus))
            }

            is Missae -> {
                append(audioMissae(typus, resource.id))
            }

            is Commentarii -> {
                append(audioCommentarii(typus))
            }

            is AlteriSanctii -> {
                append(audioSanctii(typus))
            }

            is AlteriRosarium -> {
                append(audioRosarium(typus))
            }

        }
    }
}




