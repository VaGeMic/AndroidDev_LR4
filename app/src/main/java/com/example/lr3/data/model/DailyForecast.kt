package com.example.lr3.data.model

data class DailyForecast(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)
