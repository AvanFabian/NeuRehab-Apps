package xyz.themanusia.pkm.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {
    companion object {
        fun dateToEpoch(date: String): Long {
            val dat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(date)!!
            return dat.time
        }

        fun getCurrentEpoch(): Long {
            return System.currentTimeMillis()
        }

        fun epochToDate(epoch: Long): String {
            return SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).format(epoch)
        }

        fun getDayByEpoch(epoch: Long): Int {
            return Calendar.getInstance().apply {
                timeInMillis = epoch
            }.get(Calendar.DAY_OF_WEEK)
        }

        fun getDay(day: Int): String {
            return when (day) {
                1 -> "Sunday"
                2 -> "Monday"
                3 -> "Tuesday"
                4 -> "Wednesday"
                5 -> "Thursday"
                6 -> "Friday"
                7 -> "Saturday"
                else -> "Unknown"
            }
        }

        fun getDay2(day: Int): String {
            return when (day) {
                1 -> "Sun"
                2 -> "Mon"
                3 -> "Tue"
                4 -> "Wed"
                5 -> "Thu"
                6 -> "Fri"
                7 -> "Sat"
                else -> "Unknown"
            }
        }

        fun getDateByEpoch(epoch: Long): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(epoch)
        }

        fun getDateByEpoch2(epoch: Long): String {
            return SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(epoch)
        }

        fun rng(min: Int, max: Int): Int {
            return (min..max).random()
        }
    }
}