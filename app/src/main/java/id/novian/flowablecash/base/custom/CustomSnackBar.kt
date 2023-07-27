package id.novian.flowablecash.base.custom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import id.novian.flowablecash.R

interface CustomSnackBar {
    fun showSnackBar(message: String)
}

class CustomSnackBarImpl(
    private val rootView: View,
    private val anchorView: View? = null
) : CustomSnackBar {
    override fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view as SnackbarLayout

        val customSnackLayout = LayoutInflater.from(snackBarView.context)
            .inflate(R.layout.custom_snackbar, null, false)

        snackBarView.setBackgroundColor(Color.TRANSPARENT)
        customSnackLayout.setPadding(0, 0, 0, 0)

        customSnackLayout.findViewById<TextView>(R.id.messageTextView).text = message
        customSnackLayout.findViewById<ImageButton>(R.id.dismissButton).setOnClickListener {
            snackBar.dismiss()
        }

        snackBarView.addView(customSnackLayout, 0)

        if (anchorView != null) {
            snackBar.anchorView = anchorView
        }

        snackBar.show()
    }
}