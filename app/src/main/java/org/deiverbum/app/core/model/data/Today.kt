package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Esta clase recoge toda la información sobre el día litúrgico de una fecha dada.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

class Today(
    var todayDate: Int = 0,
    /*
        @Deprecated("Esta columna se eliminará de la base de datos en versiones futuras. Se sustituye por [nightPrayerFK]",
            ReplaceWith("nightPrayerFK"))
        var weekDay: Int = 0,
        //get() = if(field==null) 0 else field
    */
    var timeFK: Int = 0,

    @Ignore
    var weekDayFK: Int = 0,
    var liturgyFK: Int = 0,
    var previousFK: Int = 0,
    var massReadingFK: Int = 0,
    var invitatoryFK: Int = 0,

    //@Ignore
    var hasSaint: Int = 0,
    var saintFK: Int = 0,
    var oHymnFK: Int = 0,
    var oPsalmodyFK: Int = 0,
    var oVerseFK: Int = 0,
    var oBiblicalFK: Int = 0,
    var oPatristicFK: Int = 0,
    var oPrayerFK: Int = 0,
    var oTeDeum: Int = 0,
    var lHymnFK: Int = 0,
    var lPsalmodyFK: Int = 0,
    var lBiblicalFK: Int = 0,
    var lBenedictusFK: Int = 0,
    var lIntercessionsFK: Int = 0,
    var lPrayerFK: Int = 0,
    var tHymnFK: Int = 0,
    var tPsalmodyFK: Int = 0,
    var tBiblicalFK: Int = 0,
    var tPrayerFK: Int = 0,
    var sHymnFK: Int = 0,
    var sPsalmodyFK: Int = 0,
    var sBiblicalFK: Int = 0,
    var sPrayerFK: Int = 0,
    var nHymnFK: Int = 0,
    var nPsalmodyFK: Int = 0,
    var nBiblicalFK: Int = 0,
    var nPrayerFK: Int = 0,
    var vHymnFK: Int = 0,
    var vPsalmodyFK: Int = 0,
    var vBiblicalFK: Int = 0,
    var vMagnificatFK: Int = 0,
    var vIntercessionsFK: Int = 0,
    var vPrayerFK: Int = 0,
    var nightPrayerFK: Int = 71,

    @Ignore
    var liturgyDay: Liturgy = Liturgy(""),

    @Ignore
    var liturgyPrevious: Liturgy? = null,

    @Ignore
    var liturgyTime: LiturgyTime? = null
) {

    fun setMLecturasFK(mLecturasFK: Int) {
        massReadingFK = mLecturasFK
    }

    private val tituloVisperas: String
        get() = if (liturgyPrevious != null) {
            liturgyPrevious!!.nomen.replace(" I Vísperas.| I Vísperas".toRegex(), "")
        } else {
            liturgyDay.nomen
        }
    val titulo: String
        get() = if (liturgyDay.typeID == 6) tituloVisperas else liturgyDay.nomen
    private val tituloForRead: String
        get() = if (liturgyDay.typeID == 6) tituloVisperas else liturgyDay.titleForRead
    val fecha: String
        get() = Utils.formatDate(todayDate.toString(), "yyyyMMdd", "EEEE d 'de' MMMM 'de' yyyy")
    val tiempo: String?
        get() = if (liturgyDay.typeID == 6 && liturgyPrevious != null) liturgyPrevious!!.tempus!!.externus else liturgyDay.tempus!!.externus


    private fun hasSaintToday(): Boolean {
        return hasSaint == 1
    }

    fun getAllForView(todayRequest: TodayRequest): SpannableStringBuilder {
        liturgyDay.setHasSaint(hasSaintToday())
        ColorUtils.isNightMode = todayRequest.isNightMode
        val sb = SpannableStringBuilder()
        try {
/*
            if (liturgyDay.typeID == 12) {
                //sb.append(LS2)
                sb.append(liturgyDay.saintLife?.getForView(todayRequest.isNightMode))
                return sb
            }

            sb.append(fecha)
            sb.append(Utils.LS2)
            sb.append(Utils.toH2(tiempo))
            sb.append(Utils.LS2)
            sb.append(Utils.toH3(titulo))

            if (liturgyDay.typeID == 9) {
                sb.append(liturgyDay.homilyList?.getAllForView(todayRequest))
                return sb
            }

            if (liturgyDay.typeID == 10) {
                sb.append(liturgyDay.massReadingList?.getForView(todayRequest))
                return sb
            }

            if (liturgyDay.typeID == 11) {
                sb.append(LS2)
                sb.append(liturgyDay.bibleCommentList?.getAllForView(todayRequest))
                return sb
            }


            val bh: BreviaryHour? = liturgyDay.breviaryHour

            if (liturgyDay.typeID == 0) {
                if (oBiblicalFK == 600010101) {
                    sb.append(bh?.getOficioEaster()?.forView)
                } else {
                    sb.append(bh?.LHMixtus!!.forView(timeFK, false))
                }
            }
            if (liturgyDay.typeID == 1) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay.breviaryHour!!.getOficioEaster()?.forView)
                } else {
                    sb.append(
                        liturgyDay.breviaryHour!!.oficio!!.forView(timeFK, false)
                    )
                }
            }
            if (liturgyDay.typeID == 2) {
                sb.append(
                    //liturgyDay.breviaryHour!!.laudes!!.getMixtoForView(timeFK,false)
                )
            }

            if (liturgyDay.typeID == 3 || liturgyDay.typeID == 4 || liturgyDay.typeID == 5) {
                sb.append(
                    liturgyDay.breviaryHour!!.getIntermedia()!!
                        .getForView(timeFK, liturgyDay.typeID)
                )
            }
            if (liturgyDay.typeID == 6) {
                sb.append(liturgyDay.breviaryHour!!.visperas!!.getForView(timeFK))
            }
            if (liturgyDay.typeID == 7) {
                sb.append(liturgyDay.breviaryHour!!.completas.getAllForView(timeFK))
            }
*/

        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    fun getAllForRead(hasInvitatory: Boolean): StringBuilder {
        val sb = StringBuilder()
        //sbReader = new StringBuilder(VOICE_INI);
        try {
            /*
                        if (liturgyDay.typeID == 12) {
                            sb.append(liturgyDay.saintLife?.forRead)
                            return sb
                        }

                        sb.append(Utils.pointAtEnd(fecha))
                        sb.append(tituloForRead)

                        if (liturgyDay.typeID == 9) {
                            sb.append(liturgyDay.homilyList?.allForRead)
                            return sb
                        }



                        if (liturgyDay.typeID == 0) {
                            if (oBiblicalFK == 600010101) {
                                sb.append(liturgyDay.breviaryHour!!.getOficioEaster()?.forRead)
                            } else {
                                //TODO
                                sb.append(liturgyDay.breviaryHour!!.LHMixtus!!.forRead())
                            }
                        }
                        if (liturgyDay.typeID == 1) {
                            if (oBiblicalFK == 600010101) {
                                sb.append(liturgyDay.breviaryHour!!.getOficioEaster()?.forRead)
                            } else {
                                sb.append(liturgyDay.breviaryHour!!.oficio!!.forRead())
                            }
                        }
                        if (liturgyDay.typeID == 2) {
                            //sb.append(liturgyDay.breviaryHour!!.laudes!!.forRead)
                        }
                        if (liturgyDay.typeID == 3 || liturgyDay.typeID == 4 || liturgyDay.typeID == 5) {
                            sb.append(liturgyDay.breviaryHour!!.getIntermedia()!!.forRead)
                        }
                        if (liturgyDay.typeID == 6) {
                            sb.append(liturgyDay.breviaryHour!!.visperas!!.getAllForRead())
                        }
                        if (liturgyDay.typeID == 7) {
                            sb.append(liturgyDay.breviaryHour!!.completas.getForRead())
                        }
                        if (liturgyDay.typeID == 10) {
                            sb.append(liturgyDay.massReadingList!!.allForRead)
                        }

                        if (liturgyDay.typeID == 11) {
                            sb.append(LS2)
                            sb.append(liturgyDay.bibleCommentList?.getAllForRead)
                            return sb
               */
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

}