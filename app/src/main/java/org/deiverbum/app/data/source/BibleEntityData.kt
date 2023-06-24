package org.deiverbum.app.data.source

import org.deiverbum.app.domain.model.BibleBookRequest
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.BibleBook
import org.deiverbum.app.model.BibleBooks

/**
 * Interfaz para manejar las fuentes de datos del módulo `Biblia`.
 **
 * @author A. Cedano
 * @since 2023.1.3
 */
interface BibleEntityData {

    /**
     * Este método recibe una petición de libro expresada
     * en un objeto del tipo [BibleBookRequest].
     *
     * @param bookRequest es un objeto [BibleBookRequest] con la petición de sincronización.
     * @return Un objeto [BibleBook].
     */
    suspend fun getBibleBook(bookRequest: BibleBookRequest): BibleBooks



}