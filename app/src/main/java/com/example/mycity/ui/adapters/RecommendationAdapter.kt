package com.example.mycity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycity.databinding.ItemRecommendationBinding
import com.example.mycity.model.Recommendation

class RecommendationAdapter(
    private val items: List<Recommendation>,
    private val onClick: (Recommendation) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.VH>() {

    inner class VH(private val binding: ItemRecommendationBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(rec: Recommendation) {
            binding.tvRecommendationName.text = rec.name
            binding.root.setOnClickListener { onClick(rec) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
