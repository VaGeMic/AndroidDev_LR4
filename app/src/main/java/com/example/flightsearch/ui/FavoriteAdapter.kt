package com.example.flightsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flightsearch.data.local.Favorite
import com.example.flightsearch.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onRemove: (String, String) -> Unit
) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(a: Favorite, b: Favorite) =
            a.departureCode == b.departureCode && a.destinationCode == b.destinationCode
        override fun areContentsTheSame(a: Favorite, b: Favorite) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val b: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(item: Favorite) {
            b.textRoute.text = "${item.departureCode} → ${item.destinationCode}"
            b.buttonRemove.setOnClickListener { onRemove(item.departureCode, item.destinationCode) }
        }
    }
}