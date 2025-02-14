package org.deiverbum.app.core.model.data

data class AlteriRosarium(
    val rosarium: Rosarium,
    override var typus: String = "rosarium"
) : Alteri(typus) {

    override fun forRead(): StringBuilder {
        return StringBuilder(rosarium.getForView())
    }

    override fun sort() {
    }

}