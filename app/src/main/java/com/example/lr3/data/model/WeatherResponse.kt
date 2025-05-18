package com.example.lr3.data.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: DailyForecast,
    val hourly: HourlyForecast
)
