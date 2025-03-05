package org.deiverbum.app.util

import android.annotation.SuppressLint
import android.net.ParseException
import org.deiverbum.app.core.model.data.WeekDays
import java.text.SimpleDateFormat
import java.time.LocalDate
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
}