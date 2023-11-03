package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import java.util.*

class Conclusion {
    private var antiphonaMariam: String? = null

    //@SerializedName("bendicion")
    //@Expose
    private var bendicion: String =
        "El Señor todopoderoso nos conceda una noche tranquila y una santa muerte. Amén."

    private fun getHeader(): SpannableStringBuilder {
        return Utils.formatTitle(Constants.TITLE_CONCLUSION)
    }

    private fun getHeaderForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_CONCLUSION)
    }

    private fun getBendicion(): String {
        return bendicion
    }

    @Suppress("unused")
    fun setBendicion(bendicion: String) {
        this.bendicion = bendicion
    }

    private fun getBendicionForRead(): String {
        return "El Señor todopoderoso nos conceda una noche tranquila y una santa muerte. Amén."
    }

    fun setAntifonaVirgen(antVirgen: String?) {
        this.antiphonaMariam = antVirgen
    }

    private fun getAntifonaVirgen(): String? {
        return antiphonaMariam
    }

    fun getAll(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeader())
        sb.append(Utils.LS2)
        sb.append(getBendicion())
        sb.append(Utils.LS2)
        sb.append(Utils.formatTitle(Constants.TITLE_VIRGIN_ANTIHPON))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(getAntifonaVirgen()!!))
        return sb
    }

    fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderForRead())
        sb.append(getBendicionForRead())
        sb.append("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN.")
        sb.append(Utils.fromHtml(getAntifonaVirgen()!!))
        return sb
    }
}