package org.deiverbum.app.model

/**
 *
 *
 * HomeItem es la clase para manejar las opciones
 * que aparecen en la pantalla inicial de la aplicación
 * desde un elemento `RecyclerView`.
 *
 *
 * Esta clase trabaja con:
 *
 *  * [HomeFragment]
 *  * `[org.deiverbum.app.data.adapters.HomeAdapter]`
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class HomeItem {
    var name: String? = null
    var itemId = 0
    var thumbnail = 0
    var color = 0
    var navId = 0
    var imageColor = 0
        private set

    constructor() {}
    constructor(
        name: String?,
        itemId: Int,
        thumbnail: Int,
        color: Int,
        navId: Int,
        imageColor: Int
    ) {
        this.name = name
        this.itemId = itemId
        this.thumbnail = thumbnail
        this.imageColor = imageColor
        this.color = color
        this.navId = navId
    }
}