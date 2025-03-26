package org.deiverbum.app.core.model.book

import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.model.cic.Chapter

@JsonClass(generateAdapter = true)
data class LiberTitle(
    val n: Int,
    val txt: String,
    val intro: List<CICIntro>,
    val content: List<Chapter>
)

@JsonClass(generateAdapter = true)
data class CICIntro(val type: String, val n: Int, val txt: List<String>)
