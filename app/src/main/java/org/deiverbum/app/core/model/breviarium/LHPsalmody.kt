package org.deiverbum.app.core.model.data.breviarium

import androidx.room.Ignore
import org.deiverbum.app.core.model.data.Sortable

/**
 * Esta clase sirve para manejar la salmodia del Breviario.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 *
 * @see [Sortable]
 */
open class LHPsalmody(@Ignore open var antiphonae: MutableList<LHAntiphon>, var typus: Int) :
    Sortable {

    /**
     * Constructor secundario
     * @param psalmus Lista con los salmos
     * @param antiphonae Lista con las antífonas
     * @param typus El tipo de salmodia
     */
    constructor(
        psalmus: MutableList<LHPsalm>,
        antiphonae: MutableList<LHAntiphon>,
        typus: Int
    ) : this(
        antiphonae, typus
    ) {
        this.psalmus = psalmus
    }

    @Ignore
    open var psalmus: MutableList<LHPsalm> = mutableListOf()


    /**
     * Ordena los salmos y las antífonas de la salmodia.
     *
     * @since 2025.1
     */
    override fun sort() {
        psalmus = psalmus.sortedBy { it.theOrder } as MutableList<LHPsalm>
        antiphonae = antiphonae.sortedBy { it.theOrder } as MutableList<LHAntiphon>
    }

    fun normalizeByTime(calendarTime: Int) {
        antiphonae.forEach {
            it.normalizeByTime(calendarTime)
        }
    }
}