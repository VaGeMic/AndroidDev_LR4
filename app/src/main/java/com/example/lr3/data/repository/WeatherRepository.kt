package com.example.lr3.data.repository

import com.example.lr3.data.model.CityResponse
import com.example.lr3.data.model.DailyForecastUI
import com.example.lr3.data.model.HourlyData
import com.example.lr3.data.remote.CityService
import com.example.lr3.data.remote.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val cityService: CityService,
    private val weatherService: WeatherService
) {
    suspend fun getCityCoordinates(apiKey: String, name: String): CityResponse {
        val list = cityService.getCity(apiKey, name)
        if (list.isEmpty()) throw NoSuchElementException("City not found")
        return list.first()
    }

    suspend fun getDailyForecast(apiKey: String, name: String): List<DailyForecastUI> = withContext(Dispatchers.IO) {

        val city = getCityCoordinates(apiKey, name)
        val resp = weatherService.getWeather(city.latitude, city.longitude)

        val times = resp.daily.time
        val maxTemps = resp.daily.temperature_2m_max
        val minTemps = resp.daily.temperature_2m_min
        val hourlyTimes = resp.hourly.time
        val hourlyTemps = resp.hourly.temperature_2m

        val result = mutableListOf<DailyForecastUI>()

        // проходим по каждому дню
        times.forEachIndexed { dayIdx, date ->
            // получаем макс/мин для дня, но только если они не null
            val max = maxTemps.getOrNull(dayIdx)
            val min = minTemps.getOrNull(dayIdx)
            if (max == null || min == null) {
                // если в этом дне нет ни минимума, ни максимума — пропускаем его
                return@forEachIndexed
            }

            // собираем только непустые часовые данные
            val hourlyList = hourlyTimes.mapIndexedNotNull { hourIdx, ts ->
                val temp = hourlyTemps.getOrNull(hourIdx)
                // берём только те записи, у которых timestamp стартует с этого дня и temp != null
                if (temp != null && ts.startsWith(date)) {
                    HourlyData(ts.substringAfter("T"), temp)
                } else null
            }

            // создаём UI-модель
            result += DailyForecastUI(
                date = date,
                maxTemp = max,
                minTemp = min,
                hourlyData = hourlyList
            )
        }

        result
    }
}
