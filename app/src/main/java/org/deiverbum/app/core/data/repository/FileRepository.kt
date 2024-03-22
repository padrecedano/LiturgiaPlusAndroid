package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse

/**
 * <p>Interfaz para el repositorio del módulo de archivos locales.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
interface FileRepository {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>
}