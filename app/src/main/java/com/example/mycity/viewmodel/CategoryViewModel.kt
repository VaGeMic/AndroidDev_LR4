package com.example.mycity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycity.data.RecommendationRepository
import com.example.mycity.model.Category

class CategoryViewModel : ViewModel() {

    private val repo = RecommendationRepository()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        _categories.value = repo.getCategories()
    }
}
