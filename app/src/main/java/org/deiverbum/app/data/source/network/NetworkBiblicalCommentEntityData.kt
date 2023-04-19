package org.deiverbum.app.data.source.network

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.mapper.BiblicalCommentResponseMapper.toBiblicalComment
import org.deiverbum.app.data.source.BiblicalCommentEntityData
import org.deiverbum.app.domain.model.BiblicalComment
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import javax.inject.Inject

class NetworkBiblicalCommentEntityData @Inject constructor(
    private val todayApi: TodayApi
) : BiblicalCommentEntityData {

    override suspend fun getBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        return sb;
    }

    override suspend fun addBiblicalComment(biblicalComment: SpannableStringBuilder) {
        //no op
    }
}