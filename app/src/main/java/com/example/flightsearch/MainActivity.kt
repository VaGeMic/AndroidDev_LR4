package com.example.flightsearch

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flightsearch.data.local.FlightDatabase
import com.example.flightsearch.data.preferences.SearchPreferences
import com.example.flightsearch.databinding.ActivityMainBinding
import com.example.flightsearch.repository.FlightRepository
import com.example.flightsearch.ui.FlightAdapter
import com.example.flightsearch.ui.AirportAdapter
import com.example.flightsearch.ui.FavoriteAdapter
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.FlightSearchViewModelFactory
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: FlightSearchViewModel by viewModels {
        FlightSearchViewModelFactory(
            FlightRepository(
                FlightDatabase.getInstance(applicationContext).airportDao,
                FlightDatabase.getInstance(applicationContext).favoriteDao,
                SearchPreferences(applicationContext)
            ),
            SearchPreferences(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Адаптеры
        val airportAdapter = AirportAdapter { airport ->
            viewModel.setQuery(airport.iataCode)
            //viewModel.confirmSearch()
        }
        val flightAdapter = FlightAdapter { flight ->
            if (flight.isFavorite) viewModel.removeFavorite(flight.departCode, flight.arriveCode)
            else viewModel.addFavorite(flight.departCode, flight.arriveCode)
        }
        val favAdapter = FavoriteAdapter { origin, dest -> viewModel.removeFavorite(origin, dest) }

        // 1) Создаём адаптер для избранного
        val favFlightAdapter = FlightAdapter { item ->
            // При клике на звезду снимаем из избранного
            viewModel.removeFavorite(item.departCode, item.arriveCode)
        }

        // 2) RecyclerView
        binding.recyclerSuggestions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = airportAdapter
        }
        binding.recyclerDestinations.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = flightAdapter
        }
        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = favAdapter
        }
        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = favFlightAdapter
        }

        // 3) UI-слушатели
        binding.searchEdit.addTextChangedListener { text ->
            viewModel.setQuery(text?.toString().orEmpty())
        }
        binding.searchButton.setOnClickListener {
            viewModel.confirmSearch()
        }

        // 4) Подписка на данные
        viewModel.suggestions
            .asLiveData()
            .observe(this) { list ->
                airportAdapter.submitList(list)
            }

        binding.recyclerDestinations.adapter = flightAdapter

        viewModel.flights
            .asLiveData()
            .observe(this) { list ->
                Log.d("FlightsDebug", list.joinToString { "${it.departCode}->${it.arriveCode}" })
                flightAdapter.submitList(list)
            }

        viewModel.favorites
            .asLiveData()
            .observe(this) { list ->
                favAdapter.submitList(list)
            }

        viewModel.favoriteItems
            .asLiveData()
            .observe(this) { list ->
                favFlightAdapter.submitList(list)
            }

        viewModel.query
            .asLiveData()
            .observe(this) { q ->
                // обновляем текст в поле и видимость списков
                Log.i("MainActivity", binding.searchEdit.text.toString());
                Log.i("MainActivity", q);
                if (binding.searchEdit.text.toString() != q) {
                    binding.searchEdit.setText(q)
                    // и сразу двигаем курсор в конец
                    binding.searchEdit.setSelection(q.length)
                }
                val showFavorites = q.isBlank()
                binding.tvFavoritesHeader.visibility    = if (showFavorites) View.VISIBLE else View.GONE
                binding.recyclerFavorites.visibility    = if (showFavorites) View.VISIBLE else View.GONE

                binding.recyclerSuggestions.visibility  = if (showFavorites) View.GONE    else View.VISIBLE
                binding.recyclerDestinations.visibility = if (showFavorites) View.GONE    else View.VISIBLE
            }
    }
}
