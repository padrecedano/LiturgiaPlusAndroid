package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.model.alteri.AlteriRosarium
import org.deiverbum.app.core.model.alteri.AlteriSanctii
import org.deiverbum.app.core.model.breviarium.BreviariumCompletorium
import org.deiverbum.app.core.model.breviarium.BreviariumIntermedia
import org.deiverbum.app.core.model.breviarium.BreviariumLaudes
import org.deiverbum.app.core.model.breviarium.BreviariumMixtus
import org.deiverbum.app.core.model.breviarium.BreviariumOfficium
import org.deiverbum.app.core.model.breviarium.BreviariumVesperas
import org.deiverbum.app.core.model.breviarium.LHOfficiumPascua
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.data.Normalizable
import org.deiverbum.app.core.model.data.Sortable
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.universalis.MetaData
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils

@Composable
fun LiturgiaHeader(
    id: Int,
    date: Int,
    userData: UserData,
    metaData: MetaData
) {

    val date = Utils.formatDate(
        date.toString(),
        "yyyyMMdd",
        "EEEE d 'de' MMMM 'de' yyyy"
    )

    ContentLabel(
        data = date,
        level = 1,
        userData = userData,
        uppercase = false,
        withColor = false
    )
    Spacer(modifier = Modifier.width(100.dp))

    ContentHeadd(
        metaData.tempus,
        3,
        userData,
        false
    )

    SectionTitlee(metaData.nomen, 2, userData, false)
    ContentHeadd(
        text = LiturgyHelper.titulusMap[id].toString(),
        level = 2,
        userData = userData,
        uppercase = false,
        withColor = true
    )
}

@Composable
fun UniversalisBodyForView(
    resource: UniversalisResource,
) {
    val universalis = resource.data.liturgia!!.liturgiaTypus
    if (universalis is Sortable) {
        universalis.sort()
    }
    if (universalis is Normalizable) {
        universalis.normalizeByTime(resource.data.timeFK)
    }
    val userData = resource.dynamic

    Row(
        modifier = Modifier.padding(1.dp),
        //modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {

            if (resource.id < 20) {
                LiturgiaHeader(
                    id = resource.id,
                    date = resource.date,
                    userData = resource.dynamic,
                    metaData = resource.metaData,
                )
            }

            when (universalis) {
                is BreviariumMixtus -> {
                    MixtusContent(universalis, userData)
                }

                is LHOfficiumPascua -> {
                    OfficiumEasterContent(universalis, userData)
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
                    MissaeContent(universalis, resource.id, userData)
                }

                is Commentarii -> {
                    CommentariiContent(universalis, userData)
                }

                is AlteriSanctii -> {
                    SanctiiContent(
                        data = universalis,
                        userData = userData,
                        metaData = resource.metaData
                    )
                }

                is AlteriRosarium -> {
                    RosariumContent(
                        data = universalis,
                        userData = userData,
                        metaData = resource.metaData
                    )
                }
            }
        }
    }
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




