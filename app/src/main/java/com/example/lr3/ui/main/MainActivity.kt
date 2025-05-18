package com.example.lr3.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr3.databinding.ActivityMainBinding
import com.example.lr3.ui.main.adapter.DailyForecastAdapter
import com.example.lr3.domain.usecase.GetCityCoordinatesUseCase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val apiKey = "LNyP74AT7NOkkjpk7HZJqg==xAVjrE2JjbU7qS0p"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        binding.buttonSearch.setOnClickListener {
            val city = binding.editTextCity.text.toString().trim()
            if (city.isEmpty()) {
                Toast.makeText(this, "Введите город", Toast.LENGTH_SHORT).show()
            } else {
                vm.fetch(apiKey, city)
            }
        }

        vm.cityName.observe(this) { name ->
            binding.textViewForecastHeader.text = "Прогноз для $name"
        }

        // А список прогнозов как был
        vm.forecasts.observe(this) { list ->
            binding.recyclerViewForecast.adapter = DailyForecastAdapter(list)
        }
        vm.error.observe(this) {
            it?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }
}
