package org.deiverbum.app.data.source

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest

interface BibleEntityData {

    suspend fun getBible(bibleRequest: BibleRequest): List<Bible>

    suspend fun addBible(bibles: List<Bible>)
}