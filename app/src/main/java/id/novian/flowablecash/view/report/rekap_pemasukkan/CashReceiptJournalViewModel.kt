package id.novian.flowablecash.view.report.rekap_pemasukkan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.repository.CashReceiptJournalRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import id.novian.flowablecash.usecase.posting.PostingUseCase
import io.reactivex.rxjava3.core.Scheduler
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CashReceiptJournalViewModel @Inject constructor(
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val repo: CashReceiptJournalRepository,
    private val toast: CreateToast,
    private val gson: Gson,
    private val postingUseCase: PostingUseCase,
    private val calendar: Calendar
) : BaseViewModel() {
    private val _dataCashReceiptJournal: MutableLiveData<List<CashReceiptJournal>> =
        MutableLiveData()
    val dataCashReceiptJournal: LiveData<List<CashReceiptJournal>> get() = _dataCashReceiptJournal

    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result> get() = _result

    private val observableCashReceiptJournal = repo.getJournal()
        .share()

    fun getCashReceiptJournal() {
        val disposable = observableCashReceiptJournal
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe { _result.postValue(Result.LOADING) }
            .subscribe({
                _result.postValue(Result.SUCCESS)
                _dataCashReceiptJournal.postValue(it)
            }, {
                it.printStackTrace()
                _result.postValue(Result.FAILED)
                errorMessage.postValue(it.message)
            })

        compositeDisposable.addAll(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}