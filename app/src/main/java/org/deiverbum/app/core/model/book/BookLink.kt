package org.deiverbum.app.core.model.data.book

/**
 * Clase de datos para enlaces.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2025.1
 * @see [Content]
 *
 */

data class BookLink(
    var previous: String?,
    var url: String,
    var textus: String,
    var posterioris: String?,
    var haveBreak: Boolean = false
)