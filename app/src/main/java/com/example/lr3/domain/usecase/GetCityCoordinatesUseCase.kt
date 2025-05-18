package com.example.lr3.domain.usecase

import com.example.lr3.data.model.CityResponse
import com.example.lr3.data.repository.WeatherRepository

class GetCityCoordinatesUseCase(
    private val repo: WeatherRepository
) {
    suspend operator fun invoke(apiKey: String, city: String): CityResponse =
        repo.getCityCoordinates(apiKey, city)
}
