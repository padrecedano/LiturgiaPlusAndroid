package org.deiverbum.app.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.deiverbum.app.data.source.remote.RemoteDataSource
import org.deiverbum.app.data.wrappers.BaseApiResponse
import org.deiverbum.app.data.wrappers.NetworkResult
import org.deiverbum.app.model.Today
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
    suspend fun getTest(theDate:String): Flow<NetworkResult<Today>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTest(theDate) })
        }.flowOn(Dispatchers.IO)
    }
}
