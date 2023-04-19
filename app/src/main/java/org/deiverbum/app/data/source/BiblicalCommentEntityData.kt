package org.deiverbum.app.data.source

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.HomilyRequest

interface BiblicalCommentEntityData {

    suspend fun getBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder

    suspend fun addBiblicalComment(biblicalComment: SpannableStringBuilder)
}