package org.deiverbum.app.util

import android.content.Context
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.data.wrappers.CustomException
import org.deiverbum.app.data.wrappers.DataWrapper
import org.deiverbum.app.model.OracionSimple
import javax.inject.Inject

class FileReaderUtil() {

    private var mContext: Context? = null

    @Inject
    fun FileDataSource(@ApplicationContext context: Context?) {
        mContext = context
    }


    fun readFile(): String {
        try {
            val filename = "angelus.json"
            if (filename != null) {
                mContext?.assets?.open(filename)?.use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val plantType = object : TypeToken<DataWrapper<OracionSimple, CustomException>>() {}.type
                    return "plantType.toString()"
                    }
                }
            } else {
                Log.e("TAG", "Error seeding database - no valid filename")
            return "ERR"
            //Result.failure()
            }
        } catch (ex: Exception) {
            Log.e("TAG", "Error seeding database", ex)
            return "ERR"

            //Result.failure()
        }
return "E"
    }
}