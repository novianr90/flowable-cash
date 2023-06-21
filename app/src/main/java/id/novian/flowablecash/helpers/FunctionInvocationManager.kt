package id.novian.flowablecash.helpers

import android.content.Context
import io.reactivex.rxjava3.core.Single
import java.util.Calendar

interface FunctionInvocationManager {
    fun invokeFunction()
    fun isFunctionInvoked(): Single<Boolean>
}

class FunctionInvocationManagerImpl(
    private val context: Context,
    private val calendar: Calendar
) : FunctionInvocationManager {

    private val sharedPref =
        context.getSharedPreferences("FunctionInvocation", Context.MODE_PRIVATE)

    override fun invokeFunction() {
        val lastInvocation = sharedPref.getLong("last_invoke", 0L)
        val currentDate = calendar.timeInMillis

        if (!isSameDay(lastInvocation, currentDate)) {
            sharedPref.edit()
                .putLong("last_invoke", currentDate)
                .apply()
        }
    }

    override fun isFunctionInvoked(): Single<Boolean> {
        val lastInvocation = sharedPref.getLong("last_invoke", 0L)
        val currentDate = calendar.timeInMillis

        return Single.just(isSameDay(lastInvocation, currentDate))
    }

    private fun isSameDay(date1: Long, date2: Long): Boolean {
        val cal1 = calendar.apply { timeInMillis = date1 }
        val cal2 = calendar.apply { timeInMillis = date2 }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}