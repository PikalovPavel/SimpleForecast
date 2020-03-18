package com.example.simpleforecast.UI.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleforecast.Data.Local.Util.CityTemperature
import com.example.simpleforecast.R
import java.util.ArrayList


class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private val cityAdapter: MutableList<CityTemperature> = ArrayList()



    fun setData(news:List<CityTemperature>) {
        cityAdapter.clear()
        cityAdapter.addAll(news)
        notifyDataSetChanged()
    }

    fun addItem(cityTemperature: CityTemperature) {
        cityAdapter.add(cityTemperature)
        notifyItemInserted(cityAdapter.size - 1)
    }

    fun clear() {
        cityAdapter.clear()
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false))
    }

    override fun getItemCount(): Int =  cityAdapter.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cityAdapter[position])
    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val cityName: TextView = itemView.findViewById(R.id.cityName)
        private val cityArea: TextView = itemView.findViewById(R.id.area)
        private val cityTemperature: TextView = itemView.findViewById(R.id.cityTemperature)

        fun bind(city: CityTemperature) {
            cityName.text = city.cityName
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