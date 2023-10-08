package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.data.factory.FileFactory
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo File.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
class FileRepositoryImpl @Inject constructor(
    private val fileFactory: FileFactory
) : FileRepository {

    override suspend fun getFile(fileRequest: FileRequest): MutableList<FileResponse> {
        return fileFactory.create().getFile(fileRequest)
    }

}