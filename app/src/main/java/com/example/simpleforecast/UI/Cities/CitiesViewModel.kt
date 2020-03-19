package com.example.simpleforecast.UI.Cities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Data.Local.Util.CityTemperature
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.Util.ResponseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiesViewModel(val repository: WeatherRepository) : ViewModel() {
    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()
    private val _responseState = MutableLiveData<Pair<ResponseState, String>>()

    val responseState: LiveData<Pair<ResponseState, String>>
        get() = _responseState

    private val _weatherResponse = MutableLiveData<List<City>>()

    val weather: LiveData<List<City>>
        get() = _weatherResponse



    fun getCities(city:String){
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val disposable = repository.getCites(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseState.postValue(Pair(ResponseState.SUCCESS,
                    it.size.toString()))
                _weatherResponse.postValue(it)
                Log.d("kek", it.toString())
            }, {
                _responseState.postValue(Pair(ResponseState.ERROR,
                    it.message?:it.toString()))
                Log.d("kek", it.message!!)
            })
        disposables.add(disposable)
    }
}