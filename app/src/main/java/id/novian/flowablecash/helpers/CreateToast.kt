package id.novian.flowablecash.helpers

import android.content.Context
import android.widget.Toast

interface CreateToast {
    fun createToast(message: String, duration: Int)
}

class CreateToastImpl(
    private val context: Context
) : CreateToast {
    override fun createToast(message: String, duration: Int) {
        val durationOfToast = when (duration) {
            0 -> Toast.LENGTH_SHORT
            1 -> Toast.LENGTH_LONG
            else -> 2
        }

        Toast.makeText(context, message, durationOfToast).show()
    }
}