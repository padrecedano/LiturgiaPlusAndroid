package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.BiblicalCommentEntityData
import org.deiverbum.app.domain.model.BiblicalComment
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import javax.inject.Inject

class LocalBiblicalCommentEntityData @Inject constructor(
    private val todayDao: TodayDao
) : BiblicalCommentEntityData {

    override suspend fun getBiblicalComment(biblicalCommentRequest: BiblicalCommentRequest): SpannableStringBuilder {
        val allBiblicalComment: List<BiblicalComment> = emptyList()//todayDao.getBiblicalComment().toBiblicalCommentDomainModel()
        val sb = SpannableStringBuilder()
        allBiblicalComment.forEach {
            sb.append("\n\n")
            sb.append(it.homilyID.toString())
            sb.append("\n\n")
            sb.append(it.homily)
        }
        return sb
    }

    override suspend fun addBiblicalComment(biblicalComment: SpannableStringBuilder) {
        //prayDao.insert(biblicalComment)
    }
}