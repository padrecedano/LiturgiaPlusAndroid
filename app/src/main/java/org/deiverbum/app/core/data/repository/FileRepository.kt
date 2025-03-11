package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.model.data.FileRequestt
import org.deiverbum.app.core.model.data.FileResource
import org.deiverbum.app.core.model.data.FileResponse

/**
 * <p>Interfaz para el repositorio del módulo de archivos locales.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
interface FileRepository {

    suspend fun getFile(fileRequest: FileRequestt): List<FileResponse>
    suspend fun getFileModel(fileRequest: FileRequestt): FileResource

}