package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.domain.model.BiblicalComment
import org.deiverbum.app.domain.model.Homily

object BiblicalCommentEntityMapper {

    fun List<BiblicalComment>.toBiblicalCommentDomainModel(): List<BiblicalComment> {
        val allBiblicalComment = ArrayList<BiblicalComment>()
        forEach {
            allBiblicalComment.add(BiblicalComment(it.homilyID,it.homily))
        }
        return allBiblicalComment
    }
}