package org.deiverbum.app.core.model.universalis

import org.deiverbum.app.util.Source

/**
 * Maneja las respuestas destinadas al módulo Today.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class UniversalisResponse(
    var dataModel: Universalis,
    var source: Source,
    var success: Boolean
) {
    constructor() : this(Universalis(), Source.LOCAL, success = true)
}