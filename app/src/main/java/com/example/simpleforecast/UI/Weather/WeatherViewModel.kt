package com.example.simpleforecast.UI.Cities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.Util.ResponseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()
    private val _responseState = MutableLiveData<Pair<ResponseState, String>>()

    val responseState: LiveData<Pair<ResponseState, String>>
        get() = _responseState

    private val _weatherResponse = MutableLiveData<Weather>()

    val weather: LiveData<Weather>
        get() = _weatherResponse

    fun getCurrentWeather(cityId:String) {
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val disposable = repository.getWeather(cityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherResponse.postValue(it)
                _responseState.postValue(
                    Pair(
                        ResponseState.SUCCESS,
                        ""
                    )
                )
            }, {
                _weatherResponse.postValue(null)
                _responseState.postValue(
                    Pair(
                        ResponseState.ERROR,
                        it.message ?: it.toString()
                    )
                )
                Log.d("test", it.message)
            })
        disposables.add(disposable)
    }
}