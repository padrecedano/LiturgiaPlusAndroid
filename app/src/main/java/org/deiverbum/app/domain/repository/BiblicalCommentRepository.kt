package org.deiverbum.app.domain.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.HomilyRequest

interface BiblicalCommentRepository {

    suspend fun getBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder

}