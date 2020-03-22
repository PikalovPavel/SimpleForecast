package com.example.simpleforecast.UI.Cities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.CityWeather
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.Util.ResponseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class WeatherViewModel(
    private val repository: WeatherRepository,
    state: SavedStateHandle
) : ViewModel() {

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()
    private val _responseState = MutableLiveData<Pair<ResponseState, String>>()
    private val savedStateHandle = state

    val responseState: LiveData<Pair<ResponseState, String>>
        get() = _responseState

    private val _weatherResponse = MutableLiveData<CityWeather>()

    val weather: LiveData<CityWeather>
        get() = _weatherResponse

    companion object {
        const val CITY_KEY = "cityId"
        const val TAG = "WeatherViewModel"

    }

    fun saveCityId(cityId: String) {
        savedStateHandle.set(CITY_KEY, cityId)
    }

    fun getCityId(): String? {
        return savedStateHandle.get(CITY_KEY)
    }


    fun updateWeather(cityId: String?) {
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val localCityId = cityId ?: getCityId()
        if (localCityId != null) {
            val localCity = _weatherResponse.value?.city
                ?: City(localCityId, "", "", "", country = "")
            val disposable = repository.updateWeather(localCityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _weatherResponse.postValue(CityWeather(localCity, it))
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
                    Log.d(TAG, it.message ?: it.toString())
                })
            disposables.add(disposable)
        } else {
            _responseState.postValue(
                Pair(
                    ResponseState.ERROR,
                    "Вернитесь к выбору города и попробуйте позже"
                )
            )
        }
    }

    fun getCurrentWeather(cityId: String) {
        _responseState.postValue(Pair(ResponseState.LOADING, ""))
        val disposable = repository.getCityWeather(cityId)
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
                Log.d(TAG, it.message ?: it.toString())
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}