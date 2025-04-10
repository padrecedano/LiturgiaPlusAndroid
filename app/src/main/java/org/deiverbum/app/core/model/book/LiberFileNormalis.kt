package org.deiverbum.app.core.model.book


/**
 * Clase que representa archivos normales en la capa de datos externa.
 *
 * @author A. Cedano
 * @see [LiberFile]
 */

data class LiberFileNormalis(
    override var typus: String = "normalis",
    override val bookType: Int,
    var content: String = "",
    //var chapters: List<Chapter>? = null

    //, override var tempore: LiturgyTime
) : LiberFile(typus, bookType)