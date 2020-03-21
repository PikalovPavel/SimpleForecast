package com.example.simpleforecast.UI.Cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.simpleforecast.Data.Local.Database.Entity.CityWeather
import com.example.simpleforecast.R
import com.example.simpleforecast.Util.ResponseState
import com.example.simpleforecast.Util.kmToMsConverter
import com.example.simpleforecast.Util.temperatureConverter
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
            showError("При загрузке погоды произошла ошибка, попробуйте позже.")
        }


        viewModel.weather.observe(this, Observer { item ->
            attachOnUi(item)
        })

        swipeRefresh.setOnRefreshListener {
            viewModel.updateWeather(cityId)
        }

        viewModel.responseState.observe(this, Observer {
            when (it.first) {
                ResponseState.LOADING -> {
                    swipeRefresh.isRefreshing = true
                    hideError()
                }
                ResponseState.SUCCESS -> {
                    swipeRefresh.isRefreshing = false

                }
                ResponseState.ERROR -> {
                    swipeRefresh.isRefreshing = false
                    showError("При загрузке погоды произошла ошибка, попробуйте позже.")
                    Toast.makeText(this, it.second, Toast.LENGTH_LONG).show()
                }
            }
        })

        backButton.setOnClickListener {
            onBackPressed()
        }

    }



    private fun attachOnUi(cityWeather: CityWeather) {
        degreeCelsius.text = if (cityWeather.weather.temperature==null) ""
        else temperatureConverter(cityWeather.weather.temperature)

        degreeCelsiusFeel.text = if (cityWeather.weather.temperatureFeels==null) ""
        else temperatureConverter(cityWeather.weather.temperatureFeels)

        weatherDescription.text = cityWeather.weather.temperatureDescription
        cityIdTv.text = cityWeather.city.name
        areaWeather.text = cityWeather.city.area

        windPowerTv.text = if (cityWeather.weather.wind!=null) "${cityWeather.weather.wind} м/c" else ""

        windDirectionTv.text = cityWeather.weather.windDirection

        preasureTv.text = if (cityWeather.weather.pressure==null) ""
        else "${cityWeather.weather.pressure} мм рт. ст."


        Glide
            .with(this)
            .load(getImage("s${cityWeather.weather.icon}"))
            .into(weatherImage)
    }


    private fun showError(text:String) {
        error_image.visibility = View.VISIBLE
        error_sign.visibility = View.VISIBLE
        error_sign.text = text
    }

    private fun hideError() {
        error_image.visibility = View.GONE
        error_sign.visibility = View.GONE
        error_sign.text = ""
    }

    private fun getImage(imageName: String): Int {
        val id = this.resources.getIdentifier(imageName, "drawable", this.packageName)
        Log.d("kek", id.toString())
        return id
    }


}
