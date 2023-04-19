package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest

interface BibleRepository {

    suspend fun getBible(bibleRequest: BibleRequest): List<Bible>
}