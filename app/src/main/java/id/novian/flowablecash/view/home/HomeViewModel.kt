package id.novian.flowablecash.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onLoading: LiveData<Boolean> get() = _onLoading

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}