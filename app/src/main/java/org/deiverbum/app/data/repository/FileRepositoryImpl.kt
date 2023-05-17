package org.deiverbum.app.data.repository

import org.deiverbum.app.data.factory.FileFactory
import org.deiverbum.app.data.model.FileResponse
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.repository.FileRepository
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

    override suspend fun getFile(fileRequest: FileRequest): FileResponse {
        return fileFactory.create().getFile(fileRequest)
    }

}