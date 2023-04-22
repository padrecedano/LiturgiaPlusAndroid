package org.deiverbum.app.data.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.factory.FileFactory
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.repository.FileRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileFactory: FileFactory
) : FileRepository {

    override suspend fun getFile(fileRequest: FileRequest): String {
        return fileFactory.create(Source.LOCAL).getFile(fileRequest)
            //.ifEmpty { syncFile(fileRequest) }
    }

}