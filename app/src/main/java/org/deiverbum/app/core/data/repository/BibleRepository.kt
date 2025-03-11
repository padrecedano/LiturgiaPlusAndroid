package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.biblia.BibleBookRequest
import org.deiverbum.app.core.model.biblia.BibleBooks

/**
 * Interfaz para el repositorio del módulo Biblia.
 *
 * @author A. Cedano
 * @since 2024.1
 */
interface BibleRepository {
    /**
     * Este método obtiene el libro de la Biblia.
     *
     * Es implementado en [org.deiverbum.app.core.data.repository.BibleRepositoryImpl.getBibleBook]
     * @param bibleBookRequest Un objeto [BibleBookRequest] con la solicitud.
     * @return Un objeto [BibleBook] con el libro obtenido.
     */
    suspend fun getBibleBook(bibleBookRequest: BibleBookRequest): BibleBooks
}