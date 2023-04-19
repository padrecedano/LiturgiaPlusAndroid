package org.deiverbum.app.data.source.remote

import org.deiverbum.app.data.source.remote.network.TestService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val testService: TestService) {
    suspend fun getTest(theDate:String) =
        testService.getToday("tercia",theDate)
}
