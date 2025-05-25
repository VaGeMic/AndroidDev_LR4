// app/src/main/java/com/example/flightsearch/ui/FlightSearchViewModelFactory.kt
package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flightsearch.data.preferences.SearchPreferences
import com.example.flightsearch.repository.FlightRepository

class FlightSearchViewModelFactory(
    private val repository: FlightRepository,
    private val prefs: SearchPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlightSearchViewModel(repository, prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
