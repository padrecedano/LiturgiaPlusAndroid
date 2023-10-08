package org.deiverbum.app.core.data.source

import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse

/**
 * Interfaz para manejar las fuentes de datos de archivos.
 **
 * @author A. Cedano
 * @since 2023.1.3
 */
interface FileEntityData {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>

    suspend fun addFile(fileName: String)
}