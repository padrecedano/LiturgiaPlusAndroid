package org.deiverbum.app.core.file

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.deiverbum.app.core.model.book.BookTest
import org.deiverbum.app.util.FileUtils
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFiles @Inject constructor(@ApplicationContext val context: Context) {

    suspend fun getTopicsForSuggestion(): Flow<List<BookTest>> =
        flow {
            val data = FileUtils.readRawFile(context, "raw/oratio/angelus.json")
                .run {
                    return@run Moshi.Builder().build().adapter<List<BookTest>>(
                        Types.newParameterizedType(
                            List::class.java,
                            BookTest::class.java
                        )
                    )
                        .fromJson(this) ?: emptyList<BookTest>()
                }
            emit(data)
        }.catch {
            //Logger.record(it)
            Timber.d("axy", it.message)
        }
}