package org.deiverbum.app.util

import android.annotation.SuppressLint
import android.net.ParseException
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.deiverbum.app.core.model.data.WeekDays
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtil {

    fun setTime(dateString: String, time: String): Long {
        return getTimeMilis(dateString, time)
    }

    fun Long.getTimeFormated(): String =
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(this))

    fun getDateFormatted(date: Date): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

    @SuppressLint("SimpleDateFormat")
    fun getTimestamp(dateString: String, plusDay: Int): Long {
        val c = Calendar.getInstance()
        c.time = SimpleDateFormat("yyyy-MM-dd").parse(dateString) ?: Date(0)
        c.add(Calendar.DATE, plusDay)
        return c.time.time
    }


    @SuppressLint("SimpleDateFormat")
    fun getTimestamp(dateString: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString) ?: Date(0)
        return date.time
    }

    fun getDayOfWeek(dateInt: Int): Int {
        val dayOfWeek = LocalDate.parse(
            dateInt.toString(),
            DateTimeFormatter.BASIC_ISO_DATE
        ).dayOfWeek
        //val dayOfWeek = localDate.getDayOfWeek()
        return WeekDays.valueOf(dayOfWeek.name).value
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTimeMilis(dayTimestamp: String, time: String): Long {
        val dateTime = dayTimestamp.plus(" ").plus(time)
        return SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateTime)?.time ?: 0
    }

    fun Int.isDateValid(): Boolean {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(this.toString())
            true
        } catch (e: ParseException) {
            false
        }
    }

    fun dayName(givenDate: Int): String {
        try {
            val localDate: LocalDate = LocalDate.parse(
                givenDate.toString(),
                DateTimeFormatter.ofPattern("yyyyMMdd")
            )
            val locale = Locale("es", "ES")
            return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, locale)
        } catch (e: ParseException) {
            return ""
        }
    }

    fun year(givenDate: Int): Int {
        try {
            val localDate: LocalDate = LocalDate.parse(
                givenDate.toString(),
                DateTimeFormatter.ofPattern("yyyyMMdd")
            )
            return localDate.year
        } catch (e: ParseException) {
            return 0
        }
    }

    /**
     * Obtiene la fecha de Pascua de un año dado.
     * Se usa el algoritmo de [Butcher-Meeus](https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques)
     *
     * @param theYear El año cuyo calendario se quiere generar
     * @return Un objeto `LocalDate` [LD][LocalDate] con la fecha de Pascua en formato yyyymmdd
     */
    fun getDiePaschae(theYear: Int): LocalDate {
        val a = theYear % 19
        val b = theYear / 100
        val c = theYear % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451
        val theMonth = (h + l - 7 * m + 114) / 31
        val p = (h + l - 7 * m + 114) % 31
        val theDay = p + 1
        return LocalDate.of(theYear, theMonth, theDay)
        //return String.format("%04d%02d%02d", theYear, easterMonth, easterDay);
    }

    fun isPasqua(givenDate: Int): Boolean {
        val dateTime = LocalDate.parse(givenDate.toString(), DateTimeFormatter.BASIC_ISO_DATE)
        return dateTime == getDiePaschae(dateTime.year)
    }

    fun getCurrentDate(): kotlinx.datetime.LocalDate {
        val isP = isPasqua(20250420)
        val now = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()
        val customFormat = LocalDateTime.Format {
            date(kotlinx.datetime.LocalDate.Formats.ISO_BASIC)
        }
        val dateTime = kotlinx.datetime.LocalDate(2024, 2, 15)
        //check(dateTime.format(customFormat) == "2024-02-15")
//val isPasqua= getDiePaschae(2025) == now.toLocalDateTime(tz).date
//        check(dateTime.format(kotlinx.datetime.LocalDate.Formats.ISO_BASIC) == "2024-02-15")

        return now.toLocalDateTime(tz).date
    }

    fun getToday(): Instant {
        val isP = isPasqua(20250420)
        val now = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()
        val customFormat = LocalDateTime.Format {
            date(kotlinx.datetime.LocalDate.Formats.ISO_BASIC)
        }
        val dateTime = kotlinx.datetime.LocalDate(2024, 2, 15)
        //check(dateTime.format(customFormat) == "2024-02-15")
//val isPasqua= getDiePaschae(2025) == now.toLocalDateTime(tz).date
//        check(dateTime.format(kotlinx.datetime.LocalDate.Formats.ISO_BASIC) == "2024-02-15")

        return now
    }


    fun getFormattedDate(date: LocalDate, pattern: String = "yyyyMMdd"): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }

    fun getTodayDate(): Int {
        val currentTimeZone = TimeZone.currentSystemDefault()
        val zi = ZoneId.of(currentTimeZone.id)
        val time = ZonedDateTime.now(zi)
        val newDate = time.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()
        return newDate

    }

}