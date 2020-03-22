package com.example.simpleforecast.UI.Cities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.Util.ResponseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiesViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _responseState = MutableLiveData<Pair<ResponseState, String>>()


    val responseState: LiveData<Pair<ResponseState, String>>
        get() {
            return _responseState
        }

    private val _weatherResponse = MutableLiveData<List<City>>()

    val weather: LiveData<List<City>>
        get() = _weatherResponse




    companion object {
        const val TAG = "CitiesViewModel"

    }


    fun getCities(city:String){
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val disposable = repository.getCites(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseState.postValue(Pair(ResponseState.SUCCESS,
                    it.isEmpty().toString()))
                _weatherResponse.postValue(it)
            }, {
                _responseState.postValue(Pair(ResponseState.ERROR,
                    it.message?:it.toString()))
                Log.d(TAG, it.message?:it.toString())
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}