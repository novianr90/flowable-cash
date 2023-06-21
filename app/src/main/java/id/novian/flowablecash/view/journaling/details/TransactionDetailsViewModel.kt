package id.novian.flowablecash.view.journaling.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.BaseViewModel
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val toast: CreateToast,
    private val repo: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler
) : BaseViewModel() {

    private var _onSuccess: MutableLiveData<String> = MutableLiveData()
    val onSuccess: LiveData<String> get() = _onSuccess

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }

    fun buttonSavedClicked(
        name: String,
        date: String,
        total: Int,
        type: String,
        description: String,
        feeType: String,
        fee: Int
    ) {
        val disposable = repo.createTransaction(
            name = name,
            date = date,
            total = total,
            type = type,
            description = description,
            feeType = feeType,
            fee = fee
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({
                _onSuccess.postValue("Success!")
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _onSuccess.postValue("Error occurred!")
            })

        compositeDisposable.add(disposable)
    }
}