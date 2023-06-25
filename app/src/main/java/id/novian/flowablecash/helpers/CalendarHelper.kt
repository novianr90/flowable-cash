package id.novian.flowablecash.helpers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface CalendarHelper {
    fun getCurrentDate(): String
    fun getYesterdayDate(): String
    fun getLast7DaysRange(): String
    fun getLast30DaysRange(): String
    fun getCurrentMonthRange(): String
    fun getLastMonthRange(): String
}

class CalendarHelperImpl(
    private var calendar: Calendar
) : CalendarHelper {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun getCurrentDate(): String {
        val today = calendar.time
        val response = dateFormat.format(today)
        resetToCurrentDate()
        return response
    }

    override fun getYesterdayDate(): String {
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = calendar.time
        val response = dateFormat.format(yesterday)
        resetToCurrentDate()
        return response
    }

    override fun getLast7DaysRange(): String {
        val endDate = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startDate = calendar.time
        val response = "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        resetToCurrentDate()
        return response
    }

    override fun getLast30DaysRange(): String {
        val endDate = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val startDate = calendar.time
        val response = "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        resetToCurrentDate()
        return response
    }

    override fun getCurrentMonthRange(): String {
        val currentCalendar = calendar.clone() as Calendar
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val startDate = currentCalendar.clone() as Calendar

        val endDate = calendar.clone() as Calendar

        val response = "${dateFormat.format(startDate.time)} - ${dateFormat.format(endDate.time)}"

        resetToCurrentDate()

        return response
    }

    override fun getLastMonthRange(): String {
        val currentCalendar = calendar.clone() as Calendar

        currentCalendar.add(Calendar.MONTH, -1)
        val startDate = currentCalendar.clone() as Calendar
        startDate.set(Calendar.DAY_OF_MONTH, 1)

        val endDate = currentCalendar.clone() as Calendar
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH))

        val response = "${dateFormat.format(startDate.time)} - ${dateFormat.format(endDate.time)}"

        resetToCurrentDate()
        return response
    }

    private fun resetToCurrentDate() {
        calendar.clear()
        calendar.timeInMillis = System.currentTimeMillis()
    }
}