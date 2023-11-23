package org.deiverbum.app.core.model.data

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

/**
 *
 * Clase para expresar los tiempos litúrgicos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@JsonClass(generateAdapter = true)
data class LiturgyTime(
    val timeID: Int = 0,
    @ColumnInfo(name = "timeName")
    val nomen: String? = null,
    @ColumnInfo(name = "liturgyName")
    val externus: String? = null
) {
    val allForRead: String?
        get() = externus
}