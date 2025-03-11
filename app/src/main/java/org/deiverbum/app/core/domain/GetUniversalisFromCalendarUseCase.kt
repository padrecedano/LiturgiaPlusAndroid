package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.core.model.universalis.UniversalisResource
import javax.inject.Inject

/**
 * Este caso de uso obtiene los Universalis de una fecha dada en `Calendar`.
 * Si no los encuentra ... ¿en la base de datos local, llama al método de inserción
 * en la fuente de datos remota?
 *
 **
 *
 * @param universalisRepository Repositorio que obtiene los datos.
 * @param userDataRepository Repositorio con los datos del usuario.
 */
class GetUniversalisFromCalendarUseCase @Inject constructor(
    private val universalisRepository: UniversalisRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Retorna un objeto [UniversalisResource].
     *
     * @param sortBy El campo usado para filtrar. Por defecto NONE = no filtrar.
     */

    operator fun invoke(date: Int, topicId: String): Flow<Universalis> {
        return universalisRepository.getUniversalisForTest(date)
    }

    /*
        operator fun invoke(
            searchQuery: UniversalisResourceQuery,
        ): Flow<UniversalisSearchResult> =
            universalisRepository.getOneUniversalisByDate(searchQuery)
                .mapToUserSearchResult(userDataRepository.userData)
    */


}
/*
private fun Flow<UniversalisSearchResult>.mapToUserSearchResult(userDataStream: Flow<UserData>): Flow<UniversalisSearchResult> =
    combine(userDataStream) { searchResult, userData ->
        UniversalisSearchResult(
            topics = searchResult.topicId. { topic ->
                FollowableTopic(
                    topic = topic,
                    isFollowed = true,//topic. in userData.followedTopics,
                )
            },
            newsResources = searchResult.newsResources.map { news ->
                UserCalendarResource(
                    newsResource = news,
                    userData = userData,
                )
            },
        )
    }

 */