package id.novian.flowablecash.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    open val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected val errorMessage: MutableLiveData<String> = MutableLiveData()
    open val errMessage: LiveData<String> get() = errorMessage
}