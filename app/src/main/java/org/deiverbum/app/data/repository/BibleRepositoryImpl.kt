package org.deiverbum.app.data.repository

import org.deiverbum.app.data.factory.BibleFactory
import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import org.deiverbum.app.domain.repository.BibleRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val bibleFactory: BibleFactory
) : BibleRepository {

    override suspend fun getBible(bibleRequest: BibleRequest): List<Bible> {
        return bibleFactory.create(Source.LOCAL).getBible(bibleRequest)
            .ifEmpty { syncBible(bibleRequest) }
    }

    private suspend fun syncBible(bibleRequest: BibleRequest): List<Bible> {
        return bibleFactory.create(Source.NETWORK).getBible(bibleRequest)
            .also { bibleFromNetwork ->
                bibleFactory.create(Source.LOCAL).addBible(bibleFromNetwork)
            }
    }
}