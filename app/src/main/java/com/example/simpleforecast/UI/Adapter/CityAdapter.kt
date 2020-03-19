package com.example.simpleforecast.UI.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.R
import java.util.ArrayList


class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private val cityList: MutableList<City> = ArrayList()



    fun setData(news:List<City>) {
        cityList.clear()
        cityList.addAll(news)
        notifyDataSetChanged()
    }

    private var mCallback: BaseAdapterCallback? = null

    fun attachCallback(callback: BaseAdapterCallback) {
        this.mCallback = callback
    }

    fun detachCallback() {
        this.mCallback = null
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false))
    }

    override fun getItemCount(): Int =  cityList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cityList[position])
        holder.itemView.setOnClickListener {
            mCallback?.onItemClick(cityList[position])
        }
    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val cityName: TextView = itemView.findViewById(R.id.cityName)
        private val cityArea: TextView = itemView.findViewById(R.id.area)
        private val cityTemperature: TextView = itemView.findViewById(R.id.cityTemperature)

        fun bind(city: City) {
            cityName.text = city.name
            cityArea.text = city.area
            cityTemperature.text = city.temperature
//            Glide
//                .with(itemView.context)
//                .load(getImage("s${city.icon}", itemView.context))
//                .into(imageIcon)

        }
//        fun getImage(imageName: String, context: Context): Int {
//
//            val id = context.resources.getIdentifier(imageName, "drawable", context.packageName)
//            Log.d("kek", id.toString())
//            return id
//        }
    }


}