package com.example.simpleforecast.UI.Cities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.R
import com.example.simpleforecast.UI.Adapter.BaseAdapterCallback
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
        val cityAdapter = CityAdapter()

        viewModel.weather.observe(this, Observer { items ->
            if (!items.isNullOrEmpty())
            cityAdapter.setData(items)
        })

        viewModel.responseState.observe(this, Observer {
            when(it.first) {
                ResponseState.LOADING -> {
                    hideError()
                }
                ResponseState.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    if (it.second=="true") {
                        cityAdapter.clearData()
                        showError("На данный момент нет результатов по городу: ${citySearch.text}. Попробуйте позже.")
                    }
                }
                ResponseState.ERROR -> {
                    if (!cityAdapter.hasItems) showError("Произошла ошибка. Попробуйте позже.")
                    Toast.makeText(this, it.second, Toast.LENGTH_LONG).show()
                }
            }
        })

        search_button.setOnClickListener {
            viewModel.getCities(citySearch.text.toString())
        }



        cityAdapter.attachCallback(object : BaseAdapterCallback {
            override fun onItemClick(model: City) {
                val intent = Intent(applicationContext, WeatherActivity::class.java)
                intent.putExtra("cityId", model.id)
                startActivity(intent)
            }

        })
        all_wheather.adapter = cityAdapter
        all_wheather.layoutManager = LinearLayoutManager(this)

    }

    private fun showError(text:String) {
        progressBar.visibility = View.GONE
        error_image_main.visibility = View.VISIBLE
        error_sign_main.visibility = View.VISIBLE
        error_sign_main.text = text
    }

    private fun hideError() {
        progressBar.visibility = View.VISIBLE
        error_image_main.visibility = View.GONE
        error_sign_main.visibility = View.GONE
        error_sign_main.text = ""
    }
}
