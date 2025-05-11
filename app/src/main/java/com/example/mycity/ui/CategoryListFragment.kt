package com.example.mycity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycity.R
import com.example.mycity.databinding.FragmentCategoryListBinding
import com.example.mycity.ui.adapters.CategoryAdapter
import com.example.mycity.viewmodel.CategoryViewModel

class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    private val vm by viewModels<CategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCategoryListBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        vm.categories.observe(viewLifecycleOwner) { list ->
            binding.rvCategories.adapter = CategoryAdapter(list) { category ->
                // Навигация и передача аргумента
                findNavController().navigate(
                    R.id.action_category_to_recommendation,
                    bundleOf("categoryName" to category.name)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
