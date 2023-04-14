package org.deiverbum.app.data.source.local

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.model.Rosario
import org.deiverbum.app.utils.Constants
import javax.inject.Inject

class FileData {

        private var mContext: Context? = null

        @Inject
        fun FileDataSource(@ApplicationContext context: Context?) {
            mContext = context
        }
    suspend fun getRosario(day: Int): Rosario?
    {
        val s = getResourceAsText(Constants.FILE_ROSARY)
        val gson = Gson()
        val json = gson.toJson(s)
        val theModel = gson.fromJson(json, Rosario::class.java)
        theModel.day=day
        return theModel
    }


    fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()


    }