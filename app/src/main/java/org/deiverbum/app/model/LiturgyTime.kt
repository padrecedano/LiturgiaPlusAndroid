package org.deiverbum.app.model

/**
 *
 * Clase para expresar los tiempos litúrgicos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LiturgyTime {
    var timeID: Int = 0
    var timeName: String? = null
    var liturgyName: String? = null
    val allForRead: String?
        get() = liturgyName
}