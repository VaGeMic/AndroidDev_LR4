package com.example.lr_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCity: EditText
    private lateinit var buttonSearch: Button
    private lateinit var textViewForecastHeader: TextView
    private lateinit var recyclerViewForecast: RecyclerView

    private lateinit var cityService: CityService
    private lateinit var weatherService: WeatherService

    private val apiNinjasApiKey = "LNyP74AT7NOkkjpk7HZJqg==xAVjrE2JjbU7qS0p"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCity = findViewById(R.id.editTextCity)
        buttonSearch = findViewById(R.id.buttonSearch)
        textViewForecastHeader = findViewById(R.id.textViewForecastHeader)
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        recyclerViewForecast.layoutManager = LinearLayoutManager(this)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val cityRetrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cityService = cityRetrofit.create(CityService::class.java)

        val weatherRetrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherService = weatherRetrofit.create(WeatherService::class.java)

        buttonSearch.setOnClickListener {
            val cityName = editTextCity.text.toString().trim()
            if (cityName.isNotEmpty()) {
                fetchWeatherForCity(cityName)
            } else {
                Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchWeatherForCity(cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cityResponseList = cityService.getCity(apiNinjasApiKey, cityName)
                Log.i("MainActivity", cityResponseList.toString())
                if (cityResponseList.isNotEmpty()) {
                    val city = cityResponseList[0]
                    val latitude = city.latitude
                    val longitude = city.longitude
                    Log.i("MainActivity", city.toString())
                    Log.i("MainActivity", latitude.toString())
                    Log.i("MainActivity", longitude.toString())
                    val weatherResponse = weatherService.getWeather(latitude, longitude)
                    Log.i("MainActivity", weatherResponse.toString())
                    val dailyForecastUIList = buildDailyForecastUI(weatherResponse)

                    withContext(Dispatchers.Main) {
                        textViewForecastHeader.text = "Прогноз погоды для ${city.name}"

                        recyclerViewForecast.adapter = DailyForecastAdapter(dailyForecastUIList)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Город не найден", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка при загрузке данных в fetch: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.i("MainActivity", "ended")
    }

    private fun buildDailyForecastUI(weatherResponse: WeatherResponse): List<DailyForecastUI> {
        val dailyList = mutableListOf<DailyForecastUI>()
        val dailyDates = weatherResponse.daily.time
        var cnt = 0
        for (i in dailyDates.indices) {
            cnt+=1
            val date = dailyDates[i]
            val maxTemp = weatherResponse.daily.temperature_2m_max[i]
            val minTemp = weatherResponse.daily.temperature_2m_min[i]
            Log.i("MainCycle", weatherResponse.hourly.toString())
            val hourlyDataForDate = mutableListOf<HourlyData>()
            for (j in weatherResponse.hourly.time.indices) {
                //Log.i("Cycle", weatherResponse.hourly.time[j])
                //Log.i("Cycle", weatherResponse.hourly.temperature_2m[j].toString())
                try {
                    val hourlyTimeStr = weatherResponse.hourly.time[j]
                    if (hourlyTimeStr.startsWith(date)) {
                        val timePart = hourlyTimeStr.substringAfter("T")
                        val temp = weatherResponse.hourly.temperature_2m[j]
                        hourlyDataForDate.add(HourlyData(time = timePart, temperature = temp))
                    }
                }
                catch (e: Exception)
                {
                    Log.w("buildDailyForecastUI", "Получен не весь прогноз погоды")
                }
            }
            dailyList.add(DailyForecastUI(date, maxTemp, minTemp, hourlyDataForDate))
        }
        return dailyList
    }
}
