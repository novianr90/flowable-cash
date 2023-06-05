package id.novian.flowablecash.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.helpers.CreateToast
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val toast: CreateToast
) : ViewModel() {

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}