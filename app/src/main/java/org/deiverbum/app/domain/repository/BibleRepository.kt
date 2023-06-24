package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.BibleBookRequest
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.BibleBook
import org.deiverbum.app.model.BibleBooks

/**
 * Interfaz para el repositorio del módulo Biblia.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface BibleRepository {
    /**
     * Este método obtiene el libro de la Biblia.
     *
     * Es implementado en [org.deiverbum.app.data.repository.SyncRepositoryImpl.getSync]
     * @param bibleBookRequest Un objeto [BibleBookRequest] con la solicitud.
     * @return Un objeto [BibleBook] con el libro obtenido.
     */
    suspend fun getBibleBook(bibleBookRequest: BibleBookRequest): BibleBooks
}