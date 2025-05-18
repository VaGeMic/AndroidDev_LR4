package com.example.lr_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateIsoToRussian(dateString: String): String {
    return try {
        val localDate = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy года", Locale("ru"))
        localDate.format(formatter)
    } catch (e: Exception) {
        dateString
    }
}

class DailyForecastAdapter(private val dailyForecasts: List<DailyForecastUI>) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val forecast = dailyForecasts[position]
        holder.bind(forecast)
    }

    override fun getItemCount(): Int = dailyForecasts.size

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val linearLayoutHourly: LinearLayout = itemView.findViewById(R.id.linearLayoutHourly)
        private val textViewMaxTemp: TextView = itemView.findViewById(R.id.textViewMaxTemp)
        private val textViewMinTemp: TextView = itemView.findViewById(R.id.textViewMinTemp)

        fun bind(forecast: DailyForecastUI) {

            textViewDate.text = "${formatDateIsoToRussian(forecast.date)}"
            textViewMaxTemp.text = "День: ${forecast.maxTemp}°C"
            textViewMinTemp.text = "Ночь: ${forecast.minTemp}°C"
            linearLayoutHourly.removeAllViews()

            forecast.hourlyData.forEach { hourly ->
                val hourView = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_hourly_forecast, linearLayoutHourly, false)
                val textViewHour: TextView = hourView.findViewById(R.id.textViewHour)
                val textViewTemp: TextView = hourView.findViewById(R.id.textViewTemp)
                textViewHour.text = hourly.time
                textViewTemp.text = "${hourly.temperature}°"
                linearLayoutHourly.addView(hourView)
            }
        }
    }
}
