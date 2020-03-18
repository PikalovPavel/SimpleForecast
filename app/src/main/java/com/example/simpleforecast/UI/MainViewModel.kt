package com.example.simpleforecast.UI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleforecast.Data.Local.WeatherItem
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.Util.ResponseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(val repository: WeatherRepository) : ViewModel() {
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()
    private val _responseState = MutableLiveData<Pair<ResponseState, String>>()

    val responseState: LiveData<Pair<ResponseState, String>>
        get() = _responseState

    private val _weatherResponse = MutableLiveData<WeatherItem>()

    val weather: LiveData<WeatherItem>
        get() = _weatherResponse

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather(){
        val cities = listOf("294021", "295212", "294922","291605", "349727")
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val disposable = repository.getWeather(cities)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherResponse.postValue(it)
            }, {
                _weatherResponse.postValue(null)
                _responseState.postValue(Pair(ResponseState.ERROR,
                    it.message?:it.toString()))
                Log.d("test", it.message)
            }, {
                _responseState.postValue(Pair(ResponseState.SUCCESS, ""))
            })
        disposables.add(disposable)
    }
}