package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.local.Favorite
import com.example.flightsearch.data.local.Airport
import com.example.flightsearch.data.preferences.SearchPreferences
import com.example.flightsearch.repository.FlightRepository
import com.example.flightsearch.ui.model.FlightItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val repository: FlightRepository,
    private val prefs: SearchPreferences
) : ViewModel() {

    // 1) Текущая строка поиска — храним и сразу читаем из DataStore
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = prefs.searchQueryFlow
        .onEach { _query.value = it }
        .stateIn(viewModelScope, SharingStarted.Lazily, _query.value)

    // 2) Подсказки аэропортов
    val suggestions: Flow<List<Airport>> = _query
        .debounce(300)
        .flatMapLatest { q ->
            if (q.isBlank()) emptyFlow()
            else repository.searchAirports("%$q%")
        }

    // 3) Список пар рейсов — именно List<FlightItem>
    val flights: Flow<List<FlightItem>> = _query
        .filter { it.isNotBlank() }
        .flatMapLatest { originCode ->
            repository.getFlights(originCode)
        }

    val favoriteItems: Flow<List<FlightItem>> =
        repository.getFavoriteFlightItems()

    // 4) Избранное
    val favorites: Flow<List<Favorite>> = repository.getFavorites()

    // 5) Управление поиском и избранным
    fun setQuery(q: String) {
        viewModelScope.launch {
            prefs.saveSearchQuery(q)
        }
    }

    fun confirmSearch() {
        viewModelScope.launch {
            prefs.saveSearchQuery(_query.value)
        }
    }

    fun addFavorite(origin: String, dest: String) {
        viewModelScope.launch { repository.addFavorite(origin, dest) }
    }

    fun removeFavorite(origin: String, dest: String) {
        viewModelScope.launch { repository.removeFavorite(origin, dest) }
    }
}
