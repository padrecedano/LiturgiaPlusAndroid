package org.deiverbum.app.domain.repository

import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest

/**
 * <p>Interfaz para el repositorio del módulo de archivos locales.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface FileRepository {

    suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse>
}