package com.example.lr3.data.model

data class DailyForecastUI(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val hourlyData: List<HourlyData>
)
