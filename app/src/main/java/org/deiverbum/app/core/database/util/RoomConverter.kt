package org.deiverbum.app.core.database.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import org.deiverbum.app.core.model.data.Universalis
import javax.inject.Inject

@ProvidedTypeConverter
class RoomConverter @Inject constructor(
    private val moshi: Moshi
) {
    @TypeConverter
    fun fromUniversalis(data: Universalis): String {
        return moshi.adapter(Universalis::class.java).toJson(data)
    }

    @TypeConverter
    fun toUniversalis(json: String): Universalis? {
        return moshi.adapter(Universalis::class.java).fromJson(json)
    }
}

