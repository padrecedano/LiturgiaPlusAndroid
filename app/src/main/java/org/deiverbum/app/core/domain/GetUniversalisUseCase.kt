package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.universalis.MetaData
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.core.model.universalis.UniversalisResourceQuery
import org.deiverbum.app.util.DateTimeUtil
import javax.inject.Inject

/**
 * Este caso de uso obtiene los Universalis de la fecha dada.
 * Si no los encuentra en la base de datos local, llama al método de inserción
 * en la fuente de datos remota.
 *
 * Más adelante se volverá un modelo de lista,
 * para poder tener varias celebraciones posibles
 * por ejemplo, cuando haya una celebración de la feria y otra de memoria.
 **
 *
 * @param universalisRepository Repositorio que obtiene los datos.
 * @param userDataRepository Repositorio con los datos del usuario.
 */
class GetUniversalisUseCase @Inject constructor(
    private val universalisRepository: UniversalisRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Retorna una lista de objetos [UniversalisResource].
     *
     */
    operator fun invoke(
        date: Int,
        selectedTopicId: String,
        title: String
    ): Flow<UniversalisResource> {
        return combine(
            userDataRepository.userData,
            universalisRepository.getUniversalisByDate(
                UniversalisResourceQuery(
                    filterDate = date,
                    selectedTopicId.toInt()
                )
            ),
        ) { userData, universalis ->
            if (universalis.todayDate == 0 && selectedTopicId.toInt() < 19) {
                universalisRepository.insertFromRemote(
                    UniversalisResourceQuery(
                        date,
                        selectedTopicId.toInt()
                    )
                )
            }

            val resourceTitle = when (selectedTopicId) {
                "7" -> universalis.liturgia!!.liturgiaTypus!!.getTitle()
                else -> title
            }
            val metaData = when (selectedTopicId) {
                "7" -> MetaData(
                    liturgia = universalis.liturgia!!.liturgiaTypus!!.getTitle(),
                    tempus = universalis.liturgia!!.tempus!!.externus!!,
                    nomen = universalis.liturgia!!.nomen,

                    )

                "20" -> MetaData(
                    liturgia = title,
                    tempus = universalis.liturgia!!.nomen,
                    nomen = universalis.liturgia!!.liturgiaTypus!!.getTitle(),

                    )

                "30" -> MetaData(
                    liturgia = title,
                    tempus = "Santo Rosario",//universalis.liturgia!!.liturgiaTypus!!.typus,
                    nomen = DateTimeUtil.dayName(date),

                    )

                else -> MetaData(
                    liturgia = title,
                    tempus = universalis.liturgia!!.tempus!!.externus!!,
                    nomen = universalis.liturgia!!.nomen,

                    )
            }
            UniversalisResource(
                date = date,
                title = resourceTitle,
                meta = "",
                metaData = metaData,
                id = selectedTopicId.toInt(),
                data = universalis,
                dynamic = userData
            )
        }

    }
}


