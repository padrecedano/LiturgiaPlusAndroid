package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.UniversalisRequest
import javax.inject.Inject

/**
 * Este caso de uso obtiene los Universalis de la fecha dada.
 * Si no los encuentra en la base de datos local, llama al método de inserción
 * en la fuente de datos remota.
 *
 * Se mantiene un modelo de lista,
 * para poder tener en un futuro varias celebraciones posibles
 * por ejemplo, cuando hay una celebración de la feria y una memoria.
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
     * Retorna una lista de objetos [UniversalisRequest].
     *
     * @param sortBy El campo usado para filtrar. Por defecto NONE = no filtrar.
     */
    operator fun invoke(
        sortBy: HomeSortField = HomeSortField.NONE,
        date: Int,
        selectedTopicId: String,
        title: String
    ): Flow<List<UniversalisRequest>> {
        return combine(
            userDataRepository.userData,
            universalisRepository.getUniversalisByDate(
                UniversalisResourceQuery(
                    setOf(date),
                    selectedTopicId.toInt()
                )
            ),

            ) { userData, topics ->
            if (topics.isEmpty() || topics[0].data.isEmpty()) {
                universalisRepository.insertFromRemote(
                    UniversalisResourceQuery(
                        setOf(date),
                        selectedTopicId.toInt()
                    )
                )
            }
            val followedTopics = topics.map { topic ->
                UniversalisRequest(
                    date = topics[0].data[0].todayDate, //TODO:Este flujo se repite ¿?
                    title = title,
                    name = "",
                    id = selectedTopicId.toInt(),
                    data = topic.data,
                    dynamic = userData.dynamic
                )
            }.sortedBy { it.date }
            when (sortBy) {
                HomeSortField.ID -> followedTopics.sortedBy { it.id }
                else -> followedTopics
            }
        }.filterNotNull()
    }
}