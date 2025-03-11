package org.deiverbum.app.core.data.source

import org.deiverbum.app.core.model.data.FileRequest
import org.deiverbum.app.core.model.data.FileResponse

/**
 * Interfaz para manejar las fuentes de datos de archivos.
 **
 * @author A. Cedano
 * @since 2024.1
 */
interface FileEntityData {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>

    suspend fun addFile(fileName: String)
}