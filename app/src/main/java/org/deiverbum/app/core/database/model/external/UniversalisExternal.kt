package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

/**
 * Representación de Universalis para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Universalis
 */
data class UniversalisExternal(
    @Embedded
    var universalis: UniversalisEntity,
)

fun UniversalisExternal.asExternalModel() = UniversalisResource(
    data =
    listOf(
        universalis.asExternalModel()
    )
)