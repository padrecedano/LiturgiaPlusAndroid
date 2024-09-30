package org.deiverbum.app.core.domain

import org.deiverbum.app.core.data.repository.LocalFileRepository
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.FileResponse
import javax.inject.Inject

/**
 * Caso de uso archivos locales.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class GetFileUseCase @Inject constructor(
    private val fileRepository: LocalFileRepository
) {

    suspend operator fun invoke(fileRequest: FileRequest): MutableList<FileResponse> =
        fileRepository.getFile(fileRequest)
}