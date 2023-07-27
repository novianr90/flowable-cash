package id.novian.flowablecash.view.report.rekap_pengeluaran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.PurchasesJournal
import id.novian.flowablecash.domain.repository.PurchasesJournalRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PurchasesJournalViewModel @Inject constructor(
    private val repo: PurchasesJournalRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast
) : BaseViewModel() {
    private val _dataPurchaseJournal: MutableLiveData<List<PurchasesJournal>> = MutableLiveData()
    val dataPurchaseJournal: LiveData<List<PurchasesJournal>> get() = _dataPurchaseJournal

    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result> get() = _result

    private val observablePurchasesJournal = repo.getJournal()
        .share()

    fun getJournal() {
        val disposable = observablePurchasesJournal
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe { _result.postValue(Result.LOADING) }
            .subscribe({
                _dataPurchaseJournal.postValue(it)
                _result.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _result.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}