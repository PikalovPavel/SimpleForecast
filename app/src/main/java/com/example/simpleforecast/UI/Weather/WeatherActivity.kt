package com.example.simpleforecast.UI.Cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.R
import com.example.simpleforecast.UI.Adapter.CityAdapter
import com.example.simpleforecast.Util.ResponseState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weather.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class WeatherActivity : AppCompatActivity(), KodeinAware {
    private val factory: WeatherViewModelFactory by instance()
    private var cityId: String? = null

    override val kodein by kodein()
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)



        cityId = intent.getStringExtra("cityId") ?: viewModel.getCityId()
        if (cityId != null) {
            viewModel.saveCityId(cityId!!)
            viewModel.getCurrentWeather(cityId!!)
        } else {
            showError()
        }


        viewModel.weather.observe(this, Observer { item ->
            attachOnUi(item)
        })

        viewModel.responseState.observe(this, Observer {
            when (it.first) {
                ResponseState.LOADING -> {
                    progressBar2.visibility = View.VISIBLE
                }
                ResponseState.SUCCESS -> {
                    progressBar2.visibility = View.GONE
                }
                ResponseState.ERROR -> {
                    progressBar2.visibility = View.GONE
                    Toast.makeText(this, it.second, Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun attachOnUi(weather: Weather) {
        degreeCelsius.text = weather.temperature.toString()?:""
        degreeCelsiusFeel.text = weather.temperatureFeels.toString()
        weatherDescription.text = weather.temperatureDescription
        cityIdTv.text = "СПБ, славяне"
        areaWeather.text = "СПБ край славянский"
        windPowerTv.text = weather.wind.toString()
        windDirectionTv.text = weather.windDirection
        preasureTv.text = weather.pressure.toString()
        Glide
            .with(this)
            .load(getImage("s${weather.icon}"))
            .into(weatherImage)
    }

    private fun showError() {
        Log.d("kek", cityId.toString())
    }

    private fun getImage(imageName: String): Int {
        val id = this.resources.getIdentifier(imageName, "drawable", this.packageName)
        Log.d("kek", id.toString())
        return id
    }
}
