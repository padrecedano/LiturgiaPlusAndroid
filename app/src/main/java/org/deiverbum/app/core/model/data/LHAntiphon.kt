package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Utils

/**
 * Maneja las antífonas de la salmodia.
 *
 * @property antiphonID El ID de la antífona en la base de datos.
 * @property antiphon El texto de la antífona.
 * @property haveSymbol Determina si la antífona tiene el símbolo "†" en su contenido.
 * @property antiphon El texto de la antífona.
 */
data class LHAntiphon(
    var antiphonID: Int = 0,
    var antiphon: String = "",
    @Ignore
    var theOrder: Int = 0
) {
    @Ignore
    var haveSymbol: Boolean = false

    init {

        if (antiphon.contains("†")) {
            haveSymbol = true
            antiphon = antiphon.replace("†", "")
        }
    }


    /**
     * El texto de la antífona.
     * El setter verifica si la misma contiene el símbolo †
     * en cuyo caso el valor de [haveSymbol] pasará a ser `true`
     * y la antífona se limpiará para los usos posteriores,
     * tales como el lector de voz, o la presentación de la misma al final del salmo,
     * mostrándose ese símbolo solamente cuando se invoque al método [getBeforeForView]
     * con un parámetro igual a `true` (valor por defecto).
     *
     * @see [LHPsalmody.getAllForView]
     */
    /*
        var antiphon = antiphon
        // antiphon = ""
            set(value) {
                field = if (value.contains("†")) {
                    haveSymbol = true
                    value.replace("†", "")
                } else {
                    value
                }
            }
    */
    /**
     * Prepara para la vista la antífona antes del salmo.
     * En la salmodia, cuando corresponden varias antífonas, las mismas se presentan antes del
     * salmo precedidas de la palabra "Ant. " más el número de la antífona, que se determina
     * mediante la propiedad [theOrder].
     *
     * También aquí se determina mediante el valor de [haveSymbol] si el texto de la antífona contiene el símbolo †
     * para colocarlo al final de la misma formateado con el color de rúbrica.
     *
     * @param withOrder Para determinar si se requiere el orden de la antífona.
     * @return Un [SpannableStringBuilder] con la antífona preparada.
     * @since 2024.1
     */

    fun getBeforeForView(withOrder: Boolean = true): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        if (withOrder) {
            ssb.append(Utils.toRed("Ant. %d. ".format(theOrder)))
        } else {
            ssb.append(Utils.toRed("Ant. "))
        }
        if (haveSymbol) {
            ssb.append(antiphon).append(Utils.toRed(" † "))
        } else {
            ssb.append(antiphon)
        }
        return ssb
    }

    /**
     * Prepara para la vista la antífona después del salmo.
     * En la salmodia, las antífonas que van después del salmo se presentan precedidas de la palabra "Ant. "
     * y no llevan el número de orden.
     *
     * @return Un [SpannableStringBuilder] con la antífona preparada.
     * @since 2024.1
     */
    val afterForView: SpannableStringBuilder
        get() = SpannableStringBuilder(Utils.toRed("Ant. ")).append(antiphon)

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario.
     * No se traslada este método a la fuente local (entidades de la base de datos), porque debe resolverse en el modelo,
     * ya sea que los datos vengan de una fuente local, remota u otra.
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        this.antiphon = if (antiphon.isEmpty()) "" else Utils.replaceByTime(antiphon, calendarTime)
    }
}