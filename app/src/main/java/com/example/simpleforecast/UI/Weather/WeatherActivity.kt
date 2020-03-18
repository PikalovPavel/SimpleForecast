package com.example.simpleforecast.UI.Cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleforecast.R
import com.example.simpleforecast.UI.Adapter.CityAdapter
import com.example.simpleforecast.Util.ResponseState
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class WeatherActivity : AppCompatActivity(), KodeinAware  {
    private val factory : WeatherViewModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
        val newsAdaper = CityAdapter()

//        viewModel.weather.observe(this, Observer { item ->
//            newsAdaper.addItem(item)
//        })
//
//        viewModel.responseState.observe(this, Observer {
//            when(it.first) {
//                ResponseState.LOADING -> {newsAdaper.clear()}
//                ResponseState.SUCCESS -> {}
//                ResponseState.ERROR -> {}
//            }
//        })
//        all_wheather.adapter = newsAdaper
//        all_wheather.layoutManager = LinearLayoutManager(this)
    }
}
