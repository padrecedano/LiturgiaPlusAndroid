package org.deiverbum.app.model

/**
 *
 *
 * HomeItem es la clase para manejar las opciones
 * que aparecen en la pantalla inicial de la aplicación
 * desde un elemento `RecyclerView`.
 *
 *
 * Esta clase trabaja con: {@link org.deiverbum.app.presentation.home.adapter.HomeAdapter}
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class HomeItemOld(
    var name: String?,
    var itemId: Int,
    var thumbnail: Int,
    var color: Int,
    var navId: Int,
    imageColor: Int
) {
    var imageColor = imageColor
        private set

}