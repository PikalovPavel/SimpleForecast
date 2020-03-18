package com.example.simpleforecast

import android.app.Application
import com.example.simpleforecast.Data.Remote.ForecastApi
import com.example.simpleforecast.Data.Remote.RetrofitService
import com.example.simpleforecast.Data.Repositories.WeatherRepository
import com.example.simpleforecast.UI.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind<ForecastApi>() with singleton {
            RetrofitService.create()
        }

        bind<WeatherRepository>() with singleton {
            WeatherRepository(instance())
        }

        bind() from provider { MainViewModelFactory(instance())}

    }
}