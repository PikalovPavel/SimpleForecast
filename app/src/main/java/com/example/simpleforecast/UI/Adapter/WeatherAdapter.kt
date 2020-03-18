package com.example.simpleforecast.UI.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleforecast.Data.Local.WeatherItem
import com.example.simpleforecast.R
import java.util.ArrayList


class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val mWeatherList: MutableList<WeatherItem> = ArrayList()



    fun setData(news:List<WeatherItem>) {
        mWeatherList.clear()
        mWeatherList.addAll(news)
        notifyDataSetChanged()
    }

    fun addItem(newItem: WeatherItem) {
        mWeatherList.add(newItem)
        notifyItemInserted(mWeatherList.size - 1)
    }

    fun clear() {
        mWeatherList.clear()
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false))
    }

    override fun getItemCount(): Int =  mWeatherList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mWeatherList[position])
    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val degreeCelsius: TextView = itemView.findViewById(R.id.degreeCelsius)
        private val weatherDescription: TextView = itemView.findViewById(R.id.weatherDescription)
        private val locationId: TextView = itemView.findViewById(R.id.cityId)
        private val imageIcon:ImageView = itemView.findViewById(R.id.weatherImage)

        fun bind(weather: WeatherItem) {
            degreeCelsius.text = weather.temperature
            weatherDescription.text = weather.temperatureDescription
            locationId.text = weather.cityId
            Glide
                .with(itemView.context)
                .load(getImage("s${weather.icon}", itemView.context))
                .into(imageIcon)

        }
        fun getImage(imageName: String, context: Context): Int {

            val id = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            Log.d("kek", id.toString())
            return id
        }
    }


}