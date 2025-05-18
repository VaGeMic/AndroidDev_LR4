package com.example.lr3.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lr3.databinding.ItemDailyForecastBinding
import com.example.lr3.data.model.DailyForecastUI
import com.example.lr3.util.DateUtils
import com.example.lr3.ui.main.adapter.viewholder.HourlyViewHolderCreator

class DailyForecastAdapter(
    private val items: List<DailyForecastUI>
) : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    inner class ViewHolder(private val b: ItemDailyForecastBinding)
        : RecyclerView.ViewHolder(b.root) {
        fun bind(f: DailyForecastUI) {
            b.textViewDate.text = DateUtils.formatIsoToRussian(f.date)
            b.textViewMaxTemp.text = "День: ${f.maxTemp}°C"
            b.textViewMinTemp.text = "Ночь: ${f.minTemp}°C"
            b.linearLayoutHourly.removeAllViews()
            f.hourlyData.forEach { h ->
                val hv = HourlyViewHolderCreator.create(b.root, h)
                b.linearLayoutHourly.addView(hv)
            }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, i: Int) =
        ViewHolder(ItemDailyForecastBinding.inflate(
            LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: ViewHolder, pos: Int) = h.bind(items[pos])
    override fun getItemCount() = items.size
}
