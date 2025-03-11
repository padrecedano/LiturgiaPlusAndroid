package org.deiverbum.app.core.model.biblia


/**
 * Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2024.1
 *
 * @param bookId Id del libro que se solicita.

 */
class BibleBookRequest(
    var bookId: Int =0
)