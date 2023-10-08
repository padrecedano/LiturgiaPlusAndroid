package org.deiverbum.app.core.data.factory

import org.deiverbum.app.core.data.source.FileEntityData
import org.deiverbum.app.core.data.source.local.LocalFileEntityData
import javax.inject.Inject

/**
 * <p>Factory para el módulo que lee archivos locales. Fuente de datos única.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

class FileFactory @Inject constructor(
    private val localFileEntityData: LocalFileEntityData
) {

    fun create(): FileEntityData {
        return localFileEntityData
    }
}