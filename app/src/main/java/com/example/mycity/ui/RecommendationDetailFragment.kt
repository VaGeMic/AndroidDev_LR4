package com.example.mycity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mycity.databinding.FragmentRecommendationDetailBinding

class RecommendationDetailFragment : Fragment() {

    private var _binding: FragmentRecommendationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentRecommendationDetailBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Получаем аргументы
        val args = requireArguments()
        binding.ivDetailImage.setImageResource(args.getInt("imageResId"))
        binding.tvDetailName.text = args.getString("name")
        binding.tvDetailDescription.text = args.getString("description")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
