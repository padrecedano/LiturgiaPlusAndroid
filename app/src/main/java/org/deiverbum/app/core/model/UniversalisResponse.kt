package org.deiverbum.app.core.model

import org.deiverbum.app.core.model.data.Universalis
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