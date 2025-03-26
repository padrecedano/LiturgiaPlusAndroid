package org.deiverbum.app.core.model.alteri

data class AlteriSanctii(
    val sanctus: Sanctus,
    override var typus: String = "sanctii"
) : Alteri(typus) {

    override fun getTitle(): String {
        return sanctus.monthName
    }
}