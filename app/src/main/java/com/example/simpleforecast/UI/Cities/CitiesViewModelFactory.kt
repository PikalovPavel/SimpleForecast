package com.example.simpleforecast.UI.Cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleforecast.Data.Repositories.WeatherRepository

@Suppress("UNCHECKED_CAST")
class CitiesViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitiesViewModel(repository) as T
    }

}