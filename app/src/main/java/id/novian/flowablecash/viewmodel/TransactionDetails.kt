package id.novian.flowablecash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.data.remote.repository.TransactionRemoteRepository
import id.novian.flowablecash.helpers.CreateToast
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TransactionDetails @Inject constructor(
    private val toast: CreateToast,
    private val remoteRepo: TransactionRemoteRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var _onSuccess: MutableLiveData<String> = MutableLiveData()
    val onSuccess: LiveData<String> get() = _onSuccess

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun buttonSavedClicked(name: String, date: String, desc: String, total: Int, type: String) {
        val disposable = remoteRepo.postTransaction(
            name = name,
            date = date,
            description = desc,
            total = total,
            type = type
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onSuccess.value = "Start to upload"
            }
            .doOnComplete {
                _onSuccess.value = "Success"
            }
            .doOnError {
                _onSuccess.value = "Error sent to server"
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}