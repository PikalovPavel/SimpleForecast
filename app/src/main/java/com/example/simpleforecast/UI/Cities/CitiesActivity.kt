package com.example.simpleforecast.UI.Cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

class CitiesActivity : AppCompatActivity(), KodeinAware  {
    private val factory : CitiesViewModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: CitiesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(CitiesViewModel::class.java)
        val newsAdaper = CityAdapter()

        viewModel.weather.observe(this, Observer { items ->
            if (items!=null)
            newsAdaper.setData(items)
        })

        viewModel.responseState.observe(this, Observer {
            when(it.first) {
                ResponseState.LOADING -> {progressBar.visibility = View.VISIBLE}
                ResponseState.SUCCESS -> {progressBar.visibility = View.GONE}
                ResponseState.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.second, Toast.LENGTH_LONG).show()
                }
            }
        })
        search_button.setOnClickListener {
            viewModel.getCities(citySearch.text.toString())
        }
        all_wheather.adapter = newsAdaper
        all_wheather.layoutManager = LinearLayoutManager(this)
    }
}
