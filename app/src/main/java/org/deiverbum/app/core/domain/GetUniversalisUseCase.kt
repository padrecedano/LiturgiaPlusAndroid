package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.model.data.UniversalisResourceQuery
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
     * @param sortBy El campo usado para filtrar. Por defecto NONE = no filtrar.
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
            if (universalis.todayDate == 0 && selectedTopicId != "30") {
                universalisRepository.insertFromRemote(
                    UniversalisResourceQuery(
                        date,
                        selectedTopicId.toInt()
                    )
                )
            }
            UniversalisResource(
                date = universalis.todayDate, //TODO:Este flujo se repite ¿?
                title = title,
                name = "",
                id = selectedTopicId.toInt(),
                data = universalis,
                dynamic = userData
            )
        }
    }
}


