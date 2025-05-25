package com.example.flightsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flightsearch.R
import com.example.flightsearch.ui.model.FlightItem

class FlightAdapter(
    private val onToggleFavorite: (FlightItem) -> Unit
) : ListAdapter<FlightItem, FlightAdapter.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDepart: TextView = view.findViewById(R.id.tvDepart)
        private val tvArrive: TextView = view.findViewById(R.id.tvArrive)
        private val ivFavorite: ImageView = view.findViewById(R.id.ivFavorite)

        fun bind(item: FlightItem) {
            tvDepart.text = "${item.departCode} — ${item.departName}"
            tvArrive.text = "${item.arriveCode} — ${item.arriveName}"
            ivFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_star_filled
                else R.drawable.ic_star_border
            )

            ivFavorite.setOnClickListener {
                onToggleFavorite(item)
            }
        }
    }

    private object Diff : DiffUtil.ItemCallback<FlightItem>() {
        override fun areItemsTheSame(a: FlightItem, b: FlightItem) =
            a.departCode == b.departCode && a.arriveCode == b.arriveCode

        override fun areContentsTheSame(a: FlightItem, b: FlightItem) = a == b
    }
}
