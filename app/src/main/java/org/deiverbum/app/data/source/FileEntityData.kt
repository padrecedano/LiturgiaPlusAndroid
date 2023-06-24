package org.deiverbum.app.data.source

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest

/**
 * Interfaz para manejar las fuentes de datos de archivos.
 **
 * @author A. Cedano
 * @since 2023.1.3
 */
interface FileEntityData {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>

    suspend fun addFile(file: String)
}