package org.deiverbum.app.core.domain

import org.deiverbum.app.core.data.repository.BibleRepository
import org.deiverbum.app.core.model.BibleBookRequest
import org.deiverbum.app.core.model.data.BibleBooks
import javax.inject.Inject

/**
 * Caso de uso para libros de la Biblia.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class GetBibleBookUseCase @Inject constructor(
    private val mRepository: BibleRepository
) {
    /**
     * Obtiene la respuesta del [BibleBookRequest], llamando a [BibleRepository.getBibleBook].
     * @param bookRequest Un objeto [BibleBookRequest] con la información de la sincronización que se solicita.
     * @return Un objeto  [BibleBooks] con el resultado de la sincronización.
     */
    suspend fun execute(bookRequest: BibleBookRequest): BibleBooks {
        return mRepository.getBibleBook(bookRequest)
    }
}