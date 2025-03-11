package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.universalis.HomeResource
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.core.model.universalis.UniversalisResourceQuery
import org.deiverbum.app.util.DateTimeUtil.isDateValid
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
class GetHomeUseCase @Inject constructor(
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
    ): Flow<HomeResource> {
        return combine(
            userDataRepository.userData,
            universalisRepository.countUniversalis(UniversalisResourceQuery(date)),
        ) { userData, count ->
            if (count == 0 && date.isDateValid()) {
                universalisRepository.insertFromRemote(UniversalisResourceQuery(date))
            }
            val newData = universalisRepository.getUniversalisForTest(date).first()
            HomeResource(
                date = date,
                data = newData,
                count = count,
                dynamic = userData
            )
        }.distinctUntilChanged()
    }
}


