package com.example.mycity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycity.data.RecommendationRepository
import com.example.mycity.model.Recommendation

class RecommendationViewModel : ViewModel() {

    private val repo = RecommendationRepository()

    private val _recommendations = MutableLiveData<List<Recommendation>>()
    val recommendations: LiveData<List<Recommendation>> = _recommendations

    fun loadRecommendations(categoryName: String) {
        _recommendations.value = repo.getRecommendationsFor(categoryName)
    }
}
