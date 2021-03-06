package com.example.covid19.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19.data.Repository
import com.example.covid19.data.model.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel internal constructor(private val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()

    internal val homeDataResponse: LiveData<ApiResponse>
        get() = responseLiveData

    internal fun hitHomeDataApi() {
        disposable.add(repository.executeHomeDataApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> responseLiveData.setValue(ApiResponse.success(result)) },
                { error -> responseLiveData.setValue(ApiResponse.error(error)) }
            ))
    }

    override fun onCleared() {
        disposable.clear()
    }
}