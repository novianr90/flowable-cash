package id.novian.flowablecash.view.journaling.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.flowablecash.base.vm.BaseViewModel
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.repository.TransactionRepository
import id.novian.flowablecash.helpers.CreateToast
import id.novian.flowablecash.helpers.Result
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val repo: TransactionRepository,
    @Named("IO") private val schedulerIo: Scheduler,
    @Named("MAIN") private val schedulerMain: Scheduler,
    private val toast: CreateToast,
    private val mainRemote: MainRemoteRepository
) : BaseViewModel() {

    private val _onSuccess: MutableLiveData<Result> = MutableLiveData()
    val onSuccess: LiveData<Result> get() = _onSuccess

    private val _data: MutableLiveData<Any> = MutableLiveData()
    val data: LiveData<Any> = _data

    fun buttonUpdateClicked(
        id: Int,
        date: String,
        total: Int,
        type: String,
        description: String,
    ) {
        val disposable = repo.updateTransaction(
            id = id,
            date = date,
            total = total,
            type = type,
            description = description,
        )
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .doOnSubscribe {
                _onSuccess.postValue(Result.LOADING)
            }
            .subscribe({
                _onSuccess.postValue(Result.SUCCESS)
            }, {
                it.printStackTrace()
                errorMessage.postValue(it.message)
                _onSuccess.postValue(Result.FAILED)
            })

        compositeDisposable.add(disposable)
    }

    fun getTransactionById(id: Int, type: String) {
        val disposable: Disposable = when (type) {
            "Pemasukkan" -> mainRemote.getPemasukkanById(id)
            "Pengeluaran" -> mainRemote.getPengeluaranById(id)
            else -> throw IllegalArgumentException("Error: no kind of type")
        }
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMain)
            .subscribe({
                _data.postValue(it)
            }, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

    fun createToast(message: String) {
        toast.createToast(message, 0)
    }
}