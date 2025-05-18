package com.example.lr_3
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class DailyForecast(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)

data class HourlyForecast(
    val time: List<String>,
    val temperature_2m: List<Double>
)

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: DailyForecast,
    val hourly: HourlyForecast
)

interface WeatherService {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("hourly") hourly: String = "temperature_2m",
        @Query("forecast_days") forecastDays: Int = 16,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

data class CityResponse(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)

interface CityService {
    @GET("city")
    suspend fun getCity(
        @Header("X-Api-Key") apiKey: String,
        @Query("name") cityName: String
    ): List<CityResponse>
}

data class DailyForecastUI(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val hourlyData: List<HourlyData>
)

data class HourlyData(
    val time: String,
    val temperature: Double
)