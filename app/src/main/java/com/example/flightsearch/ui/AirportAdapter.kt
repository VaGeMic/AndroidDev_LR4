package com.example.flightsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flightsearch.data.local.Airport
import com.example.flightsearch.databinding.ItemAirportBinding

class AirportAdapter(
    private val onClick: (Airport) -> Unit
) : ListAdapter<Airport, AirportAdapter.ViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Airport>() {
        override fun areItemsTheSame(a: Airport, b: Airport) = a.iataCode == b.iataCode
        override fun areContentsTheSame(a: Airport, b: Airport) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAirportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val b: ItemAirportBinding) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(item: Airport) {
            b.textCode.text = item.iataCode
            b.textName.text = item.name
            b.root.setOnClickListener { onClick(item) }
        }
    }
}