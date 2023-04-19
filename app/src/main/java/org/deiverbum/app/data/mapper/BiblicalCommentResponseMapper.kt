package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.model.BiblicalCommentResponse
import org.deiverbum.app.domain.model.BiblicalComment

object BiblicalCommentResponseMapper {

    fun BiblicalCommentResponse.toBiblicalComment(): List<BiblicalComment> {
        val allBiblicalComment = ArrayList<BiblicalComment>()
        return allBiblicalComment
    }
}