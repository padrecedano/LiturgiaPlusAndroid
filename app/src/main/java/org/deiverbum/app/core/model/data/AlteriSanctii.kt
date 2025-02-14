package org.deiverbum.app.core.model.data

data class AlteriSanctii(
    val sanctus: Sanctus,
    override var typus: String = "sanctii"
) : Alteri(typus) {


    override fun forRead(): StringBuilder {
        return StringBuilder(sanctus.forRead)
    }

    override fun sort() {
    }
}