package ru.playsoftware.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.list_item_weather.view.*
import ru.playsoftware.weather.GlideApp
import ru.playsoftware.weather.R
import ru.playsoftware.weather.data.Day
import ru.playsoftware.weather.util.getImageURL
import ru.playsoftware.weather.util.getWeatherInfo

class WeatherAdapter(private var list: List<Day>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = list[position]
        holder.info.text = getWeatherInfo(day)
        GlideApp.with(holder.icon.context)
                .load(getImageURL(day.weather[0].icon))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.icon)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.icon
        val info = view.info
    }
}