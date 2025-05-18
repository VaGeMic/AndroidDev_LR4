package com.example.lr3.domain.usecase

import com.example.lr3.data.model.DailyForecastUI
import com.example.lr3.data.repository.WeatherRepository

class GetDailyForecastUseCase(
    private val repo: WeatherRepository
) {
    suspend operator fun invoke(apiKey: String, city: String): List<DailyForecastUI> =
        repo.getDailyForecast(apiKey, city)
}
