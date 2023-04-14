package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import java.util.*

class Conclusion {
    var antVirgen: List<String>? = null

    @SerializedName("bendicion")
    @Expose
    private var bendicion: String? = null
    fun getHeader(): SpannableStringBuilder {
        return Utils.formatTitle(Constants.TITLE_CONCLUSION)
    }

    fun getHeaderForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_CONCLUSION)
    }

    fun getBendicion(): String? {
        return bendicion
    }

    fun setBendicion(bendicion: String?) {
        this.bendicion = bendicion
    }

    fun getBendicionForRead(): String {
        return "El Señor todopoderoso nos conceda una noche tranquila y una santa muerte. Amén."
    }

    fun getAntifonaVirgen(timeID: Int): String {
        val mIndex = if (timeID != 6) Random().nextInt(3) else 4
        return antVirgen!![mIndex]
    }

    fun getAll(idTiempo: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeader())
        sb.append(Utils.LS2)
        sb.append(getBendicion())
        sb.append(Utils.LS2)
        sb.append(Utils.formatTitle(Constants.TITLE_VIRGIN_ANTIHPON))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(getAntifonaVirgen(idTiempo)))
        return sb
    }

    fun getAllForRead(idTiempo: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderForRead())
        sb.append(getBendicionForRead())
        sb.append("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN.")
        sb.append(Utils.fromHtml(getAntifonaVirgen(idTiempo)))
        return sb
    }
}