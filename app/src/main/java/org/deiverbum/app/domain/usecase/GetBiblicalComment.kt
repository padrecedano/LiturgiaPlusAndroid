package org.deiverbum.app.domain.usecase

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.repository.BiblicalCommentRepository
import org.deiverbum.app.domain.repository.HomilyRepository
import javax.inject.Inject

class GetBiblicalComment @Inject constructor(
    private val mRepository: BiblicalCommentRepository
) {
    suspend fun execute(request: BiblicalCommentRequest): SpannableStringBuilder {
        return mRepository.getBiblicalComment(request)
    }
}