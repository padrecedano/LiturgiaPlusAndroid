package org.deiverbum.app.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.deiverbum.app.data.wrappers.CustomException
import org.deiverbum.app.data.wrappers.DataWrapper
import org.deiverbum.app.model.Rosario
import org.deiverbum.app.model.Today
import org.deiverbum.app.utils.Constants
import javax.inject.Inject

class AssetProvider @Inject constructor(
    private val context: Context
) {

    fun getDescription() = context.assets.open(Constants.FILE_ROSARY)

     fun getRosary(day: Int): DataWrapper<Rosario, CustomException>? {
        try {

            context.assets.open(Constants.FILE_ROSARY).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        //val plantType = object : TypeToken<List<Plant>>() {}.type
                        val data: Rosario = Gson().fromJson(jsonReader, Rosario::class.java)
                        //plantList.avemaria?.let { Log.d("DDD", it) }
                        data.day = day
                        return DataWrapper<Rosario, CustomException>(data);


                    }
                }

        } catch (ex: Exception) {
            Log.e("TAG", "Error seeding database", ex)
            return DataWrapper<Rosario, CustomException>(
                CustomException(
                    ex.localizedMessage
                )
            );
            //Result.failure()
        }
    }


}
