package com.example.simpleforecast.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleforecast.R
import com.example.simpleforecast.UI.Adapter.WeatherAdapter
import com.example.simpleforecast.Util.ResponseState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_item.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware  {
    private val factory : MainViewModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        val newsAdaper = WeatherAdapter()

        viewModel.weather.observe(this, Observer { item ->
            newsAdaper.addItem(item)
        })

        viewModel.responseState.observe(this, Observer {
            when(it.first) {
                ResponseState.LOADING -> {newsAdaper.clear()}
                ResponseState.SUCCESS -> {}
                ResponseState.ERROR -> {}
            }
        })
        all_wheather.adapter = newsAdaper
        all_wheather.layoutManager = LinearLayoutManager(this)
    }
}
