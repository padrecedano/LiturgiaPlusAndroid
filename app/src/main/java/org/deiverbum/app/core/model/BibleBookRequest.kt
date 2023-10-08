package org.deiverbum.app.core.model


/**
 * Recoge los parámetros que son enviados a las peticiones para el módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 *
 * @param bookId Id del libro que se solicita.

 */
class BibleBookRequest(
    var bookId: Int =0
)