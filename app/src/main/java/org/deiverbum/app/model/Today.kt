package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Esta clase recoge información valiosa sobre el día litúrgico.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

class Today {
    var todayDate: Int = 0

    var weekDay: Int? = null
        get() = if(field==null) 0 else field

    var timeID: Int? = null

    @Ignore
    var weekDayFK: Int? = null
    var liturgyFK: Int? = null
    var previousFK: Int? = null
    var massReadingFK: Int? = null
    var invitatoryFK: Int? = null

    //@Ignore
    var hasSaint: Int? = null
    @JvmField
    var saintFK: Int? = null
    var oHymnFK: Int? = null
    var oPsalmodyFK: Int? = null
    var oVerseFK: Int? = null
    @JvmField
    var oBiblicalFK: Int? = null
    var oPatristicFK: Int? = null
    var oPrayerFK: Int? = null
    var oTeDeum: Int? = null
    var lHymnFK: Int? = null
    var lPsalmodyFK: Int? = null
    var lBiblicalFK: Int? = null
    var lBenedictusFK: Int? = null
    var lIntercessionsFK: Int? = null
    var lPrayerFK: Int? = null
    var tHymnFK: Int? = null
    var tPsalmodyFK: Int? = null
    var tBiblicalFK: Int? = null
    var tPrayerFK: Int? = null
    var sHymnFK: Int? = null
    var sPsalmodyFK: Int? = null
    var sBiblicalFK: Int? = null
    var sPrayerFK: Int? = null
    var nHymnFK: Int? = null
    var nPsalmodyFK: Int? = null
    var nBiblicalFK: Int? = null
    var nPrayerFK: Int? = null
    var vHymnFK: Int? = null
    var vPsalmodyFK: Int? = null
    var vBiblicalFK: Int? = null
    var vMagnificatFK: Int? = null
    var vIntercessionsFK: Int? = null
    var vPrayerFK: Int? = null
    var nightPrayerFK: Int? = null

    @JvmField
    @Ignore
    var liturgyDay: Liturgy? = null

    @JvmField
    @Ignore
    var liturgyPrevious: Liturgy? = null

    @Ignore
    var liturgyTime: LiturgyTime? = null
    fun setMLecturasFK(mLecturasFK: Int?) {
        massReadingFK = mLecturasFK
    }

    private val tituloVisperas: String
        get() = if (liturgyPrevious != null) {
            liturgyPrevious!!.name.replace(" I Vísperas.| I Vísperas".toRegex(), "")
        } else {
            liturgyDay!!.name
        }
    val titulo: String
        get() = if (liturgyDay!!.typeID == 6) tituloVisperas else liturgyDay!!.name
    private val tituloForRead: String
        get() = if (liturgyDay!!.typeID == 6) tituloVisperas else liturgyDay!!.titleForRead
    val fecha: String
        get() = Utils.getLongDate(todayDate.toString())
    val tiempo: String?
        get() = if (liturgyDay!!.typeID == 6 && liturgyPrevious != null) liturgyPrevious!!.liturgyTime!!.liturgyName else liturgyDay!!.liturgyTime!!.liturgyName


    private fun hasSaintToday(): Boolean {
        return hasSaint != null && hasSaint == 1
    }

     fun getAllForView(todayRequest:TodayRequest): SpannableStringBuilder {
        liturgyDay!!.setHasSaint(hasSaintToday())
        ColorUtils.isNightMode = todayRequest.isNightMode
        val sb = SpannableStringBuilder()
        try {
            sb.append(fecha)
            sb.append(Utils.LS2)
            sb.append(Utils.toH2(tiempo))
            sb.append(Utils.LS2)
            sb.append(Utils.toH3(titulo))

            if (liturgyDay!!.typeID == 9) {
                sb.append(liturgyDay?.homilyList?.getAllForView(todayRequest)
                )
                return sb
            }

            if (liturgyDay!!.typeID == 10) {
                sb.append(liturgyDay!!.massReadingList?.getForView(
                    todayRequest
                )
                )
            }

            val bh: BreviaryHour? =liturgyDay!!.breviaryHour

            if (liturgyDay!!.typeID == 0) {
                if (oBiblicalFK == 600010101) {
                    sb.append(bh?.getOficioEaster()?.forView)
                } else {
                    sb.append(
                        bh?.getMixtoForView(
                            liturgyDay!!.liturgyTime!!,
                            hasSaintToday()
                        )
                    )
                }
            }
            if (liturgyDay!!.typeID == 1) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forView)
                } else {
                    sb.append(
                        liturgyDay!!.breviaryHour!!.getOficio(todayRequest.isMultipleInvitatory)!!
                            .getForView(liturgyDay!!.liturgyTime, hasSaintToday())
                    )
                }
            }
            if (liturgyDay!!.typeID == 2) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getLaudes(todayRequest.isMultipleInvitatory)!!
                        .getForView(liturgyDay!!.liturgyTime!!, hasSaintToday())
                )
            }
            if (liturgyDay!!.typeID == 3 || liturgyDay!!.typeID == 4 || liturgyDay!!.typeID == 5) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getIntermedia()!!
                        .getForView(liturgyDay!!.liturgyTime!!, liturgyDay!!.typeID)
                )
            }
            if (liturgyDay!!.typeID == 6) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getVisperas()!!
                        .getForView(liturgyDay!!.liturgyTime)
                )
            }
            if (liturgyDay!!.typeID == 7) {
                sb.append(liturgyDay!!.breviaryHour!!.getCompletas()!!.getAllForView())
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val singleForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            try {
                sb.append(Utils.LS)
                sb.append(fecha)
                sb.append(Utils.LS2)
                sb.append(Utils.toH2(tiempo))
                sb.append(Utils.LS2)
                sb.append(Utils.toH3(titulo))
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    fun getAllForRead(hasInvitatory: Boolean): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(Utils.pointAtEnd(fecha))
            sb.append(tituloForRead)

            if (liturgyDay!!.typeID == 9) {
                sb.append(liturgyDay?.homilyList?.allForRead
                )
                return sb
            }
            if (liturgyDay!!.typeID == 0) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forRead)
                } else {
                    //TODO
                    sb.append(liturgyDay!!.breviaryHour!!.getMixtoForRead())
                }
            }
            if (liturgyDay!!.typeID == 1) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forRead)
                } else {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficio(hasInvitatory)?.forRead)
                }
            }
            if (liturgyDay!!.typeID == 2) {
                sb.append(liturgyDay!!.breviaryHour!!.getLaudes(hasInvitatory)!!.forRead)
            }
            if (liturgyDay!!.typeID == 3 || liturgyDay!!.typeID == 4 || liturgyDay!!.typeID == 5) {
                sb.append(liturgyDay!!.breviaryHour!!.getIntermedia()!!.forRead)
            }
            if (liturgyDay!!.typeID == 6) {
                sb.append(liturgyDay!!.breviaryHour!!.getVisperas()!!.getAllForRead())
            }
            if (liturgyDay!!.typeID == 7) {
                sb.append(liturgyDay!!.breviaryHour!!.getCompletas()!!.getForRead())
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    fun getSingleForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(Utils.pointAtEnd(fecha))
            sb.append(Utils.pointAtEnd(tiempo))

            sb.append(tituloForRead)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }
}