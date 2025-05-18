package com.example.lr3.ui.main

import androidx.lifecycle.*
import com.example.lr3.data.model.DailyForecastUI
import com.example.lr3.domain.usecase.GetCityCoordinatesUseCase
import com.example.lr3.domain.usecase.GetDailyForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDaily: GetDailyForecastUseCase,
    private val getCoords: GetCityCoordinatesUseCase
) : ViewModel() {

    private val _forecasts = MutableLiveData<List<DailyForecastUI>>()
    val forecasts: LiveData<List<DailyForecastUI>> = _forecasts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> = _cityName

    fun fetch(apiKey: String, city: String) = viewModelScope.launch {
        try {
            val cityResp = getCoords("LNyP74AT7NOkkjpk7HZJqg==xAVjrE2JjbU7qS0p", city)
            _cityName.value = cityResp.name;
            _forecasts.value = getDaily(apiKey, city)
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}
