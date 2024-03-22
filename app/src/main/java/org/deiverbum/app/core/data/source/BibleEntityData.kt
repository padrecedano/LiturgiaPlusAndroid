package org.deiverbum.app.core.data.source

import org.deiverbum.app.core.model.BibleBookRequest
import org.deiverbum.app.core.model.data.BibleBook
import org.deiverbum.app.core.model.data.BibleBooks

/**
 * Interfaz para manejar las fuentes de datos del módulo `Biblia`.
 **
 * @author A. Cedano
 * @since 2024.1
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