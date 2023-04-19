package org.deiverbum.app.data.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.factory.BiblicalCommentFactory
import org.deiverbum.app.data.factory.HomilyFactory
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.repository.BiblicalCommentRepository
import org.deiverbum.app.domain.repository.HomilyRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

class BiblicalCommentRepositoryImpl @Inject constructor(
    private val biblicalCommentFactory: BiblicalCommentFactory
) : BiblicalCommentRepository {

    override suspend fun getBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder {
        return biblicalCommentFactory.create(Source.LOCAL).getBiblicalComment(biblicalCommentRequest)
            .ifEmpty { syncBiblicalComment(biblicalCommentRequest) }
    }

    private suspend fun syncBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder {
        return biblicalCommentFactory.create(Source.NETWORK).getBiblicalComment(biblicalCommentRequest)
            .also { biblicalCommentFromNetwork ->
                biblicalCommentFactory.create(Source.LOCAL).addBiblicalComment(biblicalCommentFromNetwork)
            }
    }
}