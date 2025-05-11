package com.example.mycity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycity.databinding.FragmentRecommendationListBinding
import com.example.mycity.ui.adapters.RecommendationAdapter
import com.example.mycity.viewmodel.RecommendationViewModel
import androidx.core.os.bundleOf
import com.example.mycity.R

class RecommendationListFragment : Fragment() {

    private var _binding: FragmentRecommendationListBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<RecommendationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRecommendationListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvRecommendations.layoutManager = LinearLayoutManager(requireContext())

        val categoryName = requireArguments().getString("categoryName")!!
        vm.loadRecommendations(categoryName)
        vm.recommendations.observe(viewLifecycleOwner) { list ->
            binding.rvRecommendations.adapter = RecommendationAdapter(list) { rec ->
                // Навигация в детали с передачей всех трёх аргументов
                findNavController().navigate(
                    R.id.action_recommendation_to_detail,
                    bundleOf(
                        "name" to rec.name,
                        "description" to rec.description,
                        "imageResId" to rec.imageResId
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
